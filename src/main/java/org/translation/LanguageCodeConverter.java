package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    // Map for language lookup
    private Map<String, String> codeToLanguageMap;
    // Map for reverse lookup
    private Map<String, String> languageToCodeMap;
    private int numLanguages;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        codeToLanguageMap = new HashMap<>();
        languageToCodeMap = new HashMap<>();
        numLanguages = 0;

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // Skip the first line (header)
            for (String line : lines.subList(1, lines.size())) {
                // Split by tab
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String languageName = parts[0];
                    String languageCode = parts[1];
                    codeToLanguageMap.put(languageCode, languageName);
                    languageToCodeMap.put(languageName, languageCode);
                    numLanguages++;
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        return codeToLanguageMap.getOrDefault(code, "Unknown Language Code");
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        return languageToCodeMap.getOrDefault(language, "Unknown Language");
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return numLanguages;
    }
}
