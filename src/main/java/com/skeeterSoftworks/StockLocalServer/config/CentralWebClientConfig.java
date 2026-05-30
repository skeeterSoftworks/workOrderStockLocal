package com.skeeterSoftworks.StockLocalServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CentralWebClientConfig {

    @Bean(name = "centralWebClient")
    public WebClient centralWebClient(
            @Value("${central.webclient.max-in-memory-size-bytes:16777216}") int maxInMemoryBytes) {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(maxInMemoryBytes))
                        .build())
                .build();
    }
}
