package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralStockProductsProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "*")
public class StockProductsProxyFacade {

    private final CentralStockProductsProxyService centralStockProductsProxyService;

    public StockProductsProxyFacade(CentralStockProductsProxyService centralStockProductsProxyService) {
        this.centralStockProductsProxyService = centralStockProductsProxyService;
    }

    @GetMapping("/products-availability")
    public ResponseEntity<?> getProductsAvailability() {
        log.debug("Facade call: proxy GET /stock/products-availability -> central");
        try {
            return ResponseEntity.ok(centralStockProductsProxyService.fetchProductsAvailability());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_PRODUCT_STOCK_UNAVAILABLE");
        }
    }
}
