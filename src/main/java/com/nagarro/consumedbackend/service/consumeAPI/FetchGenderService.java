package com.nagarro.consumedbackend.service.consumeAPI;

import com.nagarro.consumedbackend.Constants.RequestURL;
import com.nagarro.consumedbackend.dto.consumeApiResponse.GenderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FetchGenderService {
    private final WebClient fetchGender;

    @Autowired
    public FetchGenderService(@Qualifier("fetchGender") WebClient fetchGender){
        this.fetchGender = fetchGender;
    }

    public Mono<ResponseEntity<GenderResponse>> fetch(String username){
        String params = "?name=".concat(username);
        return fetchGender.get()
                .uri(RequestURL.FETCH_GENDER_BY_USER.concat(params))
                .retrieve()
                .toEntity(GenderResponse.class);
    }
}
