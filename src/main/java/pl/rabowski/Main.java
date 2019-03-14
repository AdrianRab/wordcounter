package pl.rabowski;

import pl.rabowski.util.CommonVariables;

public class Main {
    public static void main(String[] args) {
        WordCounter wordCounter = new WordCounter();
        new Thread(runFirstThread(wordCounter)).start();
        new Thread(runSecondThread(wordCounter)).start();

        System.out.println(CommonVariables.deEnDictionary);
        System.out.println(CommonVariables.enDeDictionary);

        System.out.println(CommonVariables.enPlPlEnDictionary);
        System.out.println(CommonVariables.dePlPlDeDictionary);
    }

    private static Runnable runFirstThread(WordCounter wordCounter){
        Runnable first = () -> {
            String[] words = {"ala", "kot", "masło", "Ala"};
            for (int i = 0; i < words.length; i++) {
                wordCounter.count(words[i]);
            }

            for (int j = 0; j < words.length; j++) {
                System.out.println("pierwsza lista słowo: " + words[j] + " ilość występowania: " + wordCounter.getCount(words[j]));
            }

        };
        return first;
    }

    private static Runnable runSecondThread(WordCounter wordCounter){
        Runnable second = () -> {
            String[] word2 = {"ala", "kot", "test", "ala"};
            for (int i = 0; i < word2.length; i++) {
                wordCounter.count(word2[i]);
            }

            for (int j = 0; j < word2.length; j++) {
                System.out.println("druga lista słowo " + word2[j] + " ilość występowania: " + wordCounter.getCount(word2[j]));
            }
        };

        return second;
    }

}
