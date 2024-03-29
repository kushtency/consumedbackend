package com.nagarro.consumedbackend.service.consumeAPI;

import com.nagarro.consumedbackend.Constants.RequestURL;
import com.nagarro.consumedbackend.dto.consumeApiResponse.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FetchUserNameService {
    private final WebClient fetchUserName;

    @Autowired
    public FetchUserNameService(@Qualifier("fetchUser") WebClient fetchUserName) {
        this.fetchUserName = fetchUserName;
    }

    public Mono<UserResponse> fetch(String size) {
        String resultsParam = "?results=".concat(String.valueOf(size));
        String url = RequestURL.FETCH_USER_NAME.concat(resultsParam);

        return fetchUserName.get()
                .uri(url)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }
}
