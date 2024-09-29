package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // Maps country code to a map of language code to translation
    private Map<String, Map<String, String>> countryTranslations;
    // List of country codes
    private List<String> countryCodes;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {

        countryTranslations = new HashMap<>();
        countryCodes = new ArrayList<>();

        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                String alpha3 = countryObject.getString("alpha3");
                Map<String, String> translations = new HashMap<>();

                // Populate translations for the current country
                for (String languageCode : countryObject.keySet()) {
                    if (!"id".equals(languageCode) && !"alpha2".equals(languageCode)
                            && !"alpha3".equals(languageCode)) {
                        translations.put(languageCode, countryObject.getString(languageCode));
                    }
                }

                countryTranslations.put(alpha3, translations);
                countryCodes.add(alpha3);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // Retrieve the map of language codes for the given country, or an empty map if not found
        Map<String, String> translations = countryTranslations.getOrDefault(country, new HashMap<>());

        // Return a new ArrayList of the language codes (keys)
        return new ArrayList<>(translations.keySet());
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String country, String language) {
        Map<String, String> translations = countryTranslations.get(country);

        // Check if the translations map is not null
        if (translations != null) {
            // Retrieve the translation for the specified language
            String translation = translations.get(language);
            // Return the translation if found, or null if not
            return translation;
        }
        // Return null if no translations are found for the specified country
        return null;
    }
}
