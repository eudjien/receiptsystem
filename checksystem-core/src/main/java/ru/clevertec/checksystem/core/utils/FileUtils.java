package ru.clevertec.checksystem.core.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void writeBytesToFile(byte[] bytes, String destPath) throws IOException {
        writeBytesToFile(bytes, Path.of(destPath));
    }

    public static void writeBytesToFile(byte[] bytes, Path destPath) throws IOException {
        Files.createDirectories(destPath.getParent());
        Files.write(destPath, bytes);
    }

    public static void writeBytesToFile(byte[] bytes, File file) throws IOException {
        Files.createDirectories(file.toPath().getParent());
        Files.write(file.toPath(), bytes);
    }

    public static void readBytesFromFile(byte[] bytes, Path destPath) throws IOException {
        Files.createDirectories(destPath.getParent());
        Files.write(destPath, bytes);
    }
}
