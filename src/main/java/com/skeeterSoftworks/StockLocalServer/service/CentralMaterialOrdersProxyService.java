package com.skeeterSoftworks.StockLocalServer.service;

import com.skeeterSoftworks.StockLocalServer.to.objects.MaterialOrderTO;
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
public class CentralMaterialOrdersProxyService {

    private final WebClient webClient;

    @Value("${central.url}")
    private String centralUrl;

    public CentralMaterialOrdersProxyService(@Qualifier("centralWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<MaterialOrderTO> fetchOpenForReception() {
        try {
            List<MaterialOrderTO> list = webClient.get()
                    .uri(centralUrl + "/material-orders/open-for-reception")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<MaterialOrderTO>>() {})
                    .block();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            log.error("Central material orders open-for-reception fetch failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}
