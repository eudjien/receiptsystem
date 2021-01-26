package ru.clevertec.checksystem.core.io.print.strategy;

import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.check.CheckItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlCheckPrintStrategy extends CheckPrintStrategy {

    private static final String REPLACEMENT_NAME = "%NAME%";
    private static final String REPLACEMENT_DESCRIPTION = "%DESCRIPTION%";
    private static final String REPLACEMENT_ADDRESS = "%ADDRESS%";
    private static final String REPLACEMENT_PHONE_NUMBER = "%PHONE-NUMBER%";
    private static final String REPLACEMENT_CASHIER = "%CASHIER%";
    private static final String REPLACEMENT_DATE = "%DATE%";
    private static final String REPLACEMENT_TIME = "%TIME%";
    private static final String REPLACEMENT_SUBTOTAL = "%SUBTOTAL%";
    private static final String REPLACEMENT_DISCOUNTS = "%DISCOUNTS%";
    private static final String REPLACEMENT_TOTAL = "%TOTAL%";
    private static final String REPLACEMENT_ITEM = "%ITEM%";
    private static final String REPLACEMENT_ITEM_QUANTITY = "%ITEM-QUANTITY%";
    private static final String REPLACEMENT_ITEM_NAME = "%ITEM-NAME%";
    private static final String REPLACEMENT_ITEM_PRICE = "%ITEM-PRICE%";
    private static final String REPLACEMENT_ITEM_SUBTOTAL = "%ITEM-SUBTOTAL%";
    private static final String REPLACEMENT_ITEM_DISCOUNTS = "%ITEM-DISCOUNTS%";
    private static final String REPLACEMENT_ITEM_TOTAL = "%ITEM-TOTAL%";

    private static final String REPLACEMENT_PATTERN = "%[\\w-]+%";

    private static final String PATTERN_DATE = "dd.MM.yyyy";
    private static final String PATTERN_TIME = "hh:mm";

    @Override
    public byte[] getData(Check check) throws IOException {
        return createCheck(check);
    }

    @Override
    public byte[] getCombinedData(Collection<Check> checkCollection) throws IOException {

        var byteArrayOutputStream = new ByteArrayOutputStream();

        for (var check : checkCollection) {
            byteArrayOutputStream.write(createCheck(check));
        }

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] createCheck(Check check) throws IOException {

        var templateHtml = loadHtmlTemplate("template.html");
        var itemHtml = loadHtmlTemplate("template-item.html");
        var discountedItemHtml = loadHtmlTemplate("template-discounted-item.html");

        var html = replaceContentValues(check, templateHtml, itemHtml, discountedItemHtml);

        return html.getBytes(StandardCharsets.UTF_8);
    }

    private String loadHtmlTemplate(String fileName) throws IOException {

        var path = Path.of("template", "html", fileName);

        var templateInputStream = HtmlCheckPrintStrategy.class.getClassLoader()
                .getResourceAsStream(Path.of("template", "html", "default", fileName).toString());

        if (templateInputStream == null) {
            throw new IOException(path.toString());
        }

        return new String(templateInputStream.readAllBytes());
    }

    private String replaceContentValues(Check check, String content, String item, String discountedItem) {

        var dateFormatter = new SimpleDateFormat(PATTERN_DATE);
        var timeFormatter = new SimpleDateFormat(PATTERN_TIME);

        return Pattern.compile(REPLACEMENT_PATTERN).matcher(content).replaceAll(mr -> switch (mr.group()) {
            case REPLACEMENT_NAME -> Matcher.quoteReplacement(check.getName());
            case REPLACEMENT_DESCRIPTION -> Matcher.quoteReplacement(check.getDescription());
            case REPLACEMENT_ADDRESS -> Matcher.quoteReplacement(check.getAddress());
            case REPLACEMENT_PHONE_NUMBER -> Matcher.quoteReplacement(check.getPhoneNumber());
            case REPLACEMENT_CASHIER -> Matcher.quoteReplacement(check.getCashier());
            case REPLACEMENT_DATE -> Matcher.quoteReplacement(dateFormatter.format(check.getDate()));
            case REPLACEMENT_TIME -> Matcher.quoteReplacement(timeFormatter.format(check.getDate()));
            case REPLACEMENT_SUBTOTAL -> Matcher.quoteReplacement(
                    getCurrency() + check.subTotal().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_DISCOUNTS -> Matcher.quoteReplacement(
                    getCurrency() + check.discountSum().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_TOTAL -> Matcher.quoteReplacement(
                    getCurrency() + check.total().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM -> Matcher.quoteReplacement(getCheckItemsContent(check, item, discountedItem));
            default -> mr.group();
        });
    }

    private String replaceItemValues(CheckItem checkItem, String item) {
        return Pattern.compile(REPLACEMENT_PATTERN).matcher(item).replaceAll(rm -> switch (rm.group()) {
            case REPLACEMENT_ITEM_QUANTITY -> Matcher.quoteReplacement(String.valueOf(checkItem.getQuantity()));
            case REPLACEMENT_ITEM_NAME -> Matcher.quoteReplacement(checkItem.getProduct().getName());
            case REPLACEMENT_ITEM_PRICE -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.getProduct().getPrice().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_SUBTOTAL -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.subTotal().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_DISCOUNTS -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.discountsSum().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_TOTAL -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.total().setScale(getScale(), RoundingMode.CEILING));
            default -> rm.group();
        });
    }

    private String getCheckItemsContent(Check check, String item, String discountedItem) {
        if (check.getCheckItems() != null) {
            var stringBuilder = new StringBuilder();
            for (var checkItem : check.getCheckItems()) {
                if (checkItem.discountsSum().doubleValue() > 0) {
                    stringBuilder.append(replaceItemValues(checkItem, discountedItem));
                } else {
                    stringBuilder.append(replaceItemValues(checkItem, item));
                }
            }
            return stringBuilder.toString();
        }
        return "";
    }
}
