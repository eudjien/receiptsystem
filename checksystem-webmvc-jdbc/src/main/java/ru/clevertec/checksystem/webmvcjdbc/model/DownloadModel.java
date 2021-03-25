package ru.clevertec.checksystem.webmvcjdbc.model;

import ru.clevertec.checksystem.webmvcjdbc.constant.Sources;
import ru.clevertec.checksystem.webmvcjdbc.validation.annotation.FormatTypeConstraint;
import ru.clevertec.checksystem.webmvcjdbc.validation.annotation.SourceConstraint;

public class DownloadModel {

    @SourceConstraint
    private String source = Sources.DATABASE;

    @FormatTypeConstraint
    private String formatType;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }
}
