package ru.clevertec.checksystem.webuiservlet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashSet;

import static ru.clevertec.checksystem.core.Constants.Format;
import static ru.clevertec.checksystem.webuiservlet.Constants.Sources;

public class Helpers {

    private static final BiMap<String, String> contentTypeFormatBiMap = HashBiMap.create();

    static {
        contentTypeFormatBiMap.put(Format.TEXT, "text/plain");
        contentTypeFormatBiMap.put(Format.HTML, "text/html");
        contentTypeFormatBiMap.put(Format.PDF, "application/pdf");
        contentTypeFormatBiMap.put(Format.XML, "text/xml");
        contentTypeFormatBiMap.put(Format.JSON, "application/json");
    }

    private static final BiMap<String, String> formatExtensionBiMap = HashBiMap.create();

    static {
        formatExtensionBiMap.put(Format.TEXT, "txt");
        formatExtensionBiMap.put(Format.HTML, "html");
        formatExtensionBiMap.put(Format.PDF, "pdf");
        formatExtensionBiMap.put(Format.XML, "xml");
        formatExtensionBiMap.put(Format.JSON, "json");
    }

    private Helpers() {
    }

    public static String contentTypeByFormat(String format) {
        return contentTypeFormatBiMap.get(format);
    }

    public static String formatByContentType(String contentType) {
        return contentTypeFormatBiMap.inverse().get(contentType);
    }

    public static String extensionByFormat(String format) {
        return formatExtensionBiMap.get(format);
    }

    public static String formatByExtension(String extension) {
        return formatExtensionBiMap.inverse().get(extension);
    }

    public static String sourceByParameter(String parameter) {
        var sources = new HashSet<String>();
        sources.add(Sources.DATABASE);
        sources.add(Sources.FILE);
        return sources.contains(parameter) ? parameter : Sources.DATABASE;
    }
}
