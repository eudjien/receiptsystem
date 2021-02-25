package ru.clevertec.checksystem.core.io.print.layout;

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
import ru.clevertec.checksystem.core.common.ITemplatable;
import ru.clevertec.checksystem.core.common.template.IPdfTemplate;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Objects;

public class PdfCheckLayout extends AbstractCheckLayout implements ITemplatable<IPdfTemplate> {

    public static final String FONT_PATH = "font/JetBrainsMono-VariableFont_wght.ttf";

    private static final String DATE_TEMPLATE = "dd.MM.yyyy";
    private static final String TIME_TEMPLATE = "hh:mm";

    private static final float FONT_SIZE_BASE = 16f;
    private static final float LINE_HEIGHT_BASE = 1f;
    private static final float LINE_HEIGHT_SM = LINE_HEIGHT_BASE - 0.25f;
    private static final float LINE_HEIGHT_MD = LINE_HEIGHT_BASE + .25f;
    private static final float DEFAULT_SPACE_SIZE = 16f;

    private static final Color SEPARATOR_COLOR = new DeviceRgb(48, 48, 48);

    private IPdfTemplate template;

    public PdfCheckLayout() {
    }

    public PdfCheckLayout(IPdfTemplate pdfTemplate) {
        setTemplate(pdfTemplate);
    }

    @Override
    public boolean hasTemplate() {
        return template != null;
    }

    @Override
    public void setTemplate(IPdfTemplate template) {
        this.template = template;
    }

    @Override
    public IPdfTemplate getTemplate() {
        return template;
    }

    @Override
    public byte[] getLayoutData(Check check) throws IOException {
        ThrowUtils.Argument.nullValue("check", check);
        return creteCheckPdfBytes(check);
    }

    @Override
    public byte[] getAllLayoutData(Collection<Check> checks) throws IOException {

        ThrowUtils.Argument.nullOrEmpty("checks", checks);

        var os = new ByteArrayOutputStream();

        var containerPdf = new PdfDocument(new PdfWriter(os));
        var merger = new PdfMerger(containerPdf);

        for (var check : checks) {
            var checkPdf = new PdfDocument(new PdfReader(new ByteArrayInputStream(creteCheckPdfBytes(check))));
            merger.merge(checkPdf, 1, checkPdf.getNumberOfPages());
            checkPdf.close();
        }

        containerPdf.close();

        return os.toByteArray();
    }

    private byte[] creteCheckPdfBytes(Check check) throws IOException {

        var os = new ByteArrayOutputStream();
        var writer = new PdfWriter(os);

        var pdf = hasTemplate()
                ? new PdfDocument(new PdfReader(template.getInputStream()), writer)
                : new PdfDocument(writer);

        var document = new Document(pdf).setFontSize(18).setFont(createPdfFont());
        addCheckToDocument(document, check);

        document.close();
        return os.toByteArray();
    }

    private void addCheckToDocument(Document document, Check check) {
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

        for (var checkItem : check.getCheckItems()) {

            table.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(checkItem.getQuantity() + "")
                            .setMultipliedLeading(LINE_HEIGHT_MD)
                            .setTextAlignment(TextAlignment.LEFT)));

            table.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(checkItem.getProduct().getName())
                            .setMultipliedLeading(LINE_HEIGHT_MD)
                            .setTextAlignment(TextAlignment.LEFT)));

            table.addCell(new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(getCurrency()
                            + checkItem.getProduct().getPrice().setScale(getScale(), RoundingMode.CEILING))
                            .setMultipliedLeading(LINE_HEIGHT_MD)
                            .setTextAlignment(TextAlignment.RIGHT)));

            var totalParagraph = new Paragraph(getCurrency() + checkItem.subTotalAmount()
                    .setScale(getScale(), RoundingMode.CEILING))
                    .setMultipliedLeading(LINE_HEIGHT_MD)
                    .setTextAlignment(TextAlignment.RIGHT);
            table.addCell(new Cell().setBorder(Border.NO_BORDER).add(totalParagraph));

            if (checkItem.discountsAmount().doubleValue() > 0) {
                totalParagraph.setLineThrough();
                table.addCell(new Cell(1, 4)
                        .setBorder(Border.NO_BORDER)
                        .add(new Paragraph("Discount: " + getCurrency() + checkItem.discountsAmount()
                                .setScale(getScale(), RoundingMode.CEILING) + " = "
                                + getCurrency() + checkItem.totalAmount().setScale(getScale(), RoundingMode.CEILING))
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

        addResultTableRow(table, "SUBTOTAL", getCurrency() + check.subTotalAmount()
                .setScale(getScale(), RoundingMode.CEILING));
        addResultTableRow(table, "DISCOUNTS", getCurrency() + check.discountsAmount()
                .setScale(getScale(), RoundingMode.CEILING));
        addResultTableRow(table, "TOTAL", getCurrency() + check.totalAmount()
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

    private String getResourcePath() {
        try {
            return Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(PdfCheckLayout.FONT_PATH)).toURI())
                    .toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PdfFont createPdfFont() throws IOException {
        var font = PdfFontFactory.createFont(
                getResourcePath(), PdfEncodings.IDENTITY_H, true);
        font.setSubset(false);
        return font;
    }
}
