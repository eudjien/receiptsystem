package ru.clevertec.checksystem.core.io.printer.strategy;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import ru.clevertec.checksystem.core.check.Check;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class PdfCheckPrintStrategy extends CheckPrintStrategy {

    public static final String FONT = "font/JetBrainsMono-VariableFont_wght.ttf";

    @Override
    public byte[] getData(Check check) throws IOException, URISyntaxException {

        var os = new ByteArrayOutputStream();
        var document = createDocument(os);

        addCheckToDocument(document, check);

        document.close();
        return os.toByteArray();
    }

    @Override
    public byte[] getCombinedData(Check[] checks) throws IOException, URISyntaxException {
        var os = new ByteArrayOutputStream();
        var document = createDocument(os);

        for (int i = 0; i < checks.length; i++) {
            addCheckToDocument(document, checks[i]);
            if (i < checks.length - 1) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }
        }

        document.close();
        return os.toByteArray();
    }

    public Document createDocument(ByteArrayOutputStream os) throws URISyntaxException, IOException {
        var fontPath = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(FONT)).toURI())
                .toAbsolutePath().toString();

        var font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);
        font.setSubset(false);

        var writer = new PdfWriter(os);
        var pdf = new PdfDocument(writer);
        return new Document(pdf).setFontSize(16).setFont(font);
    }

    public void addCheckToDocument(Document document, Check check) {

        var lineSep = new LineSeparator(new DashedLine(1))
                .setMargins(15, 0, 15, 0)
                .setStrokeColor(Color.convertRgbToCmyk(new DeviceRgb(48, 48, 48)));

        document.add(createShopInfoDiv(check).setFontSize(18).setMarginBottom(15));
        document.add(createHeaderTable(check));
        document.add(lineSep);
        document.add(createItemsTable(check));
        document.add(lineSep);
        document.add(createResultTable(check));
    }

    private Div createShopInfoDiv(Check check) {
        return new Div()
                .add(new Paragraph(check.getName())
                        .setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(0.7f)
                        .setMarginBottom(15).setBold())
                .add(new Paragraph(check.getDescription())
                        .setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(0.7f))
                .add(new Paragraph(check.getAddress()).setTextAlignment(TextAlignment.CENTER)
                        .setMultipliedLeading(0.7f))
                .add(new Paragraph(check.getPhoneNumber())
                        .setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(0.7f));
    }

    private Table createItemsTable(Check check) {

        var table = new Table(4);
        table.useAllAvailableWidth();
        table.setBorder(Border.NO_BORDER);

        table.addHeaderCell(new Cell().add(new Paragraph(getHeaderQuantity())
                .setTextAlignment(TextAlignment.LEFT).setBold()).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph(getHeaderName())
                .setTextAlignment(TextAlignment.LEFT).setBold()).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph(getHeaderPrice())
                .setTextAlignment(TextAlignment.RIGHT).setBold()).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph(getHeaderTotal())
                .setTextAlignment(TextAlignment.RIGHT).setBold()).setBorder(Border.NO_BORDER));

        for (var item : check.getItems()) {

            table.addCell(new Cell().add(new Paragraph(item.getQuantity() + "")
                    .setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph(item.getProduct().getName())
                    .setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph(getCurrency() + item.getProduct().getPrice()
                    .setScale(getScale(), RoundingMode.CEILING))
                    .setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

            var totalParagraph = new Paragraph(getCurrency() + item.subTotal()
                    .setScale(getScale(), RoundingMode.CEILING));
            table.addCell(new Cell().add(totalParagraph
                    .setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

            if (item.discountsSum().doubleValue() > 0) {
                totalParagraph.setLineThrough();
                table.addCell(new Cell(1, 4).add(
                        new Paragraph("Discount: " + getCurrency() + item.discountsSum()
                                .setScale(getScale(), RoundingMode.CEILING) + " = "
                                + getCurrency() + item.total().setScale(getScale(), RoundingMode.CEILING)))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER));
            }
        }

        return table;
    }

    private Table createResultTable(Check check) {
        var table = new Table(2);
        table.useAllAvailableWidth();
        table.setBorder(Border.NO_BORDER);

        addResultTableRow(table, "SUBTOTAL", getCurrency() + check.subTotal()
                .setScale(getScale(), RoundingMode.CEILING));
        addResultTableRow(table, "DISCOUNTS", getCurrency() + check.allDiscountsSum()
                .setScale(getScale(), RoundingMode.CEILING));
        addResultTableRow(table, "TOTAL", getCurrency() + check.total()
                .setScale(getScale(), RoundingMode.CEILING));

        return table;
    }

    private void addResultTableRow(Table table, String leftText, String rightText) {

        var leftCell = new Cell();
        leftCell.setBorder(Border.NO_BORDER);
        var leftCellParagraph = new Paragraph(leftText)
                .setTextAlignment(TextAlignment.LEFT)
                .setBold()
                .setMultipliedLeading(1);
        leftCell.add(leftCellParagraph);

        var rightCell = new Cell();
        rightCell.setBorder(Border.NO_BORDER);
        var rightCellParagraph = new Paragraph(rightText)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMultipliedLeading(1);
        rightCell.add(rightCellParagraph);

        table.addCell(new Cell().add(new Paragraph(leftText)
                .setMultipliedLeading(1).setTextAlignment(TextAlignment.LEFT).setBold())
                .setBorder(Border.NO_BORDER));

        table.addCell(rightCell);
    }

    private Table createHeaderTable(Check check) {

        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        var timeFormatter = new SimpleDateFormat("hh:mm");

        var table = new Table(2).useAllAvailableWidth();

        table.addCell(new Cell().add(new Paragraph("CASHIER: " + check.getCashier())
                .setMultipliedLeading(0.95f))
                .setBorder(Border.NO_BORDER));

        var date = "DATE: " + dateFormatter.format(check.getDate());
        var time = "TIME: " + timeFormatter.format(check.getDate());

        table.addCell(new Cell(2, 1)
                .add(new Table(1)
                        .addCell(new Cell(2, 1)
                                .add(new Paragraph(date + "\n" + time)
                                        .setMultipliedLeading(0.95f)).setBorder(Border.NO_BORDER))
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER))
                .setBorder(Border.NO_BORDER));

        return table;
    }
}
