package pl.rabowski;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordCounter {
    private Map<String, Integer> words = new ConcurrentHashMap<String, Integer>();
    private int numberOfOccurrences;

    public int getCount(String word){
        count(word);
        return numberOfOccurrences;
    }

    private void count(String word){
        if(word != null || word.length()>0){
            if(words.containsKey(word)){
                numberOfOccurrences = words.get(word);
                numberOfOccurrences++;
                words.put(word, numberOfOccurrences);
            }else{
                numberOfOccurrences = 1;
                words.put(word, numberOfOccurrences);
            }
        }
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void setWords(Map<String, Integer> words) {
        this.words = words;
    }

}
