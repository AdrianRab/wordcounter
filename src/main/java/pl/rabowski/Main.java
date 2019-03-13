package pl.rabowski;

public class Main {
    public static void main(String[] args) {
        WordCounter wordCounter = new WordCounter();
        new Thread(runFirstThread(wordCounter)).start();
        new Thread(runSecondThread(wordCounter)).start();
    }

    private static Runnable runFirstThread(WordCounter wordCounter){
        Runnable first = () -> {
            String[] words = {"ala", "kot", "masło", "Ala"};
            for (int i = 0; i < words.length; i++) {
                System.out.println(words[i]);
                System.out.println(wordCounter);
                wordCounter.count(words[i]);
            }

            for (int j = 0; j < words.length; j++) {
                System.out.println("pierwsza lista słowo: " + words[j] + " ilość występowania: " + wordCounter.getCount(words[j]));
            }

//            System.out.println(wordCounter.getCount("ala"));
        };
        return first;
    }

    private static Runnable runSecondThread(WordCounter wordCounter){
        Runnable second = () -> {
            String[] word2 = {"ala", "kot", "test", "ala"};
            for (int i = 0; i < word2.length; i++) {
                System.out.println(word2[i]);
                System.out.println(wordCounter);
                wordCounter.count(word2[i]);
            }

            for (int j = 0; j < word2.length; j++) {
                System.out.println("druga lista słowo " + word2[j] + " ilość występowania: " + wordCounter.getCount(word2[j]));
            }

//            System.out.println(wordCounter.getCount("ala"));
        };

        return second;
    }

}
