package ru.clevertec.checksystem.core.helper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.checksystem.core.Constants.Formats;

public class FormatHelpers {

    private static final BiMap<String, String> formatContentTypeBiMap = HashBiMap.create();

    static {
        formatContentTypeBiMap.put(Formats.TEXT, "text/plain");
        formatContentTypeBiMap.put(Formats.HTML, "text/html");
        formatContentTypeBiMap.put(Formats.PDF, "application/pdf");
        formatContentTypeBiMap.put(Formats.XML, "text/xml");
        formatContentTypeBiMap.put(Formats.JSON, "application/json");
    }

    private static final BiMap<String, String> formatExtensionBiMap = HashBiMap.create();

    static {
        formatExtensionBiMap.put(Formats.TEXT, "txt");
        formatExtensionBiMap.put(Formats.HTML, "html");
        formatExtensionBiMap.put(Formats.PDF, "pdf");
        formatExtensionBiMap.put(Formats.XML, "xml");
        formatExtensionBiMap.put(Formats.JSON, "json");
    }

    private static final Map<String, String> testMap = new HashMap<>();

    static {
        testMap.put(Formats.TEXT, "txt");
        testMap.put(Formats.HTML, "html");
        testMap.put(Formats.PDF, "pdf");
        testMap.put(Formats.XML, "xml");
        testMap.put(Formats.JSON, "json");

        var extension = testMap.get(Formats.PDF);

        var format = testMap.entrySet().stream()
                .filter(e -> e.getValue().equals("pdf"))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    private FormatHelpers() {
    }

    public static String contentTypeByFormat(String format) {
        return formatContentTypeBiMap.get(format);
    }

    public static String formatByContentType(String contentType) {
        return formatContentTypeBiMap.inverse().get(contentType);
    }

    public static String extensionByFormat(String format) {
        return extensionByFormat(format, false);
    }

    public static String extensionByFormat(String format, boolean dotPrefix) {
        var extension = formatExtensionBiMap.get(format);
        return extension != null ? (dotPrefix ? "." + extension : extension) : null;
    }

    public static String formatByExtension(String extension) {
        return formatExtensionBiMap.inverse().get(extension);
    }
}
