package ru.clevertec.checksystem.core.helper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.http.MediaType;
import ru.clevertec.checksystem.core.constant.Extensions;
import ru.clevertec.checksystem.core.constant.Formats;

public class FormatHelpers {

    private static final BiMap<String, String> formatContentTypeBiMap = HashBiMap.create();

    static {
        formatContentTypeBiMap.put(Formats.TEXT, MediaType.TEXT_PLAIN_VALUE);
        formatContentTypeBiMap.put(Formats.HTML, MediaType.TEXT_HTML_VALUE);
        formatContentTypeBiMap.put(Formats.PDF, MediaType.APPLICATION_PDF_VALUE);
        formatContentTypeBiMap.put(Formats.XML, MediaType.TEXT_XML_VALUE);
        formatContentTypeBiMap.put(Formats.JSON, MediaType.APPLICATION_JSON_VALUE);
    }

    private static final BiMap<String, String> formatExtensionBiMap = HashBiMap.create();

    static {
        formatExtensionBiMap.put(Formats.TEXT, Extensions.TXT);
        formatExtensionBiMap.put(Formats.HTML, Extensions.HTML);
        formatExtensionBiMap.put(Formats.PDF, Extensions.PDF);
        formatExtensionBiMap.put(Formats.XML, Extensions.XML);
        formatExtensionBiMap.put(Formats.JSON, Extensions.JSON);
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
