package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralProductCatalogProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductCatalogProxyFacade {

    private final CentralProductCatalogProxyService centralProductCatalogProxyService;

    public ProductCatalogProxyFacade(CentralProductCatalogProxyService centralProductCatalogProxyService) {
        this.centralProductCatalogProxyService = centralProductCatalogProxyService;
    }

    @GetMapping("/catalog")
    public ResponseEntity<?> getCatalog() {
        log.debug("Facade call: proxy GET /products/catalog -> central");
        try {
            return ResponseEntity.ok(centralProductCatalogProxyService.fetchCatalog());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_PRODUCT_CATALOG_UNAVAILABLE");
        }
    }
}
