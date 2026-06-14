package com.skeeterSoftworks.StockLocalServer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
public class CentralStockAssignmentOrderProxyService {

    private final WebClient webClient;

    @Value("${central.url}")
    private String centralUrl;

    public CentralStockAssignmentOrderProxyService(@Qualifier("centralWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Map<String, Object> fetchByCode(String code) {
        return webClient.get()
                .uri(centralUrl + "/stock/assignment-orders/{code}", code)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    public Map<String, Object> fulfill(Map<String, Object> body) {
        return webClient.post()
                .uri(centralUrl + "/stock/assignment-orders/fulfill")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
