package pl.rabowski.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonVariables {
    public static final String REGEX = "^\\p{L}*$";
    public static List<String> englishWords =
            new ArrayList<>(Arrays.asList("dog", "cat", "roll", "car", "dictionary", "ball", "play", "horse",
                    "Warsaw", "basketball", "javelin", "Germany", "universe","sun"));
    public static List<String> germanWords =
            new ArrayList<>(Arrays.asList("Hund", "Katze", "Brötchen", "Auto", "Wörterbuch", "Ball", "spielen", "Pferd",
                    "Warschau", "Basketball", "Speer", "Deutschland", "Kosmos", "Sonne"));
    public static List<String> polishWords =
            new ArrayList<>(Arrays.asList("pies", "kot", "bułka", "samochód", "słownik", "piłka", "grać", "koń",
                    "Warszawa", "koszykówka", "oszczep", "Niemcy", "kosmos", "słońce"));

}
