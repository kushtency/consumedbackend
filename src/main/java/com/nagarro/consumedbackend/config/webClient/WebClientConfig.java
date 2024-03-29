package com.nagarro.consumedbackend.config.webClient;

import com.nagarro.consumedbackend.Constants.RequestURL;
import com.nagarro.consumedbackend.factory.WebClientFactory;
import com.nagarro.consumedbackend.utils.ConsumeAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private final WebClientFactory webClientFactory;

    @Autowired
    public WebClientConfig(WebClientFactory webClientFactory) {
        this.webClientFactory = webClientFactory;
    }

    @Bean(name = "fetchUser")
    public WebClient fetchUser() {
        return webClientFactory
                .createWebClient(
                        2000,
                        2000,
                        2000
                );
    }

    @Bean(name = "fetchNationality")
    public WebClient fetchNationality() {
        return webClientFactory
                .createWebClient(
                        1000,
                        1000,
                        1000
                );
    }

    @Bean(name = "fetchGender")
    public WebClient fetchGender() {
        return webClientFactory
                .createWebClient(
                        1000,
                        1000,
                        1000
                );
    }

}
