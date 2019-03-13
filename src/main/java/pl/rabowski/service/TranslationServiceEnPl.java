package pl.rabowski.service;

import pl.rabowski.util.CommonVariables;

public class TranslationServiceEnPl implements TranslationService{
    @Override
    public String translate(String word) {
        if(CommonVariables.enPlDictionary.containsKey(word)){
            return CommonVariables.enPlDictionary.get(word);
        }
        return word;
    }
}
