package ru.clevertec.checksystem.core.helper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import static ru.clevertec.checksystem.core.Constants.Format;

public class FormatHelpers {

    private static final BiMap<String, String> formatContentTypeBiMap = HashBiMap.create();

    static {
        formatContentTypeBiMap.put(Format.TEXT, "text/plain");
        formatContentTypeBiMap.put(Format.HTML, "text/html");
        formatContentTypeBiMap.put(Format.PDF, "application/pdf");
        formatContentTypeBiMap.put(Format.XML, "text/xml");
        formatContentTypeBiMap.put(Format.JSON, "application/json");
    }

    private static final BiMap<String, String> formatExtensionBiMap = HashBiMap.create();

    static {
        formatExtensionBiMap.put(Format.TEXT, "txt");
        formatExtensionBiMap.put(Format.HTML, "html");
        formatExtensionBiMap.put(Format.PDF, "pdf");
        formatExtensionBiMap.put(Format.XML, "xml");
        formatExtensionBiMap.put(Format.JSON, "json");
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
