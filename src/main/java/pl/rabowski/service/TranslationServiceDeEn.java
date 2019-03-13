package pl.rabowski.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TranslationServiceDeEn implements TranslationService {
    private Set<String> englishWords = new HashSet<>();
    private Set<String> germanWords = new HashSet<>();
    private Map<String, String> dictionary = new HashMap();

    @Override
    public void translate(String word, String language) {

    }

    @Override
    public String checkLanguage(String word) {
        if (englishWords.contains(word)) {
            return "EN";
        } else if (germanWords.contains(word)) {
            return "DE";
        }
        return "";
    }
}
