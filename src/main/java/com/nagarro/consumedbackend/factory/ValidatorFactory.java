package com.nagarro.consumedbackend.factory;

import org.springframework.stereotype.Component;

@Component
public class ValidatorFactory {
    public static Validator getValidator(String input){
        if("numeric".equalsIgnoreCase(input)){
            return NumericValidation.getInstance();
        }else if("name".equalsIgnoreCase(input)){
            return EnglishAlphabetValidation.getInstance();
        }else{
            throw new IllegalArgumentException("Unsupported input type : "+ input);
        }
    }
}
