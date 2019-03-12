package pl.rabowski;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordCounter {
    private Map<String, Integer> words = new ConcurrentHashMap<>();
    private int numberOfOccurrences;

    public int getCount(String word) {
        count(word);
        return numberOfOccurrences;
    }

    //zrobic public i osobno wywolywac w testach? Do przemyslenia.
    private void count(String word) {
        if (checkIfNotNullOrEmptyString(word)) {
            if (words.containsKey(word)) {
                numberOfOccurrences = words.get(word);
                numberOfOccurrences++;
                words.put(word, numberOfOccurrences);
            } else {
                numberOfOccurrences = 1;
                words.put(word, numberOfOccurrences);
            }
        }
    }

    private boolean checkIfNotNullOrEmptyString(String word) {
        return word != null && word.length() > 0;
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void setWords(Map<String, Integer> words) {
        this.words = words;
    }

}
