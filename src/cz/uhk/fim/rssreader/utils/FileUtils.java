package cz.uhk.fim.rssreader.utils;

import cz.uhk.fim.rssreader.model.RSSSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String CONFIG_FILE = "config.cfg";

    public static String loadStringFromFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

    public static void saveStringToFile(String filePath, byte[] data) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, data);
    }

    public static List<RSSSource> loadSource() {
        List<RSSSource> sources = new ArrayList<>();

        try {
            new BufferedReader(new StringReader(loadStringFromFile(CONFIG_FILE)))
                    .lines()
                    .forEach(s -> {
                        String[] values = (s.split(";"));
                        sources.add(new RSSSource(values[0], values[1]));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sources;
    }

    public static void saveSource(List<RSSSource> source) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < source.size(); i++) {
            builder.append(String.format("%s;%s", source.get(i).getName(), source.get(i).getSource()));
            builder.append(i != source.size() - 1 ? "\n" : "");
        }

        try {
            saveStringToFile(CONFIG_FILE, builder.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
