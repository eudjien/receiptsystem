package ru.clevertec.checksystem.webmvcjdbc.model;

import ru.clevertec.checksystem.webmvcjdbc.constant.Sources;
import ru.clevertec.checksystem.webmvcjdbc.validation.annotation.SourceConstraint;

public class HomeModel {

    @SourceConstraint
    private String source = Sources.DATABASE;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
