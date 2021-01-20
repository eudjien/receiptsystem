package ru.clevertec.checksystem.cli;

import ru.clevertec.checksystem.core.DataSeed;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.check.CheckItem;
import ru.clevertec.checksystem.core.factory.ServiceFactory;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.BeforeExecutionLog;
import ru.clevertec.checksystem.core.service.CheckIoService;
import ru.clevertec.checksystem.core.service.CheckPrintingService;
import ru.clevertec.checksystem.core.service.ICheckIoService;
import ru.clevertec.checksystem.core.service.ICheckPrintingService;
import ru.clevertec.checksystem.normalino.list.NormalinoList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    static boolean isServicesUseProxy;
    static final Pattern pattern = Pattern.compile("^-(?<key>.+)=(?<value>.+)$");

    @BeforeExecutionLog(level = LogLevel.DEBUG)
    public static void main(String[] args) throws Exception {

        var argumentsFinder = new ArgumentsFinder(args);

        isServicesUseProxy = argumentsFinder.findBoolOrDefault(Constants.Keys.PROXIED_SERVICES);

        var checks = new NormalinoList<Check>();

        var mode = argumentsFinder.findStringOrThrow(Constants.Keys.MODE);
 
        switch (mode) {
            case Constants.Modes.GENERATE -> generate(argumentsFinder, checks);
            case Constants.Modes.FILE_DESERIALIZE -> fileDeserialize(argumentsFinder, checks);
            case Constants.Modes.PRE_DEFINED -> checks.addAll(applyFilterIfExist(DataSeed.Checks(), argumentsFinder));
        }

        if (argumentsFinder.findBoolOrDefault(Constants.Keys.FILE_SERIALIZE)) {
            fileSerialize(argumentsFinder, checks);
        }

        if (argumentsFinder.findBoolOrDefault(Constants.Keys.FILE_PRINT)) {
            filePrint(argumentsFinder, checks);
        }
    }

    static void generate(ArgumentsFinder finder, NormalinoList<Check> checks) throws Exception {
        var check = generateCheck(finder.getArguments());
        var checkItems = generateCheckItems(finder.getArguments());
        if (checkItems.isEmpty()) {
            throw new Exception("Check items not defined");
        }
        check.setItems(checkItems);
        applyCheckDiscounts(check, finder.getArguments());
        applyCheckItemsDiscounts(check, finder.getArguments());
        checks.add(check);
    }

    static void fileDeserialize(ArgumentsFinder finder, NormalinoList<Check> checks) throws Exception {

        var format = finder.findStringOrThrow(Constants.Keys.FILE_DESERIALIZE_FORMAT);
        var srcPath = finder.findStringOrThrow(Constants.Keys.FILE_DESERIALIZE_PATH);
        ICheckIoService ioService = ServiceFactory.instance(CheckIoService.class, isServicesUseProxy);
        var deserializedChecks = ioService.deserializeFromFile(srcPath, format);
        checks.addAll(applyFilterIfExist(deserializedChecks, finder));
    }

    static void fileSerialize(ArgumentsFinder finder, NormalinoList<Check> checks) throws Exception {

        var format = finder.findStringOrThrow(Constants.Keys.FILE_SERIALIZE_FORMAT);
        var destPath = finder.findStringOrThrow(Constants.Keys.FILE_SERIALIZE_PATH);
        ICheckIoService ioService = ServiceFactory.instance(CheckIoService.class, isServicesUseProxy);
        ioService.serializeToFile(checks, destPath, format);
    }

    static void filePrint(ArgumentsFinder finder, NormalinoList<Check> checks) throws Exception {

        var printFormat = finder.findStringOrThrow(Constants.Keys.FILE_PRINT_FORMAT);
        var destPath = finder.findStringOrThrow(Constants.Keys.FILE_PRINT_PATH);
        ICheckPrintingService printingService = ServiceFactory.instance(CheckPrintingService.class, isServicesUseProxy);

        switch (printFormat.toLowerCase()) {
            case "text" -> printingService.printToTextFile(checks, destPath);
            case "html" -> printingService.printToHtmlFile(checks, destPath);
            case "pdf" -> {
                if (finder.findBoolOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE)) {
                    printingService.printToPdfFile(checks, destPath,
                            finder.findStringOrNull(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH),
                            finder.findIntOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET));
                } else {
                    printingService.printToPdfFile(checks, destPath);
                }
            }
            default -> throw new Exception("Print format '" + printFormat + " 'not supported");
        }
    }

    static void applyCheckDiscounts(Check check, String[] args) throws Exception {
        var discounts = DataSeed.CheckDiscounts();
        var discountKeys = getDiscountCardsFromArgs(args);
        for (var key : discountKeys) {
            var discount = discounts.get(key);
            if (discount == null) {
                throw new Exception("Check discount '" + key + "' not found");
            }
            check.addDiscount(discount);
        }
    }

    static void applyCheckItemsDiscounts(Check check, String[] args) throws Exception {
        var discounts = DataSeed.CheckItemDiscounts();
        var checkItemDiscountPair = getDiscountCardItemsFromArgs(args);
        for (var pair : checkItemDiscountPair) {
            var discount = discounts.get(pair.getDiscountKey());
            if (discount == null) {
                throw new Exception("Check item discount '" + pair.getDiscountKey() + "' not found");
            }
            var checkItem = check.getItems().stream()
                    .filter(a -> a.getProduct().getId() == pair.getProductId()).findFirst().orElse(null);
            if (checkItem == null) {
                throw new Exception("Product #" + pair.getProductId() + " not found");
            }
            checkItem.addDiscount(discount);
        }
    }

    static String[] getDiscountCardsFromArgs(String[] args) {
        var list = new NormalinoList<String>();
        if (args == null) {
            return list.toArray(String[]::new);
        }
        var regex = "^d-check=(?<key>\\w+)$";
        var pattern = Pattern.compile(regex);

        for (String arg : args) {
            var matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                var discountKey = matcher.group("key");
                list.add(discountKey);
            }
        }
        return list.toArray(String[]::new);
    }

    static CheckItemDiscountPair[] getDiscountCardItemsFromArgs(String[] args) {
        var list = new NormalinoList<CheckItemDiscountPair>();
        if (args == null) {
            return list.toArray(CheckItemDiscountPair[]::new);
        }

        var regex = "^d-item=(?<id>\\d+):(?<key>\\w+)$";
        var pattern = Pattern.compile(regex);

        for (String arg : args) {
            var matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                var productId = Integer.parseInt(matcher.group("id"));
                var discountKey = matcher.group("key");
                list.add(new CheckItemDiscountPair(productId, discountKey));
            }
        }

        return list.toArray(CheckItemDiscountPair[]::new);
    }

    static Check generateCheck(String[] args) throws ParseException {
        var regex = "^-(?<key>\\w+)=(?<value>.+)$";

        var pattern = Pattern.compile(regex);

        var check = new Check();

        for (var arg : args) {

            var matcher = pattern.matcher(arg);

            if (matcher.matches()) {
                var key = matcher.group("key");
                if (key != null) {
                    if (key.equals("id")) {
                        check.setId(Integer.parseInt(matcher.group("value")));
                    }
                    if (key.equals("name")) {
                        check.setName(matcher.group("value"));
                    }
                    if (key.equals("description")) {
                        check.setDescription(matcher.group("value"));
                    }
                    if (key.equals("address")) {
                        check.setAddress(matcher.group("value"));
                    }
                    if (key.equals("cashier")) {
                        check.setCashier(matcher.group("value"));
                    }
                    if (key.equals("phoneNumber")) {
                        check.setPhoneNumber(matcher.group("value"));
                    }
                    if (key.equals("date")) {
                        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
                        check.setDate(dateFormatter.parse(matcher.group("value")));
                    }
                }
            }
        }

        return check;
    }

    static List<CheckItem> generateCheckItems(String[] args) throws Exception {
        var checkItems = new NormalinoList<CheckItem>();
        var id = 1;

        for (var arg : args) {

            var matcher = pattern.matcher(arg);

            if (matcher.matches()) {
                var key = matcher.group("key");
                if (key != null) {
                    if (key.equals("ci")) {
                        var s = matcher.group("value").split(":");
                        var productId = Integer.parseInt(s[0]);
                        var quantity = Integer.parseInt(s[1]);
                        var product = DataSeed.Products().stream()
                                .filter(a -> a.getId() == productId).findFirst().orElse(null);
                        if (product == null) {
                            throw new Exception("Product with id: " + productId + " not found");
                        }
                        var checkItem = new CheckItem(id++, product, quantity);
                        checkItems.add(checkItem);
                    }
                }
            }
        }

        return checkItems;
    }

    static NormalinoList<Check> applyFilterIfExist(NormalinoList<Check> checks, ArgumentsFinder argumentsFinder) {
        var value = argumentsFinder.findStringOrNull(Constants.Keys.FILTER_ID);
        if (value != null) {
            var idList = new NormalinoList<Integer>();
            var values = value.split(",");
            for (String s : values) {
                try {
                    var intValue = Integer.parseInt(s);
                    idList.add(intValue);
                } catch (Exception ignored) {
                }
            }
            return checks.stream()
                    .filter(check -> idList.contains(check.getId()))
                    .collect(Collectors.toCollection(NormalinoList<Check>::new));
        }
        return checks;
    }
}
