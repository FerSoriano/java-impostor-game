package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordsDAO {
    private final String DIR = "data";
    private final String FILE_NAME;
    public static final String COMMA_DELIMITER = ",";

    public WordsDAO(String archivo) {
        this.FILE_NAME = DIR + "/" + archivo + ".csv";
    }

    public List<String> loadWords() {
        File dir = new File(DIR);

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("❌ Error: No se pudo crear el directorio de datos: " + DIR);
                return null;
            }
        }

        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                Collections.addAll(words, values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }
}
