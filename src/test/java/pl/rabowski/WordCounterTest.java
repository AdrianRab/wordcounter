package pl.rabowski;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordCounterTest {
    private String testWord, testWord2, testWord3;
    private WordCounter wordCounter;
    private Map<String, Integer> words;
    String[] words1, words2, words3, englishWords, polishWords, germanWOrds;

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
        void wordHasOneOccurrence() {
            wordCounter.count(testWord);

            assertEquals(1, wordCounter.getCount(testWord));
        }

        @Test
        @DisplayName("Should return two occurrences")
        void wordHasTwoOccurrences() {
            words.put(testWord, 1);
            words.put(testWord2, 5);
            wordCounter.setWords(words);

            wordCounter.count(testWord);

            assertEquals(2, wordCounter.getCount(testWord));
        }

        @Test
        @DisplayName("Should return three occurrences when inserting two String objects with same value")
        void wordHasThreeOccurrences() {
            words.put(testWord, 1);
            words.put(testWord3, 2);
            wordCounter.setWords(words);

            wordCounter.count(testWord);

            assertEquals(3, wordCounter.getCount(testWord));
        }

        @ParameterizedTest
        @DisplayName("Should check ten different strings and return one for each")
        @ValueSource(strings = {"Honig", "test", "BIGLETTER", "smallletter", "space", "universe", "Ala", "Żyła", "Brötchen"})
        void shouldCheckOccurrenceForTenWords_counterShouldBeOne(String word) {
            wordCounter.count(word);

            assertEquals(1, wordCounter.getCount(word));
        }

        @ParameterizedTest
        @DisplayName("Should check ten the same strings and return one for each")
        @ValueSource(strings = {"test", "test", "test", "test", "test", "test", "test", "test", "test"})
        void shouldCheckOccurrenceForTenSameWords_counterShouldBeOne(String word) {
            wordCounter.count(word);

            assertEquals(1, wordCounter.getCount(word));
        }

        @ParameterizedTest
        @DisplayName("Should check special characters and not accept them")
        @ValueSource(strings = {" ", "   ", "\t", "\n", "\r"})
        void shouldCheckOccurrenceForTenSpecialCharacters_counterShouldBeZero(String word) {
            wordCounter.count(word);

            assertEquals(0, wordCounter.getCount(word));
        }

        @Test
        @DisplayName("Should check null and return zero")
        void shouldCheckOccurrenceForNull_counterShouldBeZero() {
            String word = null;

            wordCounter.count(word);

            assertEquals(0, wordCounter.getCount(word));
        }

        @Test
        @DisplayName("Should check empty string and return zero")
        void shouldCheckOccurrenceForEmptyString_counterShouldBeZero() {
            String word = "";

            wordCounter.count(word);

            assertEquals(0, wordCounter.getCount(word));
        }
    }

    //nie bangla

    @Nested
    @DisplayName("Tests for word counter method with threads")
    class WordCounterMethodThreadsTest {


        @BeforeEach
        void prepareData() {
            wordCounter = new WordCounter();
            words1 = new String[]{"Ala", "kot", "pies", "komputer", "Mieszkanie", "ala"};
            words2 = new String[]{"ala", "pies", "table", "Mobile", "Komórka", "mieszkanie"};
        }

        @Test
        @DisplayName("Test with two threads")
        void testWithTwoThreads() {
            new Thread(runThread(wordCounter, words1)).start();
            new Thread(runSecondThread(wordCounter, words1)).start();
        }

        @AfterEach
        void testThreads(){
            assertEquals(4, wordCounter.getCount("Ala"), "Expect to hit 4 times word Ala");
        }
    }

    private Runnable runThread(WordCounter wordCounter, String[] words) {
        Runnable first = () -> {
            for (int i = 0; i < words.length; i++) {
                wordCounter.count(words[i]);
            }
        };
        return first;
    }

    private Runnable runSecondThread(WordCounter wordCounter, String[] words) {
        Runnable second = () -> {
            for (int i = 0; i < words.length; i++) {
                wordCounter.count(words[i]);
            }
        };
        return second;
    }
}

