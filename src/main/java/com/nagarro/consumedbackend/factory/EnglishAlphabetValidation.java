package com.nagarro.consumedbackend.factory;

import com.nagarro.consumedbackend.Constants.SortOrder;
import com.nagarro.consumedbackend.Constants.SortType;

import java.util.Arrays;

public class EnglishAlphabetValidation implements Validator {
    private final static EnglishAlphabetValidation alphabetValidationConfig =
            new EnglishAlphabetValidation();

    private EnglishAlphabetValidation() {}
    public static EnglishAlphabetValidation getInstance() {
        return alphabetValidationConfig;
    }

    @Override
    public boolean validate(String input) {
        boolean hasOrder = Arrays.stream(SortOrder.values())
                .anyMatch(order -> order.name().equalsIgnoreCase(input));
        boolean hasType = Arrays.stream(SortType.values())
                .anyMatch(order -> order.name().equalsIgnoreCase(input));
        return input.matches("[a-zA-z]+") && (hasType || hasOrder);
    }
}
