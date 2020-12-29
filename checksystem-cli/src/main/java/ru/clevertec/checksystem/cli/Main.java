package ru.clevertec.checksystem.cli;

import ru.clevertec.checksystem.core.DataSeed;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.check.CheckItem;
import ru.clevertec.checksystem.core.io.printer.CheckPrinter;
import ru.clevertec.checksystem.core.io.printer.strategy.HtmlCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.printer.strategy.PdfCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.printer.strategy.TextCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.reader.factory.CheckReaderCreator;
import ru.clevertec.checksystem.core.io.writer.factory.CheckWriterCreator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    static Pattern pattern = Pattern.compile("^-(?<key>.+)=(?<value>.+)$");

    static String PARAM_KEY_MODE = "mode";
    static String PARAM_KEY_FILE_DESERIALIZE_FORMAT = "file-deserialize-format";
    static String PARAM_KEY_FILE_DESERIALIZE_PATH = "file-deserialize-path";
    static String PARAM_KEY_FILE_SERIALIZE_FORMAT = "file-serialize-format";
    static String PARAM_KEY_FILE_SERIALIZE_PATH = "file-serialize-path";
    static String PARAM_KEY_FILE_PRINT_FORMAT = "file-print-format";
    static String PARAM_KEY_FILE_PRINT_PATH = "file-print-path";
    static String PARAM_KEY_FILTER_ID = "filter-id";

    public static void main(String[] args) throws Exception {

        var checks = new ArrayList<Check>();

        var mode = findParameterOrThrow(args, PARAM_KEY_MODE);

        switch (mode) {
            case "generate" -> {
                var check = generateCheck(args);
                var checkItems = generateCheckItems(args);
                if (checkItems.isEmpty()) {
                    throw new Exception("Check items not defined");
                }
                check.setItems(checkItems);
                applyCheckDiscounts(check, args);
                applyCheckItemsDiscounts(check, args);
                checks.add(check);
            }
            case "file-deserialize" -> {
                var format = findParameterOrThrow(args, PARAM_KEY_FILE_DESERIALIZE_FORMAT);
                var path = findParameterOrThrow(args, PARAM_KEY_FILE_DESERIALIZE_PATH);
                var checkReader = new CheckReaderCreator().create(format);
                var data = checkReader.readMany(Files.readAllBytes(Path.of(path)));
                checks.addAll(applyFilterIfExist(data, args));
            }
            case "pre-defined" -> checks.addAll(applyFilterIfExist(DataSeed.Checks(), args));
        }

        if (hasEnabledSerializeFlag(args)) {
            var format = findParameterOrThrow(args, PARAM_KEY_FILE_SERIALIZE_FORMAT);
            var path = findParameterOrThrow(args, PARAM_KEY_FILE_SERIALIZE_PATH);
            var checkWriter = new CheckWriterCreator().create(format);
            Files.write(Path.of(path), checkWriter.write(checks));
            System.out.println("Serialized to file: OK");
        }

        if (hasEnabledFilePrintFlag(args)) {
            var printFormat = findParameterOrThrow(args, PARAM_KEY_FILE_PRINT_FORMAT);
            var printPath = findParameterOrThrow(args, PARAM_KEY_FILE_PRINT_PATH);
            var checkPrinter = new CheckPrinter();
            checkPrinter.setChecks(checks);
            switch (printFormat) {
                case "text" -> checkPrinter.setStrategy(new TextCheckPrintStrategy());
                case "html" -> checkPrinter.setStrategy(new HtmlCheckPrintStrategy());
                case "pdf" -> checkPrinter.setStrategy(new PdfCheckPrintStrategy());
                default -> throw new Exception("Print format '" + printFormat + " 'not supported");
            }
            var data = checkPrinter.printRaw();
            Files.write(Path.of(printPath), data);
            System.out.println("Printed to file: OK");
        }

        var printer = new CheckPrinter();
        printer.setChecks(checks);
        printer.setStrategy(new TextCheckPrintStrategy());

        for (var item : printer.print()) {
            System.out.println(item.getData());
        }
    }

    static void applyCheckDiscounts(Check check, String[] args)
            throws Exception {
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

    static void applyCheckItemsDiscounts(Check check, String[] args)
            throws Exception {
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
        var list = new ArrayList<String>();
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
        var list = new ArrayList<CheckItemDiscountPair>();
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
        var checkItems = new ArrayList<CheckItem>();
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

    static boolean hasEnabledSerializeFlag(String[] args) {
        for (var arg : args) {
            var matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                var key = matcher.group("key");
                var value = matcher.group("value");
                if (key.equals("file-serialize") && (value.equals("true") || value.equals("1"))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasEnabledFilePrintFlag(String[] args) {
        for (var arg : args) {
            var matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                var key = matcher.group("key");
                var value = matcher.group("value");
                if (key.equals("file-print") && (value.equals("true") || value.equals("1"))) {
                    return true;
                }
            }
        }
        return false;
    }

    static List<Check> applyFilterIfExist(List<Check> checks, String[] args) {
        var value = findParameter(args, PARAM_KEY_FILTER_ID);
        if (value != null) {
            var idList = new ArrayList<Integer>();
            var values = value.split(",");
            for (String s : values) {
                try {
                    var intValue = Integer.parseInt(s);
                    idList.add(intValue);
                } catch (Exception ignored) {
                }
            }
            return checks.stream().filter(check -> idList.contains(check.getId())).collect(Collectors.toList());
        }
        return checks;
    }

    static String findParameter(String[] args, String key) {
        for (var arg : args) {
            var matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                var argKey = matcher.group("key");
                if (argKey.equals(key)) {
                    return matcher.group("value").trim();
                }
            }
        }
        return null;
    }

    static String findParameterOrThrow(String[] args, String key) throws Exception {
        var value = findParameter(args, key);
        if (value == null) {
            throw new Exception("Parameter '" + key + "' not defined");
        }
        return value;
    }

    static byte[] concatBytes(byte[]... bytesArrays) throws IOException {
        var os = new ByteArrayOutputStream();
        for (var bytes : bytesArrays) {
            os.write(bytes);
        }
        return os.toByteArray();
    }
}
