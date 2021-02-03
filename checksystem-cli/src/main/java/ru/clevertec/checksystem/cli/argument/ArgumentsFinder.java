package ru.clevertec.checksystem.cli.argument;

import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ArgumentsFinder {

    private static final Pattern pattern = Pattern.compile("^-(?<key>.+)=(?<value>.+)$");
    private final HashMap<String, Argument> arguments = new HashMap<>();

    public ArgumentsFinder(String[] arguments) {
        addArguments(arguments);
    }

    public Set<Argument> getArguments() {
        return new HashSet<>(arguments.values());
    }

    public void addArguments(String[] arguments) {

        ThrowUtils.Argument.nullValue("arguments", arguments);

        for (var arg : arguments) {
            var matcher = pattern.matcher(arg);
            if (matcher.find()) {
                var key = matcher.group("key");
                var value = matcher.group("value").trim();
                addArgument(key, value);
            }
        }
    }

    public Argument argumentByKey(String key) {
        return arguments.get(key);
    }

    private void addArgument(String key, String value) {
        if (this.arguments.containsKey(key)) {
            this.arguments.get(key).addValue(value);
        } else {
            var argument = new Argument(key);
            argument.addValue(value);
            this.arguments.put(key, argument);
        }
    }

    public void clearArguments() {
        arguments.clear();
    }

    public static Pattern getPattern() {
        return pattern;
    }

    public String findFirstStringOrDefault(String key) {
        return findFirstStringOrDefault(key, null);
    }

    public String findFirstStringOrDefault(String key, String defaultValue) {
        if (arguments.containsKey(key)) {
            return arguments.get(key).firstValue();
        }
        return defaultValue;
    }

    public String findFirstStringOrThrow(String key) {
        var value = findFirstStringOrDefault(key);
        if (value == null) {
            throw new IllegalArgumentException("Parameter '" + key + "' is not defined");
        }
        return value;
    }

    public int findFirstIntOrDefault(String key) {
        return findFirstIntOrDefault(key, 0);
    }

    public int findFirstIntOrDefault(String key, int defaultValue) {
        var value = findFirstStringOrDefault(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public boolean findFirstBoolOrDefault(String key) {
        return findFirstBoolOrDefault(key, false);
    }

    public boolean findFirstBoolOrDefault(String key, boolean defaultValue) {
        var value = findFirstStringOrDefault(key);
        if (value == null) {
            return defaultValue;
        }
        if (value.trim().equals("1")) {
            return true;
        }
        return Boolean.parseBoolean(value);
    }
}
