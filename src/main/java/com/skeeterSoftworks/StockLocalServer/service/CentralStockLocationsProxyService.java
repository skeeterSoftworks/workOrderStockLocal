package com.skeeterSoftworks.StockLocalServer.service;

import com.skeeterSoftworks.StockLocalServer.to.objects.StockLocationTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CentralStockLocationsProxyService {

    private final WebClient webClient;

    @Value("${central.url}")
    private String centralUrl;

    public CentralStockLocationsProxyService(@Qualifier("centralWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<StockLocationTO> fetchAll() {
        try {
            List<StockLocationTO> list = webClient.get()
                    .uri(centralUrl + "/stock-locations/all")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<StockLocationTO>>() {})
                    .block();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            log.error("Central stock locations fetch failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}
