package com.nagarro.consumedbackend.async;

import com.nagarro.consumedbackend.dto.consumeApiResponse.GenderResponse;
import com.nagarro.consumedbackend.dto.consumeApiResponse.NationalityResponse;
import com.nagarro.consumedbackend.service.consumeAPI.FetchGenderService;
import com.nagarro.consumedbackend.service.consumeAPI.FetchNationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class FetchDetailsParallelExecutor {
    private final FetchNationalityService fetchNationalityService;
    private final FetchGenderService fetchGenderService;

    @Autowired
    public FetchDetailsParallelExecutor(
            FetchNationalityService fetchNationalityService,
            FetchGenderService fetchGenderService
    ){
        this.fetchGenderService = fetchGenderService;
        this.fetchNationalityService = fetchNationalityService;
    }

    @Async
    public CompletableFuture<ResponseEntity<NationalityResponse>> fetchNationality(String username) {
        return fetchNationalityService
                .fetch(username)
                .toFuture();
    }

    @Async
    public CompletableFuture<ResponseEntity<GenderResponse>> fetchGender(String username) {
        return fetchGenderService
                .fetch(username)
                .toFuture();
    }
}