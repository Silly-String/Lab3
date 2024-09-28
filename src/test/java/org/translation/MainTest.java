package org.translation;

import org.junit.Test;
import org.translation.JSONTranslator;
import org.translation.Main;
import org.translation.Translator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MainTest {

    public String getMainOutput() {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("quit\n".getBytes()));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        // action
        Translator translator = new JSONTranslator();
        Main.runProgram(translator);

        System.setOut(originalOut);
        System.setIn(originalIn);

        return bos.toString();
    }

    public String getMockedOutput() {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("Canada\n".getBytes()));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        // action
        Translator translator = new Translator() {
            @Override
            public List<String> getCountryLanguages(String country) {
                System.setIn(new ByteArrayInputStream("English\n".getBytes()));
                return new ArrayList<>(List.of("en"));
            }

            @Override
            public List<String> getCountries() {
                return new ArrayList<>(List.of("can"));
            }

            @Override
            public String translate(String country, String language) {
                System.setIn(new ByteArrayInputStream("quit\n".getBytes()));
                return "Canada";
            }
        };
        Main.runProgram(translator);

        System.setOut(originalOut);
        System.setIn(originalIn);

        return bos.toString();
    }

    @Test
    public void mainRunProgramWithCustomTranslatorCanadaEnglish() {
        String result = getMockedOutput();
        System.out.println(result);
        String expected = "Canada\n" +
                "select a country from above:\n" +
                "English\n" +
                "select a language from above:\n" +
                "Canada in English is Canada\n" +
                "Press enter to continue or quit to exit.";
        assertTrue("For input:\n\n'Canada\nEnglish\nquit\n'\nthe program output was expected to contain:\n\n" +
                expected + "\n\n" +
                "actual output:\n\n" + result,
                result.contains(expected));
    }


    @Test
    public void mainRunProgramRunAndQuitImmediately() {
        String result = getMainOutput();
        System.out.println(result);
        assertTrue("Program output expected to contain Canada and 'select a country from above:'\n\nactual output:\n" + result, result.contains("select a country from above:") && result.contains("Canada"));
    }

    @Test
    public void mainRunProgramRunCountriesSorted() {
        String result = getMainOutput();
        System.out.println(result);
        String expected = "Afghanistan\nAlbania\nAlgeria\nAndorra\n" +
                "Angola\nAntigua and Barbuda\nArgentina\nArmenia\nAustralia\n" +
                "Austria\nAzerbaijan\nBahamas (the)\nBahrain\nBangladesh\nBarbados\n" +
                "Belarus\nBelgium\nBelize\nBenin\nBhutan\n" +
                "Bolivia (Plurinational State of)\nBosnia and Herzegovina\n" +
                "Botswana\nBrazil\nBrunei Darussalam\nBulgaria\nBurkina Faso\n" +
                "Burundi\nCabo Verde\nCambodia\nCameroon\nCanada\n" +
                "Central African Republic (the)\nChad\nChile\nChina\n" +
                "Colombia\nComoros (the)\nCongo (the Democratic Republic of the)\n" +
                "Congo (the)\nCosta Rica\nCroatia\nCuba\nCyprus\nCzechia\n" +
                "Côte d'Ivoire\nDenmark\nDjibouti\nDominica\nDominican Republic (the)\n" +
                "Ecuador\nEgypt\nEl Salvador\nEquatorial Guinea\nEritrea\nEstonia\n" +
                "Eswatini\nEthiopia\nFiji\nFinland\nFrance\nGabon\nGambia (the)\n" +
                "Georgia\nGermany\nGhana\nGreece\nGrenada\nGuatemala\nGuinea\n" +
                "Guinea-Bissau\nGuyana\nHaiti\nHonduras\nHungary\nIceland\nIndia\n" +
                "Indonesia\nIran (Islamic Republic of)\nIraq\nIreland\nIsrael\nItaly\n" +
                "Jamaica\nJapan\nJordan\nKazakhstan\nKenya\nKiribati\n" +
                "Korea (the Democratic People's Republic of)\nKorea (the Republic of)\n" +
                "Kuwait\nKyrgyzstan\nLao People's Democratic Republic (the)\nLatvia\n" +
                "Lebanon\nLesotho\nLiberia\nLibya\nLiechtenstein\nLithuania\nLuxembourg\n" +
                "Madagascar\nMalawi\nMalaysia\nMaldives\nMali\nMalta\nMarshall Islands (the)\n" +
                "Mauritania\nMauritius\nMexico\nMicronesia (Federated States of)\n" +
                "Moldova (the Republic of)\nMonaco\nMongolia\nMontenegro\nMorocco\n" +
                "Mozambique\nMyanmar\nNamibia\nNauru\nNepal\nNetherlands (the)\n" +
                "New Zealand\nNicaragua\nNiger (the)\nNigeria\nNorway\nOman\nPakistan\n" +
                "Palau\nPanama\nPapua New Guinea\nParaguay\nPeru\nPhilippines (the)\n" +
                "Poland\nPortugal\nQatar\nRepublic of North Macedonia\nRomania\n" +
                "Russian Federation (the)\nRwanda\nSaint Kitts and Nevis\nSaint Lucia\n" +
                "Saint Vincent and the Grenadines\nSamoa\nSan Marino\nSao Tome and Principe\n" +
                "Saudi Arabia\nSenegal\nSerbia\nSeychelles\nSierra Leone\nSingapore\n" +
                "Slovakia\nSlovenia\nSolomon Islands\nSomalia\nSouth Africa\nSouth Sudan\n" +
                "Spain\nSri Lanka\nSudan (the)\nSuriname\nSweden\nSwitzerland\n" +
                "Syrian Arab Republic\nTajikistan\nTanzania, United Republic of\n" +
                "Thailand\nTimor-Leste\nTogo\nTonga\nTrinidad and Tobago\nTunisia\n" +
                "Turkey\nTurkmenistan\nTuvalu\nUganda\nUkraine\nUnited Arab Emirates (the)\n" +
                "United Kingdom of Great Britain and Northern Ireland (the)\n" +
                "United States of America (the)\nUruguay\nUzbekistan\nVanuatu\n" +
                "Venezuela (Bolivarian Republic of)\nViet Nam\nYemen\nZambia\nZimbabwe";
        assertTrue("Countries are not displayed in sorted order\n\n"
                + "expected:\n\n" + expected + "\n\nactual:\n\n" + result + "\n(only the country names are compared in this test)", result.contains(expected));
    }

}
