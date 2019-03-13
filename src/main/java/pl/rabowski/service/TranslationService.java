package pl.rabowski.service;

public interface TranslationService {

    void translate(String word, String language);

    String checkLanguage(String word);


}
