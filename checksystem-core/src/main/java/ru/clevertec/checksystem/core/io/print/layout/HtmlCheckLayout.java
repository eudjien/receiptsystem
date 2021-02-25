package ru.clevertec.checksystem.core.io.print.layout;

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

public class HtmlCheckLayout extends AbstractCheckLayout {

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
    public byte[] getLayoutData(Check check) throws IOException {
        return createCheckHtml(check);
    }

    @Override
    public byte[] getAllLayoutData(Collection<Check> checks) throws IOException {

        var byteArrayOutputStream = new ByteArrayOutputStream();

        for (var check : checks)
            byteArrayOutputStream.write(createCheckHtml(check));

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] createCheckHtml(Check check) throws IOException {

        var commonHtml = loadHtmlTemplate("template.html");
        var itemHtml = loadHtmlTemplate("template-item.html");
        var discountedItemHtml = loadHtmlTemplate("template-discounted-item.html");

        var html = replaceContentValues(check, commonHtml, itemHtml, discountedItemHtml);

        return html.getBytes(StandardCharsets.UTF_8);
    }

    private String loadHtmlTemplate(String fileName) throws IOException {

        var path = Path.of("template", "html", fileName);

        var templateInputStream = HtmlCheckLayout.class.getClassLoader()
                .getResourceAsStream(Path.of("template", "html", "default", fileName).toString());

        if (templateInputStream == null)
            throw new IOException(path.toString());

        return new String(templateInputStream.readAllBytes());
    }

    private String replaceContentValues(Check check, String commonHtml, String itemHtml, String discountedItem) {

        var dateFormatter = new SimpleDateFormat(PATTERN_DATE);
        var timeFormatter = new SimpleDateFormat(PATTERN_TIME);

        return Pattern.compile(REPLACEMENT_PATTERN).matcher(commonHtml).replaceAll(mr -> switch (mr.group()) {
            case REPLACEMENT_NAME -> Matcher.quoteReplacement(check.getName());
            case REPLACEMENT_DESCRIPTION -> Matcher.quoteReplacement(check.getDescription());
            case REPLACEMENT_ADDRESS -> Matcher.quoteReplacement(check.getAddress());
            case REPLACEMENT_PHONE_NUMBER -> Matcher.quoteReplacement(check.getPhoneNumber());
            case REPLACEMENT_CASHIER -> Matcher.quoteReplacement(check.getCashier());
            case REPLACEMENT_DATE -> Matcher.quoteReplacement(dateFormatter.format(check.getDate()));
            case REPLACEMENT_TIME -> Matcher.quoteReplacement(timeFormatter.format(check.getDate()));
            case REPLACEMENT_SUBTOTAL -> Matcher.quoteReplacement(
                    getCurrency() + check.subTotalAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_DISCOUNTS -> Matcher.quoteReplacement(
                    getCurrency() + check.discountsAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_TOTAL -> Matcher.quoteReplacement(
                    getCurrency() + check.totalAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM -> Matcher.quoteReplacement(getCheckItemsContent(check, itemHtml, discountedItem));
            default -> mr.group();
        });
    }

    private String replaceItemValues(CheckItem checkItem, String itemHtml) {
        return Pattern.compile(REPLACEMENT_PATTERN).matcher(itemHtml).replaceAll(rm -> switch (rm.group()) {
            case REPLACEMENT_ITEM_QUANTITY -> Matcher.quoteReplacement(String.valueOf(checkItem.getQuantity()));
            case REPLACEMENT_ITEM_NAME -> Matcher.quoteReplacement(checkItem.getProduct().getName());
            case REPLACEMENT_ITEM_PRICE -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.getProduct().getPrice().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_SUBTOTAL -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.subTotalAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_DISCOUNTS -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.discountsAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_TOTAL -> Matcher.quoteReplacement(
                    getCurrency() + checkItem.totalAmount().setScale(getScale(), RoundingMode.CEILING));
            default -> rm.group();
        });
    }

    private String getCheckItemsContent(Check check, String itemHtml, String discountedItem) {
        if (check.getCheckItems() != null) {
            var stringBuilder = new StringBuilder();
            for (var checkItem : check.getCheckItems()) {
                if (checkItem.discountsAmount().doubleValue() > 0) {
                    stringBuilder.append(replaceItemValues(checkItem, discountedItem));
                } else {
                    stringBuilder.append(replaceItemValues(checkItem, itemHtml));
                }
            }
            return stringBuilder.toString();
        }
        return "";
    }
}
