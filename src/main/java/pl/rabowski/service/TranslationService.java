package pl.rabowski.service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import pl.rabowski.util.CommonVariables;

public class TranslationService {


    public static Map<String, String> enPlDictionary = IntStream.range(0, CommonVariables.englishWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.englishWords.get(i), i -> CommonVariables.polishWords.get(i)));

    public static Map<String, String> plEnDictionary = IntStream.range(0, CommonVariables.polishWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.polishWords.get(i), i -> CommonVariables.englishWords.get(i)));

    public static Map<String, String> plDeDictionary = IntStream.range(0, CommonVariables.polishWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.polishWords.get(i), i -> CommonVariables.germanWords.get(i)));

    public static Map<String, String> dePLDictionary = IntStream.range(0, CommonVariables.germanWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.germanWords.get(i), i -> CommonVariables.polishWords.get(i)));

    public static Map<String, String> deEnDictionary = IntStream.range(0, CommonVariables.germanWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.germanWords.get(i), i -> CommonVariables.englishWords.get(i)));

    public static Map<String, String> enDeDictionary = IntStream.range(0, CommonVariables.englishWords.size()).boxed()
            .collect(Collectors.toMap(i -> CommonVariables.englishWords.get(i), i -> CommonVariables.germanWords.get(i)));

    public static Map<String, String> enPlPlEnDictionary = Stream.concat(enPlDictionary.entrySet().stream(), plEnDictionary.entrySet().stream())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue));

    public static Map<String, String> dePlPlDeDictionary = Stream.concat(plDeDictionary.entrySet().stream(), dePLDictionary.entrySet().stream())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue));

    public static Map<String, String> deEnEnDeDictionary = Stream.concat(deEnDictionary.entrySet().stream(), enDeDictionary.entrySet().stream())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue));
}
