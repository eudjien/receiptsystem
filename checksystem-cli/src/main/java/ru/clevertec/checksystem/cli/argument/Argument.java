package ru.clevertec.checksystem.cli.argument;


import java.util.HashSet;
import java.util.Set;

public class Argument {

    private String key;
    private final Set<String> values = new HashSet<>();

    public Argument(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<String> getValues() {
        return values;
    }

    public String firstValue() {
        return values.iterator().next();
    }

    public void addValue(String value) {
        values.add(value);
    }

    public boolean hasValues() {
        return !values.isEmpty();
    }
}
