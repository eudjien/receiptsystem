package ru.clevertec.checksystem.core.io.print.layout;

import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextReceiptLayout extends AbstractReceiptLayout {

    private static final int MIN_SPACE_COUNT = 1;

    private int spacesNumber = 4;

    public TextReceiptLayout() {
    }

    @Override
    public byte[] getLayoutData(Receipt receipt) {
        return createReceiptText(receipt);
    }

    @Override
    public byte[] getAllLayoutData(Collection<Receipt> receipts) {
        return receipts.stream().map(receipt -> new String(createReceiptText(receipt)))
                .reduce((a, b) -> a + '\n' + b)
                .map(String::getBytes)
                .orElse(null);
    }

    public int getSpacesNumber() {
        return spacesNumber;
    }

    public void setSpacesNumber(int spacesNumber) {
        ThrowUtils.Argument.lessThan("spacesNumber", spacesNumber, MIN_SPACE_COUNT);
        this.spacesNumber = spacesNumber;
    }

    private byte[] createReceiptText(Receipt receipt) {

        int maxProductNameLength = getNameLengths(receipt).max(Integer::compareTo).orElse(0);
        int maxPriceLength = getPriceLengths(receipt).max(Integer::compareTo).orElse(0);
        int maxQuantityLength = getQuantityLengths(receipt).max(Integer::compareTo).orElse(0);
        int maxAmountLength = getAmountLengths(receipt).max(Integer::compareTo).orElse(0);

        var strBuilder = new StringBuilder();

        int fullWidth = maxProductNameLength + maxPriceLength
                + maxQuantityLength + maxAmountLength + (getSpacesNumber() * 3);
        double halfWidth = fullWidth / 2d;

        appendLineCenter(strBuilder, fullWidth, cropIfOutOfBounds(receipt.getName(), fullWidth));
        strBuilder.append("\n");

        appendLineCenter(strBuilder, fullWidth, cropIfOutOfBounds(receipt.getDescription(), fullWidth));
        strBuilder.append("\n");

        appendLineCenter(strBuilder, fullWidth, cropIfOutOfBounds(receipt.getAddress(), fullWidth));
        strBuilder.append("\n");

        appendLineCenter(strBuilder, fullWidth, cropIfOutOfBounds(receipt.getPhoneNumber(), fullWidth));
        strBuilder.append("\n\n");

        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        var timeFormatter = new SimpleDateFormat("hh:mm");

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth),
                cropIfOutOfBounds("CASHIER: " + receipt.getCashier(), (int) Math.floor(halfWidth)));

        var dateStr = cropIfOutOfBounds("DATE: " + dateFormatter.format(receipt.getDate()),
                (int) Math.round(halfWidth));
        appendLineRight(strBuilder, (int) Math.round(halfWidth),
                cropIfOutOfBounds(dateStr, (int) Math.round(halfWidth)));
        strBuilder.append("\n");

        var timeStr = cropIfOutOfBounds("TIME: " + timeFormatter.format(receipt.getDate()),
                (int) Math.ceil(halfWidth));
        appendLineRight(strBuilder, fullWidth, timeStr + " ".repeat(dateStr.length() - timeStr.length()));

        strBuilder.append("\n");

        strBuilder.append("-".repeat(fullWidth));

        strBuilder.append("\n");

        appendLineCenter(strBuilder, maxQuantityLength, getHeaderQuantity());
        strBuilder.append(" ".repeat(getSpacesNumber()));

        appendLineLeft(strBuilder, maxProductNameLength, getHeaderName());
        strBuilder.append(" ".repeat(getSpacesNumber()));

        appendLineRight(strBuilder, maxPriceLength, getHeaderPrice());
        strBuilder.append(" ".repeat(getSpacesNumber()));

        appendLineRight(strBuilder, maxAmountLength, getHeaderTotal());

        strBuilder.append("\n\n");

        for (var item : receipt.getReceiptItems()) {

            // Quantity
            appendLineCenter(strBuilder, maxQuantityLength, item.getQuantity() + "");
            strBuilder.append(" ".repeat(getSpacesNumber()));

            // Name
            appendLineLeft(strBuilder, maxProductNameLength, item.getProduct().getName());
            strBuilder.append(" ".repeat(getSpacesNumber()));

            // Price
            appendLineRight(strBuilder, maxPriceLength, getCurrency() + item.getProduct().getPrice()
                    .setScale(getScale(), RoundingMode.CEILING));
            strBuilder.append(" ".repeat(getSpacesNumber()));

            // Sum
            appendLineRight(strBuilder, maxAmountLength, getCurrency() + item.subTotalAmount()
                    .setScale(getScale(), RoundingMode.CEILING));
            strBuilder.append("\n");

            // Discount
            if (item.discountsAmount().doubleValue() > 0) {
                appendLineRight(strBuilder, fullWidth,
                        "Discount: -" + getCurrency() + item.discountsAmount()
                                .setScale(getScale(), RoundingMode.CEILING)
                                + " = " + getCurrency() + item.totalAmount().setScale(getScale(), RoundingMode.CEILING));
                strBuilder.append("\n");
            }
        }

        strBuilder.append("-".repeat(fullWidth));
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth), "SUBTOTAL");
        appendLineRight(strBuilder, (int) Math.round(halfWidth), getCurrency() + receipt.subTotalAmount()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth), "DISCOUNTS");
        appendLineRight(strBuilder, (int) Math.round(halfWidth), getCurrency() + receipt.discountsAmount()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth), "TOTAL");
        appendLineRight(strBuilder, (int) Math.round(halfWidth), getCurrency() + receipt.totalAmount()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        return strBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void appendLineCenter(StringBuilder strBuilder, long maxColumnLength, String text) {
        double half = (maxColumnLength - text.length()) / 2d;
        strBuilder.append(" ".repeat((int) Math.floor(half)));
        strBuilder.append(text);
        strBuilder.append(" ".repeat((int) Math.round(half)));
    }

    private void appendLineLeft(StringBuilder strBuilder, int maxColumnLength, String text) {
        strBuilder.append(text);
        strBuilder.append(" ".repeat(maxColumnLength - text.length()));
    }

    private void appendLineRight(StringBuilder strBuilder, long maxColumnLength, String text) {
        strBuilder.append(" ".repeat((int) maxColumnLength - text.length()));
        strBuilder.append(text);
    }

    private String cropIfOutOfBounds(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }

    private Stream<Integer> getQuantityLengths(Receipt receipt) {
        var list = receipt.getReceiptItems().stream().map(a -> (a.getQuantity() + "").length())
                .collect(Collectors.toList());
        list.add(getHeaderQuantity().length());
        return list.stream();
    }

    private Stream<Integer> getPriceLengths(Receipt receipt) {
        var list = receipt.getReceiptItems().stream()
                .map(a -> getCurrency().length() + (a.getProduct() != null ? a.getProduct().getPrice()
                        .setScale(getScale(), RoundingMode.CEILING).toString().length() : 0))
                .collect(Collectors.toList());
        list.add(getHeaderPrice().length());
        return list.stream();
    }

    private Stream<Integer> getNameLengths(Receipt receipt) {
        var list = receipt.getReceiptItems().stream()
                .map(a -> a.getProduct() != null ? a.getProduct().getName().length() : 0)
                .collect(Collectors.toList());
        list.add(getHeaderName().length());
        return list.stream();
    }

    private Stream<Integer> getAmountLengths(Receipt receipt) {
        var list = receipt.getReceiptItems().stream()
                .map(a -> (getCurrency() + a.totalAmount()
                        .setScale(getScale(), RoundingMode.CEILING).toString()).length())
                .collect(Collectors.toList());
        list.add(getHeaderTotal().length());
        return list.stream();
    }
}
