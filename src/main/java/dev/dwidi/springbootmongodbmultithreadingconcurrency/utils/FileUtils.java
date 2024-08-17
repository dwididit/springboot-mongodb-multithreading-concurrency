package dev.dwidi.springbootmongodbmultithreadingconcurrency.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtils {

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    // Utility method to encode a file to a Base64 string
    public static String encodeFileToBase64(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
