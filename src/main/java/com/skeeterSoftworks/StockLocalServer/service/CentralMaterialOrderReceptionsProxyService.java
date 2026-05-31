package com.skeeterSoftworks.StockLocalServer.service;

import com.skeeterSoftworks.StockLocalServer.to.objects.MaterialOrderReceptionTO;
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
public class CentralMaterialOrderReceptionsProxyService {

    private final WebClient webClient;

    @Value("${central.url}")
    private String centralUrl;

    public CentralMaterialOrderReceptionsProxyService(@Qualifier("centralWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<MaterialOrderReceptionTO> fetchAll() {
        try {
            List<MaterialOrderReceptionTO> list = webClient.get()
                    .uri(centralUrl + "/material-order-receptions/all")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<MaterialOrderReceptionTO>>() {})
                    .block();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            log.error("Central material order receptions fetch failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    public MaterialOrderReceptionTO recordReception(MaterialOrderReceptionTO body) {
        return webClient.post()
                .uri(centralUrl + "/material-order-receptions/record")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MaterialOrderReceptionTO.class)
                .block();
    }
}
