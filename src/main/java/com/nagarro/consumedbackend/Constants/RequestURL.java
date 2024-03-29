package com.nagarro.consumedbackend.Constants;

import org.springframework.stereotype.Component;

@Component
public interface RequestURL {
    String FETCH_USER_NAME = "https://randomuser.me/api/";
    String FETCH_NATIONALITY_BY_USER = "https://api.nationalize.io/";
    String FETCH_GENDER_BY_USER = "https://api.genderize.io/";
}
