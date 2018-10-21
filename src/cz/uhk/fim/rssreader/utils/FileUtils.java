package cz.uhk.fim.rssreader.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String loadStringFromFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

    public static void saveStringToFile(String filePath, byte[] data) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path,data);
        ;

    }
}
