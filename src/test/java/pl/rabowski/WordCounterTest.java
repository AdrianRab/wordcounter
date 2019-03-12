package pl.rabowski;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordCounterTest {
    private String test;
    private WordCounter wordCounter;
    private Map <String, Integer> words;

    @Nested
    @DisplayName("Tests for word counter method")
    class WordCounterMethodTest {

        @BeforeEach
        void setup() {
            wordCounter = new WordCounter();
            words = new ConcurrentHashMap<String, Integer>();
            test = "124";
        }

        @Test
        @DisplayName("Should return one occurrence")
        void wordHasOneOccurrence() {
            assertEquals(1, wordCounter.getCount(test));
        }

        @Test
        @DisplayName("Should return two occurrences")
        void wordHasTwoOccurrences() {
            words.put(test, 1);
            wordCounter.setWords(words);

            assertEquals(2, wordCounter.getCount(test));
        }

    }
}

