package pl.rabowski;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordCounterTest {
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
            testWord = "124";
            testWord2 = "test 2";
            testWord3 = "124";
        }

        @Test
        @DisplayName("Should return one occurrence")
        void wordHasOneOccurrence() {
            assertEquals(1, wordCounter.getCount(testWord));
        }

        @Test
        @DisplayName("Should return two occurrences")
        void wordHasTwoOccurrences() {
            words.put(testWord, 1);
            words.put(testWord2, 5);
            wordCounter.setWords(words);

            assertEquals(2, wordCounter.getCount(testWord));
        }

        @Test
        @DisplayName("Should return three occurrences when inserting two String objects with same value")
        void wordHasThreeOccurrences() {
            words.put(testWord, 1);
            words.put(testWord3, 2);
            wordCounter.setWords(words);

            assertEquals(3, wordCounter.getCount(testWord));
        }

        @ParameterizedTest
        @DisplayName("Should check ten different strings and return one for each")
        @ValueSource(strings = {"23", "test", "BIGLETTER", "SMALLLETTER", "WITH SPACE", "WITH   tab", "@", "-+=", "#$%$$"})
        void shouldCheckOccurrenceForTenWords_counterShouldBeOne(String word) {
            assertEquals(1, wordCounter.getCount(word));
        }

        @ParameterizedTest
        @DisplayName("Should check ten the same strings and return one for each")
        @ValueSource(strings = {"test", "test", "test", "test", "test", "test", "test", "test", "test"})
        void shouldCheckOccurrenceForTenSameWords_counterShouldBeOne(String word) {
            assertEquals(1, wordCounter.getCount(word));
        }

        @ParameterizedTest
        @DisplayName("Should check special characters and return one for each")
        @ValueSource(strings = {" ", "   ", "\t", "\n", "\r"})
        void shouldCheckOccurrenceForTenSpecialCharacters_counterShouldBeOne(String word) {
            assertEquals(1, wordCounter.getCount(word));
        }

        @Test
        @DisplayName("Should check null and return zero")
        void shouldCheckOccurrenceForNull_counterShouldBeZero() {
            String word = null;
            assertEquals(0, wordCounter.getCount(word));
        }

        @Test
        @DisplayName("Should check empty string and return zero")
        void shouldCheckOccurrenceForEmptyString_counterShouldBeZero() {
            String word = "";
            assertEquals(0, wordCounter.getCount(word));
        }
    }
}

