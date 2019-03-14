package pl.rabowski;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.rabowski.exceptions.WordValidationException;

public class WordCounterTest {
    private String[] tableOfWords, wordsInDiffentLanguage;
    private String englishWord, polishWord, germanWord;
    private String testWord, testWord2, testWord3;
    private WordCounter wordCounter;
    private Map<String, Integer> words;

    @Nested
    @DisplayName("Tests for word counter method")
    class WordCounterMethodTest {

        @BeforeEach
        void setup() {
            wordCounter = new WordCounter();
            words = new ConcurrentHashMap<>();
            testWord = "słowo";
            testWord2 = "wording";
            testWord3 = "słowo";
        }

        @Test
        @DisplayName("Should return one occurrence")
        void wordHasOneOccurrence() throws WordValidationException {
            wordCounter.count(testWord);

            assertEquals(1, wordCounter.getCount(testWord));
        }

        @Test
        @DisplayName("Should return two occurrences")
        void wordHasTwoOccurrences() throws WordValidationException {
            words.put(testWord, 1);
            words.put(testWord2, 5);
            wordCounter.setWords(words);

            wordCounter.count(testWord);

            assertEquals(2, wordCounter.getCount(testWord));
        }

        @Test
        @DisplayName("Should return three occurrences when inserting two String objects with same value")
        void wordHasThreeOccurrences() throws WordValidationException {
            words.put(testWord, 1);
            words.put(testWord3, 2);
            wordCounter.setWords(words);

            wordCounter.count(testWord);

            assertEquals(3, wordCounter.getCount(testWord));
        }

        @ParameterizedTest
        @DisplayName("Should check ten different strings and return one for each")
        @ValueSource(strings = {"Honig", "test", "BIGLETTER", "smallletter", "space", "universe", "Ala", "Żyła", "Brötchen"})
        void shouldCheckOccurrenceForTenWords_counterShouldBeOne(String word) throws WordValidationException {
            wordCounter.count(word);

            assertEquals(1, wordCounter.getCount(word));
        }

        @ParameterizedTest
        @DisplayName("Should check ten the same strings and return one for each")
        @ValueSource(strings = {"kot", "cat", "kot", "Kot", "Cat", "kot", "Cat", "cat", "Cat"})
        void shouldCheckOccurrenceForTenSameWords_counterShouldBeOne(String word) throws WordValidationException {
            wordCounter.count(word);

            assertEquals(1, wordCounter.getCount(word));
        }

        @ParameterizedTest
        @DisplayName("Should check special characters and not accept them")
        @ValueSource(strings = {" ", "   ", "\t", "\n", "\r"})
        void shouldCheckOccurrenceForTenSpecialCharacters_counterShouldBeZero(String word) throws WordValidationException {
            try {
                wordCounter.count(word);
            } catch (Exception e) {
                e.getMessage();
            }

            assertEquals(0, wordCounter.getCount(word));
        }

        @ParameterizedTest
        @DisplayName("Should throw an exception when invalid word is passed")
        @ValueSource(strings = {" ", "   ", "\t", "\n", "\r", "", "123", "%32", " r ", "^", "!", ","})
        void shouldCheckIfWordIsValidAndThrowAndException(String word) throws WordValidationException {

            assertThrows(WordValidationException.class, () -> wordCounter.count(word));
        }

        @Test
        @DisplayName("Should check null and return zero")
        void shouldCheckOccurrenceForNull_counterShouldBeZero() throws WordValidationException {
            String word = null;

            try {
                wordCounter.count(word);
            } catch (Exception e) {
                e.getMessage();
            }

            assertEquals(0, wordCounter.getCount(word));
        }

        @Test
        @DisplayName("Should check empty string and return zero")
        void shouldCheckOccurrenceForEmptyString_counterShouldBeZero() {
            String word = "";

            try {
                wordCounter.count(word);
            } catch (Exception e) {
                e.getMessage();
            }

            assertEquals(0, wordCounter.getCount(word));
        }

