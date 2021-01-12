package ru.clevertec.checksystem.cli;

import java.util.regex.Pattern;

public class ArgumentsFinder {

    private static final Pattern pattern = Pattern.compile("^-(?<key>.+)=(?<value>.+)$");
    private String[] arguments;

    public ArgumentsFinder(String[] arguments) {
        setArguments(arguments);
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        if (arguments == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        this.arguments = arguments;
    }

    public static Pattern getPattern() {
        return pattern;
    }

    public String findStringOrNull(String key) {
        for (var arg : arguments) {
            var matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                var argKey = matcher.group("key");
                if (argKey.equals(key)) {
                    return matcher.group("value").trim();
                }
            }
        }
        return null;
    }

    public String findStringOrThrow(String key) throws IllegalArgumentException {
        var value = findStringOrNull(key);
        if (value == null) {
            throw new IllegalArgumentException("Parameter '" + key + "' is not defined");
        }
        return value;
    }

    public int findIntOrDefault(String key) {
        return findIntOrDefault(key, 0);
    }

    public int findIntOrDefault(String key, int defaultValue) {
        var value = findStringOrNull(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public boolean findBoolOrDefault(String key) {
        return findBoolOrDefault(key, false);
    }

    public boolean findBoolOrDefault(String key, boolean defaultValue) {
        var value = findStringOrNull(key);
        if (value == null) {
            return defaultValue;
        }
        if (value.trim().equals("1")) {
            return true;
        }
        return Boolean.parseBoolean(value);
    }
}
