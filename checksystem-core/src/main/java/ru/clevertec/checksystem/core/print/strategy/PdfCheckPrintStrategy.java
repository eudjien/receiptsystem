package ru.clevertec.checksystem.core.print.strategy;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.print.template.pdf.PrintPdfTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class PdfCheckPrintStrategy extends CheckPrintStrategy {

    public static final String FONT_PATH = "font/JetBrainsMono-VariableFont_wght.ttf";
    public static final String DEFAULT_TEMPLATE_PATH = "template/Clevertec_Template.pdf";

    private static final String DATE_TEMPLATE = "dd.MM.yyyy";
    private static final String TIME_TEMPLATE = "hh:mm";

    private static final float FONT_SIZE_BASE = 16f;
    private static final float LINE_HEIGHT_BASE = 1f;
    private static final float LINE_HEIGHT_SM = LINE_HEIGHT_BASE - 0.25f;
    private static final float LINE_HEIGHT_MD = LINE_HEIGHT_BASE + .25f;
    private static final float DEFAULT_SPACE_SIZE = 16f;

    private static final Color SEPARATOR_COLOR = new DeviceRgb(48, 48, 48);

    private PrintPdfTemplate template;

    public PdfCheckPrintStrategy() {
    }

    public PdfCheckPrintStrategy(PrintPdfTemplate template) {
        setTemplate(template);
    }

    public boolean hasTemplate() {
        return template != null;
    }

    public void setTemplate(PrintPdfTemplate template) {
        this.template = template;
    }

    @Override
    public byte[] getData(Check check) throws IOException, URISyntaxException {

        if (check == null) {
            throw new IllegalArgumentException("Check cannot be null.");
        }

        return creteCheckPdfBytes(check);
    }

    @Override
    public byte[] getCombinedData(Check[] checks) throws IOException, URISyntaxException {

        var os = new ByteArrayOutputStream();

        if (checks == null || checks.length == 0) {
            throw new IllegalArgumentException("Checks cannot be null or empty");
        }

        var containerPdf = new PdfDocument(new PdfWriter(os));
        var merger = new PdfMerger(containerPdf);

        for (Check check : checks) {
            var checkPdf = new PdfDocument(
                    new PdfReader(new ByteArrayInputStream(creteCheckPdfBytes(check))));

            merger.merge(checkPdf, 1, checkPdf.getNumberOfPages());

            checkPdf.close();
        }

        containerPdf.close();

        return os.toByteArray();
    }

    private byte[] creteCheckPdfBytes(Check check)
            throws IOException, URISyntaxException {

        var os = new ByteArrayOutputStream();
        var writer = new PdfWriter(os);

        var pdf = hasTemplate()
                ? new PdfDocument(new PdfReader(new ByteArrayInputStream(template.getBytes())), writer)
                : new PdfDocument(writer);

        var document = new Document(pdf).setFontSize(18).setFont(createPdfFont());
        addCheckToDocument(document, check);

        document.close();
        return os.toByteArray();
    }

    public void addCheckToDocument(Document document, Check check) throws IOException {
        var lineSep = createSeparator();

        var checkDiv = new Div();

        if (hasTemplate()) {
            checkDiv.add(new Div().setHeight(template.getTopOffset()));
        }

        checkDiv.add(createShopInfoDiv(check));
        checkDiv.add(createServiceInfoTable(check));
        checkDiv.add(lineSep);
        checkDiv.add(createItemsTable(check));
        checkDiv.add(lineSep);
        checkDiv.add(createResultTable(check));

        document.add(checkDiv);
    }

    private Div createShopInfoDiv(Check check) {
        return new Div()
                .setFontSize(FONT_SIZE_BASE * 1.125f)
                .setMarginBottom(DEFAULT_SPACE_SIZE)
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

    private Table createServiceInfoTable(Check check) {

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

    private String getResourcePath(String uri) throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(uri)).toURI())
                .toAbsolutePath().toString();
    }

    private PdfFont createPdfFont() throws URISyntaxException, IOException {
        var font = PdfFontFactory.createFont(
                getResourcePath(FONT_PATH), PdfEncodings.IDENTITY_H, true);
        font.setSubset(false);
        return font;
    }
}
