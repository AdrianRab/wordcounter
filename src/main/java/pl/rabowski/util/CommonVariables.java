package pl.rabowski.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommonVariables {
    public static final String REGEX = "^\\p{L}*$";
    public static List<String> englishWords =
            new ArrayList<>(Arrays.asList("dog", "cat", "Roll", "car", "dictionary", "ball", "play", "horse",
                    "Warsaw", "basketball", "javelin", "Germany", "universe","sun"));
    public static List<String> germanWords =
            new ArrayList<>(Arrays.asList("Hund", "Katze", "Brötchen", "Auto", "Wörterbuch", "Ball", "spielen", "Pferd",
                    "Warschau", "Basketball", "Speer", "Deutschland", "Kosmos", "Sonne"));
    public static List<String> polishWords =
            new ArrayList<>(Arrays.asList("Pies", "Kot", "bułka", "samochód", "słownik", "piłka", "grać", "koń",
                    "Warszawa", "koszykówka", "oszczep", "Niemcy", "kosmos", "słońce"));

    public static Map<String, String> enPlDictionary = IntStream.range(0, CommonVariables.englishWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.englishWords.get(i), i -> CommonVariables.polishWords.get(i)));
    public static Map<String, String> plEnDictionary = IntStream.range(0, CommonVariables.polishWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.polishWords.get(i), i -> CommonVariables.englishWords.get(i)));

//    public static Map<String, String> polishWordsMap = polishWords.stream().map()

}
