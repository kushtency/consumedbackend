package com.nagarro.consumedbackend.factory;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Component
public class WebClientFactory {
    public WebClient createWebClient(int  connectionTimeout, int readTimeout, int writeTimeout){
        HttpClient httpClient = HttpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .doOnConnected(conn -> {
                    conn.addHandlerFirst(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
                    conn.addHandlerFirst(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS));
                });
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder()
                .clientConnector(connector)
                .build();
    }
}
