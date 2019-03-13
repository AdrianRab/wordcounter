package pl.rabowski.service;

import pl.rabowski.util.CommonVariables;

public class TranslationServicePlEn implements TranslationService {


    @Override
    public String translate(String word) {
        if(CommonVariables.plEnDictionary.containsKey(word)){
            return CommonVariables.plEnDictionary.get(word);
        }
        return word;
    }
}
