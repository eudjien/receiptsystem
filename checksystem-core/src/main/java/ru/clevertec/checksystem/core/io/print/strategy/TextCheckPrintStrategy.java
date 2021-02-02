package ru.clevertec.checksystem.core.io.print.strategy;

import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextCheckPrintStrategy extends CheckPrintStrategy {

    private static final int MIN_SPACE_COUNT = 1;

    private int spacesNumber = 4;

    public TextCheckPrintStrategy() {
    }

    @Override
    public byte[] getData(Check check) {
        return createCheck(check);
    }

    @Override
    public byte[] getCombinedData(Collection<Check> checkCollection) {
        return checkCollection.stream().map(check -> new String(createCheck(check)))
                .reduce((a, b) -> a + '\n' + b).map(String::getBytes).orElse(null);
    }

    public int getSpacesNumber() {
        return spacesNumber;
    }

    public void setSpacesNumber(int spacesNumber) throws ArgumentOutOfRangeException {
        ThrowUtils.Argument.lessThan("spacesNumber", spacesNumber, MIN_SPACE_COUNT);
        this.spacesNumber = spacesNumber;
    }

    private byte[] createCheck(Check check) {

        int maxProductNameLength = getNameLengths(check).max(Integer::compareTo).orElse(0);
        int maxPriceLength = getPriceLengths(check).max(Integer::compareTo).orElse(0);
        int maxQuantityLength = getQuantityLengths(check).max(Integer::compareTo).orElse(0);
        int maxAmountLength = getAmountLengths(check).max(Integer::compareTo).orElse(0);

        var strBuilder = new StringBuilder();

        var fullwidth = maxProductNameLength + maxPriceLength
                + maxQuantityLength + maxAmountLength + (getSpacesNumber() * 3);
        double halfWidth = fullwidth / 2d;

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

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth),
                cropIfOutOfBounds("CASHIER: " + check.getCashier(), (int) Math.floor(halfWidth)));

        var dateStr = cropIfOutOfBounds("DATE: " + dateFormatter.format(check.getDate()),
                (int) Math.round(halfWidth));
        appendLineRight(strBuilder, (int) Math.round(halfWidth),
                cropIfOutOfBounds(dateStr, (int) Math.round(halfWidth)));
        strBuilder.append("\n");

        var timeStr = cropIfOutOfBounds("TIME: " + timeFormatter.format(check.getDate()),
                (int) Math.ceil(halfWidth));
        appendLineRight(strBuilder, fullwidth, timeStr + " ".repeat(dateStr.length() - timeStr.length()));

        strBuilder.append("\n");

        strBuilder.append("-".repeat(fullwidth));

        strBuilder.append("\n");

        appendLineCenter(strBuilder, maxQuantityLength, getHeaderQuantity());
        strBuilder.append(" ".repeat(getSpacesNumber()));

        appendLineLeft(strBuilder, maxProductNameLength, getHeaderName());
        strBuilder.append(" ".repeat(getSpacesNumber()));

        appendLineRight(strBuilder, maxPriceLength, getHeaderPrice());
        strBuilder.append(" ".repeat(getSpacesNumber()));

        appendLineRight(strBuilder, maxAmountLength, getHeaderTotal());

        strBuilder.append("\n\n");

        for (var item : check.getCheckItems()) {

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
                appendLineRight(strBuilder, fullwidth,
                        "Discount: -" + getCurrency() + item.discountsAmount()
                                .setScale(getScale(), RoundingMode.CEILING)
                                + " = " + getCurrency() + item.totalAmount().setScale(getScale(), RoundingMode.CEILING));
                strBuilder.append("\n");
            }
        }

        strBuilder.append("-".repeat(fullwidth));
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth), "SUBTOTAL");
        appendLineRight(strBuilder, (int) Math.round(halfWidth), getCurrency() + check.subTotalAmount()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth), "DISCOUNTS");
        appendLineRight(strBuilder, (int) Math.round(halfWidth), getCurrency() + check.discountsAmount()
                .setScale(getScale(), RoundingMode.CEILING).toString());
        strBuilder.append("\n");

        appendLineLeft(strBuilder, (int) Math.floor(halfWidth), "TOTAL");
        appendLineRight(strBuilder, (int) Math.round(halfWidth), getCurrency() + check.totalAmount()
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
        var list = check.getCheckItems().stream().map(a -> (a.getQuantity() + "").length())
                .collect(Collectors.toList());
        list.add(getHeaderQuantity().length());
        return list.stream();
    }

    private Stream<Integer> getPriceLengths(Check check) {
        var list = check.getCheckItems().stream()
                .map(a -> getCurrency().length() + (a.getProduct() != null ? a.getProduct().getPrice()
                        .setScale(getScale(), RoundingMode.CEILING).toString().length() : 0))
                .collect(Collectors.toList());
        list.add(getHeaderPrice().length());
        return list.stream();
    }

    private Stream<Integer> getNameLengths(Check check) {
        var list = check.getCheckItems().stream()
                .map(a -> a.getProduct() != null ? a.getProduct().getName().length() : 0)
                .collect(Collectors.toList());
        list.add(getHeaderName().length());
        return list.stream();
    }

    private Stream<Integer> getAmountLengths(Check check) {
        var list = check.getCheckItems().stream()
                .map(a -> (getCurrency() + a.totalAmount()
                        .setScale(getScale(), RoundingMode.CEILING).toString()).length())
                .collect(Collectors.toList());
        list.add(getHeaderTotal().length());
        return list.stream();
    }
}
