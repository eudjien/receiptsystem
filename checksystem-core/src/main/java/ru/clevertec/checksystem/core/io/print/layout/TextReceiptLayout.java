package ru.clevertec.checksystem.core.io.print.layout;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.TA_GridThemes;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.service.common.IReceiptService;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class TextReceiptLayout extends AbstractReceiptLayout {

    private static final String EMPTY_LINE_REPLACEMENT_REGEX = "(?m)^[ \t]*\r?\n";
    private static final int MIN_TABLE_WIDTH = 28;

    private int tableWidth = 40;

    public TextReceiptLayout(IReceiptService receiptService) {
        super(receiptService);
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

    private byte[] createReceiptText(Receipt receipt) {

        var table = createSellerTable(receipt) + '\n' + '\n' +
                createPurchaseTable(receipt) + '\n' + ' ' + "-".repeat(getTableWidth() - 2) + ' ' + '\n' +
                createReceiptItemsTable(receipt) + '\n' + ' ' + "-".repeat(getTableWidth() - 2) + ' ' + '\n' +
                createSummaryTable(receipt);

        return table.getBytes(StandardCharsets.UTF_8);
    }

    private String createSellerTable(Receipt receipt) {

        var at = new AsciiTable();
        at.getContext().setGridTheme(TA_GridThemes.NONE);

        at.addRule();
        at.addRow(receipt.getName()).setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        at.addRow(receipt.getDescription()).setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        at.addRow(receipt.getAddress()).setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        at.addRow(receipt.getPhoneNumber()).setTextAlignment(TextAlignment.CENTER);

        return at.render(getTableWidth()).replaceAll(EMPTY_LINE_REPLACEMENT_REGEX, "");
    }

    private String createPurchaseTable(Receipt receipt) {

        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        var timeFormatter = new SimpleDateFormat("hh:mm");

        var at = new AsciiTable();
        at.getContext().setGridTheme(TA_GridThemes.NONE);

        var date = dateFormatter.format(receipt.getDate());
        var time = timeFormatter.format(receipt.getDate());

        if (date.length() > time.length())
            time = time + "#".repeat(date.length() - time.length());
        else if (date.length() < time.length())
            date = date + "#".repeat(time.length() - date.length());

        at.addRule();
        var row1 = at.addRow("CASHIER: " + receipt.getCashier(), "DATE: " + date + "<br/>TIME: " + time);
        row1.getCells().get(0).getContext().setTextAlignment(TextAlignment.LEFT);
        row1.getCells().get(1).getContext().setTextAlignment(TextAlignment.RIGHT);

        return at.render(getTableWidth()).replaceAll(EMPTY_LINE_REPLACEMENT_REGEX, "").replace('#', ' ');
    }

    private String createReceiptItemsTable(Receipt receipt) {

        var at = new AsciiTable();
        at.getContext().setGridTheme(TA_GridThemes.NONE);

        at.addRule();
        var headerRow = at.addRow(getHeaderQuantity(), getHeaderName(), getHeaderPrice(), getHeaderTotal());
        headerRow.getCells().get(0).getContext().setTextAlignment(TextAlignment.LEFT);
        headerRow.getCells().get(1).getContext().setTextAlignment(TextAlignment.LEFT);
        headerRow.getCells().get(2).getContext().setTextAlignment(TextAlignment.RIGHT);
        headerRow.getCells().get(3).getContext().setTextAlignment(TextAlignment.RIGHT);

        for (var receiptItem : receipt.getReceiptItems()) {

            var summary = getReceiptService().getReceiptItemSummary(receiptItem);

            at.addRule();
            var row = at.addRow(
                    receiptItem.getQuantity(),
                    receiptItem.getProduct().getName(),
                    getCurrency() + receiptItem.getProduct().getPrice(),
                    getCurrency() + summary.getSubTotalAmount());
            row.getCells().get(0).getContext().setTextAlignment(TextAlignment.LEFT);
            row.getCells().get(1).getContext().setTextAlignment(TextAlignment.LEFT);
            row.getCells().get(2).getContext().setTextAlignment(TextAlignment.RIGHT);
            row.getCells().get(3).getContext().setTextAlignment(TextAlignment.RIGHT);

            if (summary.getDiscountAmount().doubleValue() > 0) {
                at.addRule();
                at.addRow(null, null, null, "Discount: -" + getCurrency() + summary.getDiscountAmount().setScale(getScale(), RoundingMode.CEILING)
                        + " = " + getCurrency() + summary.getTotalAmount().setScale(getScale(), RoundingMode.CEILING))
                        .setTextAlignment(TextAlignment.RIGHT);

            }
        }

        return at.render(getTableWidth()).replaceAll(EMPTY_LINE_REPLACEMENT_REGEX, "");
    }

    private String createSummaryTable(Receipt receipt) {

        var at = new AsciiTable();
        at.getContext().setGridTheme(TA_GridThemes.NONE);

        var summary = getReceiptService().getReceiptSummary(receipt);

        at.addRule();
        var subTotalRow = at.addRow("SUBTOTAL", getCurrency() + summary.getSubTotalAmount().setScale(getScale(), RoundingMode.CEILING));
        subTotalRow.getCells().get(0).getContext().setTextAlignment(TextAlignment.LEFT);
        subTotalRow.getCells().get(1).getContext().setTextAlignment(TextAlignment.RIGHT);

        at.addRule();
        var discountsRow = at.addRow("DISCOUNTS", getCurrency() + summary.getDiscountAmount().setScale(getScale(), RoundingMode.CEILING));
        discountsRow.getCells().get(0).getContext().setTextAlignment(TextAlignment.LEFT);
        discountsRow.getCells().get(1).getContext().setTextAlignment(TextAlignment.RIGHT);

        at.addRule();
        var totalRow = at.addRow("TOTAL", getCurrency() + summary.getTotalAmount().setScale(getScale(), RoundingMode.CEILING));
        totalRow.getCells().get(0).getContext().setTextAlignment(TextAlignment.LEFT);
        totalRow.getCells().get(1).getContext().setTextAlignment(TextAlignment.RIGHT);

        at.addRule();

        return at.render(getTableWidth()).replaceAll(EMPTY_LINE_REPLACEMENT_REGEX, "");
    }

    public int getTableWidth() {
        return tableWidth;
    }

    public void setTableWidth(int tableWidth) {
        ThrowUtils.Argument.lessThan("tableWidth", tableWidth, MIN_TABLE_WIDTH);
        this.tableWidth = tableWidth;
    }
}
