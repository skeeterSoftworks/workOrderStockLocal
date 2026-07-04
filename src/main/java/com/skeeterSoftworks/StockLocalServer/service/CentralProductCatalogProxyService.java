package com.skeeterSoftworks.StockLocalServer.service;

import com.skeeterSoftworks.StockLocalServer.to.objects.ProductCatalogEntryTO;
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
public class CentralProductCatalogProxyService {

    private final WebClient webClient;

    @Value("${central.url}")
    private String centralUrl;

    public CentralProductCatalogProxyService(@Qualifier("centralWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ProductCatalogEntryTO> fetchCatalog() {
        try {
            List<ProductCatalogEntryTO> list = webClient.get()
                    .uri(centralUrl + "/products/catalog")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ProductCatalogEntryTO>>() {})
                    .block();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            log.error("Central product catalog fetch failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}
