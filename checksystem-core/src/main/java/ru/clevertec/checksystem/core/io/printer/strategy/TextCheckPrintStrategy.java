package ru.clevertec.checksystem.core.io.printer.strategy;

import ru.clevertec.checksystem.core.check.Check;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextCheckPrintStrategy extends CheckPrintStrategy {

    private int spaceCount = 4;

    public TextCheckPrintStrategy() {
    }

    @Override
    public byte[] getData(Check check) {
        return createCheck(check);
    }

    @Override
    public byte[] getCombinedData(Check[] checks) {
        return Arrays.stream(checks).map(check -> new String(createCheck(check)))
                .reduce((a, b) -> a + '\n' + b).map(String::getBytes).orElse(null);
    }

    public int getSpaceCount() {
        return spaceCount;
    }

    public void setSpaceCount(int spaceCount) throws Exception {
        if (spaceCount < 1) {
            throw new Exception("Space count cannot be less than 1");
        }
        this.spaceCount = spaceCount;
    }

    private byte[] createCheck(Check check) {

        int maxProductNameLength = getNameLengths(check).max(Integer::compareTo).orElse(0);
        int maxPriceLength = getPriceLengths(check).max(Integer::compareTo).orElse(0);
        int maxQuantityLength = getQuantityLengths(check).max(Integer::compareTo).orElse(0);
        int maxAmountLength = getAmountLengths(check).max(Integer::compareTo).orElse(0);

        var strBuilder = new StringBuilder();

        var fullwidth = maxProductNameLength + maxPriceLength
                + maxQuantityLength + maxAmountLength + (getSpaceCount() * 3);
        double halfwidth = fullwidth / 2d;

        appendLineCenter(strBuilder, fullwidth, cropIfOutOfBounds(check.getName(), fullwidth));
        strBuilder.append("\n");

        appendLineCenter(strBuilder, fullwidth, cropIfOutOfBounds(check.getDescription(), fullwidth));
        strBuilder.append("\n");

        appendLineCenter(strBuilder, fullwidth, cropIfOutOfBounds(check.getAddress(), fullwidth));
        strBuilder.append("\n");

        appendLineCenter(strBuilder, fullwidth, cropIfOutOfBounds(check.getPhoneNumber(), fullwidth));
        strBuilder.append("\n\n");

        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        var timeFormatter = new SimpleDateFormat("hh:mm");

        appendLineLeft(strBuilder, (int) Math.floor(halfwidth),
                cropIfOutOfBounds("CASHIER: " + check.getCashier(), (int) Math.floor(halfwidth)));

        var dateStr = cropIfOutOfBounds("DATE: " + dateFormatter.format(check.getDate()),
                (int) Math.round(halfwidth));
        appendLineRight(strBuilder, (int) Math.round(halfwidth),
                cropIfOutOfBounds(dateStr, (int) Math.round(halfwidth)));
        strBuilder.append("\n");

        var timeStr = cropIfOutOfBounds("TIME: " + timeFormatter.format(check.getDate()),
                (int) Math.ceil(halfwidth));
        appendLineRight(strBuilder, fullwidth, timeStr + " ".repeat(dateStr.length() - timeStr.length()));

        strBuilder.append("\n");

        strBuilder.append("-".repeat(fullwidth));

        strBuilder.append("\n");

        appendLineCenter(strBuilder, maxQuantityLength, getHeaderQuantity());
        strBuilder.append(" ".repeat(getSpaceCount()));

        appendLineLeft(strBuilder, maxProductNameLength, getHeaderName());
        strBuilder.append(" ".repeat(getSpaceCount()));

        appendLineRight(strBuilder, maxPriceLength, getHeaderPrice());
        strBuilder.append(" ".repeat(getSpaceCount()));

        appendLineRight(strBuilder, maxAmountLength, getHeaderTotal());

        strBuilder.append("\n\n");

        for (var item : check.getItems()) {

            // Quantity
            appendLineCenter(strBuilder, maxQuantityLength, item.getQuantity() + "");
            strBuilder.append(" ".repeat(getSpaceCount()));

            // Name
            appendLineLeft(strBuilder, maxProductNameLength, item.getProduct().getName());
            strBuilder.append(" ".repeat(getSpaceCount()));

            // Price
            appendLineRight(strBuilder, maxPriceLength, getCurrency() + item.getProduct().getPrice()
                    .setScale(getScale(), RoundingMode.CEILING));
            strBuilder.append(" ".repeat(getSpaceCount()));

            // Sum
            appendLineRight(strBuilder, maxAmountLength, getCurrency() + item.subTotal()
                    .setScale(getScale(), RoundingMode.CEILING));
            strBuilder.append("\n");

            // Discount
            if (item.discountsSum().doubleValue() > 0) {
                appendLineRight(strBuilder, fullwidth,
                        "Discount: -" + getCurrency() + item.discountsSum()
                                .setScale(getScale(), RoundingMode.CEILING)
                                + " = " + getCurrency() + item.total().setScale(getScale(), RoundingMode.CEILING));
                strBuilder.append("\n");
            }
        }

        strBuilder.append("-".repeat(fullwidth));
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfwidth), "SUBTOTAL");
        appendLineRight(strBuilder, (int) Math.round(halfwidth), getCurrency() + check.subTotal()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfwidth), "DISCOUNTS");
        appendLineRight(strBuilder, (int) Math.round(halfwidth), getCurrency() + check.allDiscountsSum()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfwidth), "TOTAL");
        appendLineRight(strBuilder, (int) Math.round(halfwidth), getCurrency() + check.total()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        return strBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void appendLineCenter(StringBuilder strBuilder, int maxColumnLength, String text) {
        double half = (maxColumnLength - text.length()) / 2d;
        strBuilder.append(" ".repeat((int) Math.floor(half)));
        strBuilder.append(text);
        strBuilder.append(" ".repeat((int) Math.round(half)));
    }

    private void appendLineLeft(StringBuilder strBuilder, int maxColumnLength, String text) {
        strBuilder.append(text);
        strBuilder.append(" ".repeat(maxColumnLength - text.length()));
    }

    private void appendLineRight(StringBuilder strBuilder, int maxColumnLength, String text) {
        strBuilder.append(" ".repeat(maxColumnLength - text.length()));
        strBuilder.append(text);
    }

    private String cropIfOutOfBounds(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }

    private Stream<Integer> getQuantityLengths(Check check) {
        var list = check.getItems().stream().map(a -> (a.getQuantity() + "").length())
                .collect(Collectors.toList());
        list.add(getHeaderQuantity().length());
        return list.stream();
    }

    private Stream<Integer> getPriceLengths(Check check) {
        var list = check.getItems().stream()
                .map(a -> getCurrency().length() + (a.getProduct() != null ? a.getProduct().getPrice()
                        .setScale(getScale(), RoundingMode.CEILING).toString().length() : 0))
                .collect(Collectors.toList());
        list.add(getHeaderPrice().length());
        return list.stream();
    }

    private Stream<Integer> getNameLengths(Check check) {
        var list = check.getItems().stream()
                .map(a -> a.getProduct() != null ? a.getProduct().getName().length() : 0)
                .collect(Collectors.toList());
        list.add(getHeaderName().length());
        return list.stream();
    }

    private Stream<Integer> getAmountLengths(Check check) {
        var list = check.getItems().stream()
                .map(a -> (getCurrency() + a.total()
                        .setScale(getScale(), RoundingMode.CEILING).toString()).length())
                .collect(Collectors.toList());
        list.add(getHeaderTotal().length());
        return list.stream();
    }
}
