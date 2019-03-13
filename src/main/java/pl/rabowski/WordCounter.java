package pl.rabowski;

import pl.rabowski.service.TranslationService;
import pl.rabowski.service.TranslationServiceEnPl;
import pl.rabowski.service.TranslationServicePlEn;
import pl.rabowski.util.CommonVariables;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    private TranslationService translationService;
    private Map<String, Integer> words = new ConcurrentHashMap<>();

    //TODO translation + threads


    public int getCount(String word) {
        if (checkIfValidWord(word)) {
            String parsedWord = word.toLowerCase().trim();
            String language = checkLanguage(parsedWord);
            String translatedWord = translateWord(parsedWord, language);
            if (words.containsKey(translatedWord)) {
                return words.get(translatedWord);
            }
        }
        return 0;
    }

    public void count(String word) {
        if (checkIfValidWord(word)) {

            String parsedWord = word.toLowerCase().trim();
            String language = checkLanguage(parsedWord);
            String translatedWord = translateWord(parsedWord, language).toLowerCase();

            if (words.containsKey(translatedWord) || words.containsKey(parsedWord)) {
                words.put(translatedWord, words.get(translatedWord) + 1);
            } else {
                words.put(translatedWord, 1);
            }
        }
    }

    private boolean checkIfValidWord(String word) {
        if (word != null && word.length() > 0) {
            Pattern pattern = Pattern.compile(CommonVariables.REGEX);
            Matcher matcher = pattern.matcher(word);
            return matcher.matches();
        }
        return false;
    }

    private String checkLanguage(String word) {
        if (CommonVariables.englishWords.contains(word)) {
            return "EN";
        } else if (CommonVariables.germanWords.contains(word)) {
            return "DE";
        } else if (CommonVariables.polishWords.contains(word)){
            return "PL";
        }
        return "";
    }

    private String translateWord(String word, String language){
        if(language.equals("EN")){
            translationService = new TranslationServiceEnPl();
            return translationService.translate(word);
        } if(language.equals("PL")){
            translationService = new TranslationServicePlEn();
            return translationService.translate(word);
        }
        return word;
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void setWords(Map<String, Integer> words) {
        this.words = words;
    }

}
