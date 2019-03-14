package pl.rabowski;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.rabowski.exceptions.WordValidationException;
import pl.rabowski.service.TranslationService;
import pl.rabowski.util.CommonVariables;

public class WordCounter {
    private Map<String, Integer> words = new ConcurrentHashMap<>();
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public int getCount(String word) {
        if (checkIfValidWord(word)) {
            String parsedWord = word.toLowerCase().trim();

            String wordInEnglishOrGerman = TranslationService.deEnEnDeDictionary.get(parsedWord);
            String wordInPolishOrEnglish = TranslationService.enPlPlEnDictionary.get(parsedWord);
            String wordInPolishOrGerman = TranslationService.dePlPlDeDictionary.get(parsedWord);

            if (words.containsKey(parsedWord)) {
                return words.get(parsedWord);
            } else if (wordInEnglishOrGerman != null && words.containsKey(wordInEnglishOrGerman.toLowerCase())) {
                return words.get(wordInEnglishOrGerman.toLowerCase());
            } else if (wordInPolishOrEnglish != null && words.containsKey(wordInPolishOrEnglish.toLowerCase())) {
                return words.get(wordInPolishOrEnglish.toLowerCase());
            } else if (wordInPolishOrGerman != null && words.containsKey(wordInPolishOrGerman.toLowerCase())) {
                return words.get(wordInPolishOrGerman.toLowerCase());
            }
        }
        return 0;
    }

    private boolean checkIfValidWord(String word) {
        if (word != null && word.length() > 0) {
            Pattern pattern = Pattern.compile(CommonVariables.REGEX);
            Matcher matcher = pattern.matcher(word);
            return matcher.matches();
        }
        return false;
    }

    public void count(String word) throws WordValidationException {
        reentrantLock.lock();
        if (checkIfValidWord(word)) {

            String parsedWord = word.toLowerCase().trim();
            String language = checkLanguage(parsedWord);
            String translatedWord = "";

            if (words.containsKey(parsedWord)) {
                words.put(parsedWord, words.get(parsedWord) + 1);

            } else if (language.equals("DE") && TranslationService.dePLDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(TranslationService.dePLDictionary.get(parsedWord).toLowerCase())) {
                translatedWord = TranslationService.dePLDictionary.get(parsedWord).toLowerCase();
                words.put(translatedWord, words.get(translatedWord) + 1);

            } else if (language.equals("DE") && TranslationService.deEnDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(TranslationService.deEnDictionary.get(parsedWord).toLowerCase())) {

                words.put(TranslationService.deEnDictionary.get(parsedWord).toLowerCase(), words.get(TranslationService.deEnDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("EN") && TranslationService.enDeDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(TranslationService.enDeDictionary.get(parsedWord).toLowerCase())) {

                words.put(TranslationService.enDeDictionary.get(parsedWord).toLowerCase(), words.get(TranslationService.enDeDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("EN") && TranslationService.enPlDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(TranslationService.enPlDictionary.get(parsedWord).toLowerCase())) {

                words.put(TranslationService.enPlDictionary.get(parsedWord).toLowerCase(), words.get(TranslationService.enPlDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("PL") && TranslationService.plDeDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(TranslationService.plDeDictionary.get(parsedWord).toLowerCase())) {

                words.put(TranslationService.plDeDictionary.get(parsedWord).toLowerCase(), words.get(TranslationService.plDeDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("PL") && TranslationService.plEnDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(TranslationService.plEnDictionary.get(parsedWord).toLowerCase())) {
                words.put(TranslationService.plEnDictionary.get(parsedWord).toLowerCase(), words.get(TranslationService.plEnDictionary.get(parsedWord).toLowerCase()) + 1);

            } else {
                words.put(parsedWord, 1);
            }
        } else {
            throw new WordValidationException("Word: " + word + ", has incorrect format");
        }
        reentrantLock.unlock();
    }

    private String checkLanguage(String word) {
        if (CommonVariables.englishWords.stream().anyMatch(w -> w.toLowerCase().equalsIgnoreCase(word))) {
            return "EN";
        } else if (CommonVariables.germanWords.stream().anyMatch(w -> w.toLowerCase().equalsIgnoreCase(word))) {
            return "DE";
        } else if (CommonVariables.polishWords.stream().anyMatch(w -> w.toLowerCase().equalsIgnoreCase(word))) {
            return "PL";
        }
        return "";
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void setWords(Map<String, Integer> words) {
        this.words = words;
    }

}
