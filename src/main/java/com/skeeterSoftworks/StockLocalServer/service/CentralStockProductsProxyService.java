package com.skeeterSoftworks.StockLocalServer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CentralStockProductsProxyService {

    private final WebClient webClient;

    @Value("${central.url}")
    private String centralUrl;

    public CentralStockProductsProxyService(@Qualifier("centralWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Map<String, Object>> fetchProductsAvailability() {
        try {
            List<Map<String, Object>> list = webClient.get()
                    .uri(centralUrl + "/stock/products-availability")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                    .block();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            log.error("Central product stock fetch failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}
