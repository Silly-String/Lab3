package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // Java is being stupid and won't let me type the numbre 3
    private static final int MY_NUMBER = 3;

    // For Alpha-3 code to Country name
    private Map<String, String> codeToCountryMap;
    // For Country name to Alpha-3 code
    private Map<String, String> countryToCodeMap;
    // For tracking the number of countries
    private int numCountries;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */

    public CountryCodeConverter(String filename) {

        codeToCountryMap = new HashMap<>();
        countryToCodeMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // Process each line (skip the header)
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\t");
                if (parts.length >= MY_NUMBER) {
                    String countryName = parts[0];
                    String alpha3Code = parts[2];

                    // Populate maps
                    codeToCountryMap.put(alpha3Code, countryName);
                    countryToCodeMap.put(countryName, alpha3Code);
                }
            }

            // Total number of countries
            numCountries = codeToCountryMap.size();

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {

        return codeToCountryMap.get(code);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {

        return countryToCodeMap.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {

        return numCountries;
    }
}
