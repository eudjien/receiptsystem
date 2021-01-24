package ru.clevertec.checksystem.cli;

import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.DataSeed;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.check.CheckItem;
import ru.clevertec.checksystem.core.factory.ServiceFactory;
import ru.clevertec.checksystem.core.service.CheckIoService;
import ru.clevertec.checksystem.core.service.CheckPrintingService;
import ru.clevertec.checksystem.core.service.ICheckIoService;
import ru.clevertec.checksystem.core.service.ICheckPrintingService;
import ru.clevertec.normalino.list.NormalinoList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static ArgumentsFinder finder;
    private static boolean isServicesUseProxy;

    public static void main(String[] args) throws Exception {

        finder = new ArgumentsFinder(args);

        isServicesUseProxy = finder.findFirstBoolOrDefault(Constants.Keys.PROXIED_SERVICES);

        var checks = new NormalinoList<Check>();

        var mode = finder.findFirstStringOrThrow(Constants.Keys.MODE);

        switch (mode) {
            case Constants.Modes.GENERATE -> generate(checks);
            case Constants.Modes.FILE_DESERIALIZE -> fileDeserialize(checks);
            case Constants.Modes.PRE_DEFINED -> checks.addAll(applyFilterIfExist(DataSeed.Checks()));
        }

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_SERIALIZE)) {
            fileSerialize(checks);
        }

        if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT)) {
            filePrint(checks);
        }
    }

    static void generate(NormalinoList<Check> checks) throws Exception {
        var check = generateCheck();
        var checkItems = generateCheckItems();
        if (checkItems.isEmpty()) {
            throw new Exception("Check items not defined");
        }
        check.setItems(checkItems);
        applyCheckDiscounts(check);
        applyCheckItemsDiscounts(check);
        checks.add(check);
    }

    static void fileDeserialize(NormalinoList<Check> checks) throws Exception {

        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_FORMAT);
        var srcPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_DESERIALIZE_PATH);
        ICheckIoService ioService = ServiceFactory.instance(CheckIoService.class, isServicesUseProxy);
        var deserializedChecks = ioService.deserializeFromFile(srcPath, format);
        checks.addAll(applyFilterIfExist(deserializedChecks));
    }

    static void fileSerialize(NormalinoList<Check> checks) throws Exception {

        var format = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_FORMAT);
        var destPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_SERIALIZE_PATH);
        ICheckIoService ioService = ServiceFactory.instance(CheckIoService.class, isServicesUseProxy);
        ioService.serializeToFile(checks, destPath, format);
    }

    static void filePrint(NormalinoList<Check> checks) throws Exception {

        var printFormat = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_FORMAT);
        var destPath = finder.findFirstStringOrThrow(Constants.Keys.FILE_PRINT_PATH);
        ICheckPrintingService printingService = ServiceFactory.instance(CheckPrintingService.class, isServicesUseProxy);

        switch (printFormat.toLowerCase()) {
            case "text" -> printingService.printToTextFile(checks, destPath);
            case "html" -> printingService.printToHtmlFile(checks, destPath);
            case "pdf" -> {
                if (finder.findFirstBoolOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE)) {
                    printingService.printToPdfFile(checks, destPath,
                            finder.findFirstStringOrNull(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_PATH),
                            finder.findFirstIntOrDefault(Constants.Keys.FILE_PRINT_PDF_TEMPLATE_OFFSET));
                } else {
                    printingService.printToPdfFile(checks, destPath);
                }
            }
            default -> throw new Exception("Print format '" + printFormat + " 'not supported");
        }
    }

    static void applyCheckDiscounts(Check check) throws Exception {
        var discounts = DataSeed.CheckDiscounts();
        var discountKeys = getDiscountCardsFromArgs();
        for (var key : discountKeys) {
            var discount = discounts.get(key);
            if (discount == null) {
                throw new Exception("Check discount '" + key + "' not found");
            }
            check.addDiscount(discount);
        }
    }

    static void applyCheckItemsDiscounts(Check check) throws Exception {
        var discounts = DataSeed.CheckItemDiscounts();
        var checkItemDiscountPair = getDiscountCardItemsFromArgs();
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

    static String[] getDiscountCardsFromArgs() {

        var list = new NormalinoList<String>();

        if (finder.getArguments() == null || finder.getArguments().isEmpty()) {
            return list.toArray(String[]::new);
        }

        var dCheckArg = finder.argumentByKey(Constants.Keys.CHECK_DISCOUNT);

        if (dCheckArg != null) {
            if (dCheckArg.hasValues()) {
                list.addAll(dCheckArg.getValues());
            }
        }

        return list.toArray(String[]::new);
    }

    static CheckItemDiscountPair[] getDiscountCardItemsFromArgs() {

        var list = new NormalinoList<CheckItemDiscountPair>();

        if (finder.getArguments() == null || finder.getArguments().isEmpty()) {
            return list.toArray(CheckItemDiscountPair[]::new);
        }

        var pattern = Pattern.compile("^(?<id>\\d+):(?<key>\\w+)$");

        var dCheckItemArg = finder.argumentByKey(Constants.Keys.CHECK_ITEM_DISCOUNT);

        if (dCheckItemArg != null && dCheckItemArg.hasValues()) {

            for (var value : dCheckItemArg.getValues()) {
                var matcher = pattern.matcher(value);
                if (matcher.find()) {
                    var productId = Integer.parseInt(matcher.group("id"));
                    var discountKey = matcher.group("key");
                    list.add(new CheckItemDiscountPair(productId, discountKey));
                }
            }
        }

        return list.toArray(CheckItemDiscountPair[]::new);
    }

    static Check generateCheck() throws ParseException {

        var check = new Check();

        for (var arg : finder.getArguments()) {

            if (arg.getKey().equals("id")) {
                check.setId(Integer.parseInt(arg.firstValue()));
            }
            if (arg.getKey().equals("name")) {
                check.setName(arg.firstValue());
            }
            if (arg.getKey().equals("description")) {
                check.setDescription(arg.firstValue());
            }
            if (arg.getKey().equals("address")) {
                check.setAddress(arg.firstValue());
            }
            if (arg.getKey().equals("cashier")) {
                check.setCashier(arg.firstValue());
            }
            if (arg.getKey().equals("phoneNumber")) {
                check.setPhoneNumber(arg.firstValue());
            }
            if (arg.getKey().equals("date")) {
                var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
                check.setDate(dateFormatter.parse(arg.firstValue()));
            }
        }

        return check;
    }

    static List<CheckItem> generateCheckItems() throws Exception {

        var checkItems = new NormalinoList<CheckItem>();
        var id = 1;

        for (var arg : finder.getArguments()) {
            if (arg.getKey().equals("ci")) {
                var s = arg.firstValue().split(":");
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

        return checkItems;
    }

    static NormalinoList<Check> applyFilterIfExist(NormalinoList<Check> checks) {
        var value = finder.findFirstStringOrNull(Constants.Keys.FILTER_ID);
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
