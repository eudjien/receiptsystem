package ru.clevertec.checksystem.core.io.print.layout;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlReceiptLayout extends AbstractReceiptLayout {

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
    public byte[] getLayoutData(Receipt receipt) throws IOException {
        return createReceiptHtml(receipt);
    }

    @Override
    public byte[] getAllLayoutData(Collection<Receipt> receipts) throws IOException {

        var byteArrayOutputStream = new ByteArrayOutputStream();

        for (var receipt : receipts)
            byteArrayOutputStream.write(createReceiptHtml(receipt));

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] createReceiptHtml(Receipt receipt) throws IOException {

        var commonHtml = loadHtmlTemplate("template.html");
        var itemHtml = loadHtmlTemplate("template-item.html");
        var discountedItemHtml = loadHtmlTemplate("template-discounted-item.html");

        var html = replaceContentValues(receipt, commonHtml, itemHtml, discountedItemHtml);

        return html.getBytes(StandardCharsets.UTF_8);
    }

    private String loadHtmlTemplate(String fileName) throws IOException {

        var path = Path.of("template", "html", fileName);

        var templateInputStream = HtmlReceiptLayout.class.getClassLoader()
                .getResourceAsStream(Path.of("template", "html", "default", fileName).toString());

        if (templateInputStream == null)
            throw new IOException(path.toString());

        return new String(templateInputStream.readAllBytes());
    }

    private String replaceContentValues(Receipt receipt, String commonHtml, String itemHtml, String discountedItem) {

        var dateFormatter = new SimpleDateFormat(PATTERN_DATE);
        var timeFormatter = new SimpleDateFormat(PATTERN_TIME);

        return Pattern.compile(REPLACEMENT_PATTERN).matcher(commonHtml).replaceAll(mr -> switch (mr.group()) {
            case REPLACEMENT_NAME -> Matcher.quoteReplacement(receipt.getName());
            case REPLACEMENT_DESCRIPTION -> Matcher.quoteReplacement(receipt.getDescription());
            case REPLACEMENT_ADDRESS -> Matcher.quoteReplacement(receipt.getAddress());
            case REPLACEMENT_PHONE_NUMBER -> Matcher.quoteReplacement(receipt.getPhoneNumber());
            case REPLACEMENT_CASHIER -> Matcher.quoteReplacement(receipt.getCashier());
            case REPLACEMENT_DATE -> Matcher.quoteReplacement(dateFormatter.format(receipt.getDate()));
            case REPLACEMENT_TIME -> Matcher.quoteReplacement(timeFormatter.format(receipt.getDate()));
            case REPLACEMENT_SUBTOTAL -> Matcher.quoteReplacement(
                    getCurrency() + receipt.subTotalAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_DISCOUNTS -> Matcher.quoteReplacement(
                    getCurrency() + receipt.discountsAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_TOTAL -> Matcher.quoteReplacement(
                    getCurrency() + receipt.totalAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM -> Matcher.quoteReplacement(getReceiptItemsContent(receipt, itemHtml, discountedItem));
            default -> mr.group();
        });
    }

    private String replaceItemValues(ReceiptItem receiptItem, String itemHtml) {
        return Pattern.compile(REPLACEMENT_PATTERN).matcher(itemHtml).replaceAll(rm -> switch (rm.group()) {
            case REPLACEMENT_ITEM_QUANTITY -> Matcher.quoteReplacement(String.valueOf(receiptItem.getQuantity()));
            case REPLACEMENT_ITEM_NAME -> Matcher.quoteReplacement(receiptItem.getProduct().getName());
            case REPLACEMENT_ITEM_PRICE -> Matcher.quoteReplacement(
                    getCurrency() + receiptItem.getProduct().getPrice().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_SUBTOTAL -> Matcher.quoteReplacement(
                    getCurrency() + receiptItem.subTotalAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_DISCOUNTS -> Matcher.quoteReplacement(
                    getCurrency() + receiptItem.discountsAmount().setScale(getScale(), RoundingMode.CEILING));
            case REPLACEMENT_ITEM_TOTAL -> Matcher.quoteReplacement(
                    getCurrency() + receiptItem.totalAmount().setScale(getScale(), RoundingMode.CEILING));
            default -> rm.group();
        });
    }

    private String getReceiptItemsContent(Receipt receipt, String itemHtml, String discountedItem) {
        if (receipt.getReceiptItems() != null) {
            var stringBuilder = new StringBuilder();
            for (var receiptItem : receipt.getReceiptItems()) {
                if (receiptItem.discountsAmount().doubleValue() > 0) {
                    stringBuilder.append(replaceItemValues(receiptItem, discountedItem));
                } else {
                    stringBuilder.append(replaceItemValues(receiptItem, itemHtml));
                }
            }
            return stringBuilder.toString();
        }
        return "";
    }
}
