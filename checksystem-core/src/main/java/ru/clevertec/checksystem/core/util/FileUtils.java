package ru.clevertec.checksystem.core.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void writeBytesToFile(byte[] bytes, String destinationPath) throws IOException {
        writeBytesToFile(bytes, Path.of(destinationPath));
    }

    public static void writeBytesToFile(byte[] bytes, Path destinationPath) throws IOException {
        Files.createDirectories(destinationPath.getParent());
        Files.write(destinationPath, bytes);
    }

    public static void writeBytesToFile(byte[] bytes, File destinationFile) throws IOException {
        Files.createDirectories(destinationFile.toPath().getParent());
        Files.write(destinationFile.toPath(), bytes);
    }

    public static void readBytesFromFile(byte[] bytes, Path destinationPath) throws IOException {
        Files.createDirectories(destinationPath.getParent());
        Files.write(destinationPath, bytes);
    }
}
