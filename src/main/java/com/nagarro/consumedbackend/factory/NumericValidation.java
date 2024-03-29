package com.nagarro.consumedbackend.factory;

import org.hibernate.TypeMismatchException;

public class NumericValidation implements Validator{
    private final static NumericValidation numValidationConfig = new NumericValidation();
    private NumericValidation() {}
    public static NumericValidation getInstance() {
        return numValidationConfig;
    }
    @Override
    public boolean validate(String input) {
        try{
            int i = Integer.parseInt(input);
            if(i > 0 && i <= 5) {
                return true;
            }
        } catch(TypeMismatchException e) {
            e.printStackTrace();
        }
        return false;
    }
}
