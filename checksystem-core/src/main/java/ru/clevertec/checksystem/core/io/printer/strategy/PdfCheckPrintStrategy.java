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

    private static final String DATE_TEMPLATE = "dd.MM.yyyy";
    private static final String TIME_TEMPLATE = "hh:mm";

    private static final float FONT_SIZE_BASE = 16f;
    private static final float LINE_HEIGHT_BASE = 1f;
    private static final float LINE_HEIGHT_SM = LINE_HEIGHT_BASE - 0.25f;
    private static final float LINE_HEIGHT_MD = LINE_HEIGHT_BASE + .25f;
    private static final float DEFAULT_SPACE_SIZE = 16f;

    private static final Color SEPARATOR_COLOR = new DeviceRgb(48, 48, 48);

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
        return new Document(pdf).setFontSize(FONT_SIZE_BASE).setFont(font);
    }

    public void addCheckToDocument(Document document, Check check) {
        var lineSep = createSeparator();
        document.add(createShopInfoDiv(check).setFontSize(FONT_SIZE_BASE * 1.125f).setMarginBottom(DEFAULT_SPACE_SIZE));
        document.add(createHeaderTable(check));
        document.add(lineSep);
        document.add(createItemsTable(check));
        document.add(lineSep);
        document.add(createResultTable(check));
    }

    private Div createShopInfoDiv(Check check) {
        return new Div()
                .add(new Paragraph(check.getName())
                        .setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(LINE_HEIGHT_SM)
                        .setMarginBottom(DEFAULT_SPACE_SIZE)
                        .setBold())
                .add(new Paragraph(check.getDescription())
                        .setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(LINE_HEIGHT_SM))
                .add(new Paragraph(check.getAddress())
                        .setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(LINE_HEIGHT_SM))
                .add(new Paragraph(check.getPhoneNumber())
                        .setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(LINE_HEIGHT_SM));
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

            table.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(item.getQuantity() + "")
                            .setMultipliedLeading(LINE_HEIGHT_MD)
                            .setTextAlignment(TextAlignment.LEFT)));

            table.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(item.getProduct().getName())
                            .setMultipliedLeading(LINE_HEIGHT_MD)
                            .setTextAlignment(TextAlignment.LEFT)));

            table.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(getCurrency()
                            + item.getProduct().getPrice().setScale(getScale(), RoundingMode.CEILING))
                            .setMultipliedLeading(LINE_HEIGHT_MD)
                            .setTextAlignment(TextAlignment.RIGHT)));

            var totalParagraph = new Paragraph(getCurrency() + item.subTotal()
                    .setScale(getScale(), RoundingMode.CEILING))
                    .setMultipliedLeading(LINE_HEIGHT_MD)
                    .setTextAlignment(TextAlignment.RIGHT);
            table.addCell(new Cell().setBorder(Border.NO_BORDER).add(totalParagraph));

            if (item.discountsSum().doubleValue() > 0) {
                totalParagraph.setLineThrough();
                table.addCell(new Cell(1, 4)
                        .setBorder(Border.NO_BORDER)
                        .add(new Paragraph("Discount: " + getCurrency() + item.discountsSum()
                                .setScale(getScale(), RoundingMode.CEILING) + " = "
                                + getCurrency() + item.total().setScale(getScale(), RoundingMode.CEILING))
                                .setMultipliedLeading(LINE_HEIGHT_MD)
                                .setTextAlignment(TextAlignment.RIGHT)));
            }
        }

        return table;
    }

    private Table createResultTable(Check check) {
        var table = new Table(2)
                .useAllAvailableWidth()
                .setBorder(Border.NO_BORDER);

        addResultTableRow(table, "SUBTOTAL", getCurrency() + check.subTotal()
                .setScale(getScale(), RoundingMode.CEILING));
        addResultTableRow(table, "DISCOUNTS", getCurrency() + check.allDiscountsSum()
                .setScale(getScale(), RoundingMode.CEILING));
        addResultTableRow(table, "TOTAL", getCurrency() + check.total()
                .setScale(getScale(), RoundingMode.CEILING));

        return table;
    }

    private void addResultTableRow(Table table, String leftText, String rightText) {

        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(leftText)
                        .setMultipliedLeading(LINE_HEIGHT_BASE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setBold()));

        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(rightText)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setMultipliedLeading(LINE_HEIGHT_BASE)));
    }

    private Table createHeaderTable(Check check) {

        var dateFormatter = new SimpleDateFormat(DATE_TEMPLATE);
        var timeFormatter = new SimpleDateFormat(TIME_TEMPLATE);

        var table = new Table(2).useAllAvailableWidth();

        table.addCell(new Cell().add(new Paragraph("CASHIER: " + check.getCashier())
                .setMultipliedLeading(LINE_HEIGHT_MD))
                .setBorder(Border.NO_BORDER));

        var date = "DATE: " + dateFormatter.format(check.getDate());
        var time = "TIME: " + timeFormatter.format(check.getDate());

        table.addCell(new Cell(2, 1)
                .add(new Table(1)
                        .addCell(new Cell(2, 1)
                                .add(new Paragraph(date + "\n" + time)
                                        .setMultipliedLeading(LINE_HEIGHT_MD)).setBorder(Border.NO_BORDER))
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER))
                .setBorder(Border.NO_BORDER));

        return table;
    }

    private LineSeparator createSeparator() {
        return new LineSeparator(new DashedLine(1))
                .setMargins(DEFAULT_SPACE_SIZE, 0, DEFAULT_SPACE_SIZE, 0)
                .setStrokeColor(SEPARATOR_COLOR);
    }
}
