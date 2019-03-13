package pl.rabowski;

import pl.rabowski.service.TranslationService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    private TranslationService translationService;
    private Map<String, Integer> words = new ConcurrentHashMap<>();
    private final String REGEX = "^\\p{L}*$";

    public WordCounter(Map<String, Integer> words) {
        this.words = words;
    }

    public WordCounter() {
    }

    //TODO regex + translation + threads


    public int getCount(String word) {
        if (checkIfValidWord(word)) {

            String parsedWord = word.toLowerCase().trim();

            if (words.containsKey(parsedWord)) {
                return words.get(parsedWord);
            }
        }
        return 0;
    }

    public void count(String word) {
        if (checkIfValidWord(word)) {

            String parsedWord = word.toLowerCase().trim();

            if (words.containsKey(parsedWord)) {
                words.put(parsedWord, words.get(parsedWord) + 1);
            } else {
                words.put(parsedWord, 1);
            }
        }
    }

    private boolean checkIfValidWord(String word) {
        if (word != null && word.length() > 0) {
            Pattern pattern = Pattern.compile(REGEX);

            Matcher matcher = pattern.matcher(word);
            return matcher.matches();
        }
        return false;
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void setWords(Map<String, Integer> words) {
        this.words = words;
    }

}
