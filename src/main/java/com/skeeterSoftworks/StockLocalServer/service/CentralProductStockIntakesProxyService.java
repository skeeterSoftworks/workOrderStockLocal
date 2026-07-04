package com.skeeterSoftworks.StockLocalServer.service;

import com.skeeterSoftworks.StockLocalServer.to.objects.ProductStockIntakeTO;
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
public class CentralProductStockIntakesProxyService {

    private final WebClient webClient;

    @Value("${central.url}")
    private String centralUrl;

    public CentralProductStockIntakesProxyService(@Qualifier("centralWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ProductStockIntakeTO> fetchRecent(int limit) {
        try {
            List<ProductStockIntakeTO> list = webClient.get()
                    .uri(centralUrl + "/stock/product-intakes/recent?limit=" + limit)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ProductStockIntakeTO>>() {})
                    .block();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            log.error("Central product stock intakes fetch failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ProductStockIntakeTO recordIntake(ProductStockIntakeTO body) {
        return webClient.post()
                .uri(centralUrl + "/stock/product-intakes/record")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body != null ? body : new ProductStockIntakeTO())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductStockIntakeTO.class)
                .block();
    }
}
