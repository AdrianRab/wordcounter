package pl.rabowski.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonVariables {
    public static final String REGEX = "^\\p{L}*$";
    public static List<String> englishWords =
            new ArrayList<>(Arrays.asList("dog", "cat", "roll", "car", "dictionary", "sphere", "play", "horse",
                    "warsaw", "athlete", "javelin", "germany", "mug", "sun"));
    public static List<String> germanWords =
            new ArrayList<>(Arrays.asList("hund", "katze", "brötchen", "auto", "wörterbuch", "kugel", "spielen", "pferd",
                    "warschau", "athlet", "speer", "deutschland", "becher", "sonne"));
    public static List<String> polishWords =
            new ArrayList<>(Arrays.asList("pies", "kot", "bułka", "samochód", "słownik", "kula", "grać", "koń",
                    "warszawa", "atleta", "oszczep", "niemcy", "kubek", "słońce"));

}
