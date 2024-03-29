package com.nagarro.consumedbackend.service.consumeAPI;

import com.nagarro.consumedbackend.Constants.RequestURL;
import com.nagarro.consumedbackend.dto.consumeApiResponse.NationalityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FetchNationalityService {
    private final WebClient fetchNationality;

    @Autowired
    public FetchNationalityService(@Qualifier("fetchNationality") WebClient fetchNationality){
        this.fetchNationality = fetchNationality;
    }

    public Mono<ResponseEntity<NationalityResponse>> fetch(String username){
        String params = "?name=".concat(username);
        return fetchNationality.get()
                .uri(RequestURL.FETCH_NATIONALITY_BY_USER.concat(params))
                .retrieve()
                .toEntity(NationalityResponse.class);
    }
}
