package ru.clevertec.checksystem.core.io.printer.strategy;

import org.apache.commons.lang3.ArrayUtils;
import ru.clevertec.checksystem.core.check.Check;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class HtmlCheckPrintStrategy extends CheckPrintStrategy {

    public HtmlCheckPrintStrategy() {
    }

    @Override
    public byte[] getData(Check check) {
        return createCheck(check);
    }

    @Override
    public byte[] getCombinedData(Check[] checks) {
        return Arrays.stream(checks).map(this::createCheck)
                .reduce(ArrayUtils::addAll).orElse(null);
    }

    private byte[] createCheck(Check check) {

        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        var timeFormatter = new SimpleDateFormat("hh:mm");

        String info =
                "<div style='font-family:monospace;font-size:13px;background-color:#f7f7f7;line-height: 1rem;" +
                        "padding:16px;white-space: nowrap;'>" +
                        "<div style='text-align: center;'>" +
                        "<div style='margin-bottom:16px;'>" +
                        "<h2 style='margin:0 0 16px 0;font-size:1rem;font-weight:bold;'>" + check.getName() + "</h2>" +
                        "<div>" + check.getDescription() + "</div>" +
                        "<div>" + check.getAddress() + "</div>" +
                        "<div>" + check.getPhoneNumber() + "</h2>" +
                        "</div>" +
                        "</div>" +
                        "<div style='display:flex;justify-content:space-between'>" +
                        "<div style='margin-right:16px;'>CASHIER: " + check.getCashier() + "</div>" +
                        "<div style='text-align:left'>" +
                        "<div>DATE: " + dateFormatter.format(check.getDate()) + "</div>" +
                        "<div>TIME: " + timeFormatter.format(check.getDate()) + "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "<hr style='margin: 16px 0'/>";

        var tableBuilder = new StringBuilder();

        tableBuilder.append("<table style='width:100%'>");

        tableBuilder.append("<tr>")
                .append("<th style='font-weight:bold;text-align:center;padding:3px;'>")
                .append(getHeaderQuantity()).append("</th>")
                .append("<th style='font-weight:bold;text-align:left;padding:3px;'>")
                .append(getHeaderName()).append("</th>")
                .append("<th style='font-weight:bold;text-align:right;padding:3px;'>")
                .append(getHeaderPrice()).append("</th>")
                .append("<th style='font-weight:bold;text-align:right;padding:3px;'>")
                .append(getHeaderTotal()).append("</th>")
                .append("</tr>");

        for (var item : check.getItems()) {
            tableBuilder.append("<tr>");
            tableBuilder.append("<td style='text-align:center;padding:3px;'>")
                    .append(item.getQuantity()).append("</td>");
            tableBuilder.append("<td style='text-align:left;padding:3px;'>")
                    .append(item.getProduct().getName()).append("</td>");
            tableBuilder.append("<td style='text-align:right;padding:3px;'>")
                    .append(getCurrency()).append(item.getProduct().getPrice()
                    .setScale(getScale(), RoundingMode.CEILING)).append("</td>");
            tableBuilder.append("<td style='text-align:right;padding:3px;");
            if (item.discountsSum().doubleValue() > 0) {
                tableBuilder.append("text-decoration:line-through;");
            }
            tableBuilder.append("'>")
                    .append(getCurrency()).append(item.subTotal()
                    .setScale(getScale(), RoundingMode.CEILING)).append("</td>");

            tableBuilder.append("</tr>");

            if (item.discountsSum().doubleValue() > 0) {
                tableBuilder.append("<tr>");
                tableBuilder.append("<td colspan='4' style='text-align:right;padding:3px;'>")
                        .append("Discount: ").append("-").append(getCurrency())
                        .append(item.discountsSum().setScale(getScale(), RoundingMode.CEILING))
                        .append(" = ").append(getCurrency()).append(item.total()
                        .setScale(getScale(), RoundingMode.CEILING));
                tableBuilder.append("</tr>");
            }
        }

        tableBuilder.append("</table>");

        String results = "<table style='width:100%'>" +
                "<tr>" +
                "<td style='text-align:left;padding:3px;'>" +
                "<strong style='font-weight:bold;'>" + "SUBTOTAL:" + "</strong>" + "</td>" +
                "<td style='text-align:right;padding:3px;'>" +
                getCurrency() + check.subTotal()
                .setScale(getScale(), RoundingMode.CEILING) +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='text-align:left;padding:3px;'>" +
                "<strong style='font-weight:bold;'>" + "DISCOUNTS:" + "</strong>" + "</td>" +
                "<td style='text-align:right;padding:3px;'>" +
                getCurrency() + check.allDiscountsSum()
                .setScale(getScale(), RoundingMode.CEILING) +
                "</td>" +
                "</tr>" +
                "<td style='text-align:left;padding:3px;'>" +
                "<strong style='font-weight:bold;'>" + "TOTAL:" + "</strong>" + "</td>" +
                "<td style='text-align:right;padding:3px;'>" +
                getCurrency() + check.total()
                .setScale(getScale(), RoundingMode.CEILING) +
                "</td>" +
                "</tr>" +
                "</table>";

        return (info + tableBuilder.toString() + "<hr style='margin: 10px 0'/>" + results + "</div>")
                .getBytes(StandardCharsets.UTF_8);
    }
}