        @Test
        @DisplayName("Should check same value in two languages pl and en")
        void shouldCheckSameValueInTwoLanguagesPlEn_counterShouldBeTwo() throws WordValidationException {
            englishWord = "dog";
            polishWord = "pies";
            wordCounter.count(polishWord);
            wordCounter.count(englishWord);

            assertEquals(2, wordCounter.getCount(polishWord));
            assertEquals(2, wordCounter.getCount(englishWord));
        }

        @Test
        @DisplayName("Should check same value in two languages pl and de")
        void shouldCheckSameValueInTwoLanguagesPlDe_counterShouldBeTwo() throws WordValidationException {
            polishWord = "koń";
            germanWord = "pferd";
            wordCounter.count(polishWord);
            wordCounter.count(germanWord);

            assertEquals(2, wordCounter.getCount(germanWord));
            assertEquals(2, wordCounter.getCount(polishWord));
        }

        @Test
        @DisplayName("Should check same value in two languages de and en")
        void shouldCheckSameValueInTwoLanguagesDeEn_counterShouldBeTwo() throws WordValidationException {
            englishWord = "athlete";
            germanWord = "athlet";
            wordCounter.count(englishWord);
            wordCounter.count(germanWord);

            assertEquals(2, wordCounter.getCount(germanWord));
            assertEquals(2, wordCounter.getCount(englishWord));
        }

        @Test
        @DisplayName("Should check same value in two languages pl and en")
        void shouldCheckSameValueInAllLanguages_counterShouldBeThree() throws WordValidationException {
            englishWord = "dictionary";
            polishWord = "słownik";
            germanWord = "Wörterbuch";
            wordCounter.count(polishWord);
            wordCounter.count(englishWord);
            wordCounter.count(germanWord);

            assertEquals(3, wordCounter.getCount(polishWord));
            assertEquals(3, wordCounter.getCount(englishWord));
            assertEquals(3, wordCounter.getCount(germanWord));
        }
    }

    @Nested
    @DisplayName("Second test for threads")
    class WordCounterTestWithoutConcurrentTest {

        @BeforeEach
        void prepareData() {
            wordCounter = new WordCounter();
            tableOfWords = new String[]{"Ala", "kot", "pies", "komputer", "Mieszkanie", "ala"};
            wordsInDiffentLanguage = new String[]{"kot", "cat", "hund", "pies", "katze", "dictionary"};
            englishWord = "cat";
            germanWord = "hund";
        }

        @Test
        @DisplayName("Test with two threads and multi language")
        void testWithTwoThreads() throws InterruptedException {
            CountDownLatch cdl = new CountDownLatch(2);
            Thread firsThread = new Thread(runThread(wordCounter, tableOfWords, cdl));
            Thread secondThread = new Thread(runThread(wordCounter, tableOfWords, cdl));
            firsThread.start();
            secondThread.start();
            cdl.await();
            assertEquals(4, wordCounter.getCount("Ala"), "Expect to hit 4 times word Ala");
        }

        @Test
        @DisplayName("Test with two threads")
        void testWithTwoThreadsAndDifferentLanguage() throws InterruptedException {
            CountDownLatch cdl = new CountDownLatch(2);
            Thread firsThread = new Thread(runThread(wordCounter, wordsInDiffentLanguage, cdl));
            Thread secondThread = new Thread(runThread(wordCounter, wordsInDiffentLanguage, cdl));
            firsThread.start();
            secondThread.start();
            cdl.await();

            assertEquals(6, wordCounter.getCount(englishWord), "Expect to hit 6 times word " + englishWord);
            assertEquals(4, wordCounter.getCount(germanWord), "Expect to hit 4 times word " + germanWord);
        }
    }

    private Runnable runThread(WordCounter wordCounter, String[] words, CountDownLatch countDownLatch) {
        Runnable first = () -> {
            try {
                for (int i = 0; i < words.length; i++) {
                    wordCounter.count(words[i]);
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.getMessage();
            }
        };
        return first;
    }
}

