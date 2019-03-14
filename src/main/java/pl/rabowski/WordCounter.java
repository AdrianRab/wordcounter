package pl.rabowski;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.rabowski.exceptions.WordValidationException;
import pl.rabowski.util.CommonVariables;

public class WordCounter {
    private Map<String, Integer> words = new ConcurrentHashMap<>();

    //TODO translation + threads


    public int getCount(String word) {
        if (checkIfValidWord(word)) {
            String parsedWord = word.toLowerCase().trim();

            String wordInEnglishOrGerman = CommonVariables.deEnEnDeDictionary.get(parsedWord);
            String wordInPolishOrEnglish = CommonVariables.enPlPlEnDictionary.get(parsedWord);
            String wordInPolishOrGerman = CommonVariables.dePlPlDeDictionary.get(parsedWord);

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

    public synchronized void count(String word) throws WordValidationException {
        if (checkIfValidWord(word)) {

            String parsedWord = word.toLowerCase().trim();
            String language = checkLanguage(parsedWord);
            String translatedWord = "";

            if (words.containsKey(parsedWord)) {
                words.put(parsedWord, words.get(parsedWord) + 1);

            } else if (language.equals("DE") && CommonVariables.dePLDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(CommonVariables.dePLDictionary.get(parsedWord).toLowerCase())) {
                System.out.println(CommonVariables.dePLDictionary.get(parsedWord).toLowerCase());
                translatedWord = CommonVariables.dePLDictionary.get(parsedWord).toLowerCase();
                words.put(translatedWord, words.get(translatedWord) + 1);

            } else if (language.equals("DE") && CommonVariables.deEnDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(CommonVariables.deEnDictionary.get(parsedWord).toLowerCase())) {
                System.out.println(CommonVariables.deEnDictionary.get(parsedWord).toLowerCase());
                words.put(CommonVariables.deEnDictionary.get(parsedWord).toLowerCase(), words.get(CommonVariables.deEnDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("EN") && CommonVariables.enDeDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(CommonVariables.enDeDictionary.get(parsedWord).toLowerCase())) {
                System.out.println(CommonVariables.enDeDictionary.get(parsedWord).toLowerCase());
                words.put(CommonVariables.enDeDictionary.get(parsedWord).toLowerCase(), words.get(CommonVariables.enDeDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("EN") && CommonVariables.enPlDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(CommonVariables.enPlDictionary.get(parsedWord).toLowerCase())) {
                System.out.println(CommonVariables.enPlDictionary.get(parsedWord).toLowerCase());
                words.put(CommonVariables.enPlDictionary.get(parsedWord).toLowerCase(), words.get(CommonVariables.enPlDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("PL") && CommonVariables.plDeDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(CommonVariables.plDeDictionary.get(parsedWord).toLowerCase())) {
                System.out.println(CommonVariables.plDeDictionary.get(parsedWord).toLowerCase());
                words.put(CommonVariables.plDeDictionary.get(parsedWord).toLowerCase(), words.get(CommonVariables.plDeDictionary.get(parsedWord).toLowerCase()) + 1);

            } else if (language.equals("PL") && CommonVariables.plEnDictionary.get(parsedWord.toLowerCase()) != null
                    && words.containsKey(CommonVariables.plEnDictionary.get(parsedWord).toLowerCase())) {
                words.put(CommonVariables.plEnDictionary.get(parsedWord).toLowerCase(), words.get(CommonVariables.plEnDictionary.get(parsedWord).toLowerCase()) + 1);

            } else {
                words.put(parsedWord, 1);
            }
        } else {
            throw new WordValidationException("Word: " + word + ", has incorrect format");
        }
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
