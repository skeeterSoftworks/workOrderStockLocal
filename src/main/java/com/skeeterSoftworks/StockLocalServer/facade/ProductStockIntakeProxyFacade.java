package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralProductStockIntakesProxyService;
import com.skeeterSoftworks.StockLocalServer.to.objects.ProductStockIntakeTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RestController
@RequestMapping("/stock/product-intakes")
@CrossOrigin(origins = "*")
public class ProductStockIntakeProxyFacade {

    private final CentralProductStockIntakesProxyService centralProductStockIntakesProxyService;

    public ProductStockIntakeProxyFacade(
            CentralProductStockIntakesProxyService centralProductStockIntakesProxyService) {
        this.centralProductStockIntakesProxyService = centralProductStockIntakesProxyService;
    }

    @GetMapping("/recent")
    public ResponseEntity<?> listRecent(@RequestParam(defaultValue = "50") int limit) {
        log.debug("Facade call: proxy GET /stock/product-intakes/recent -> central");
        try {
            return ResponseEntity.ok(centralProductStockIntakesProxyService.fetchRecent(limit));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_PRODUCT_STOCK_INTAKES_UNAVAILABLE");
        }
    }

    @GetMapping("/work-orders")
    public ResponseEntity<?> listWorkOrders(@RequestParam long productId) {
        log.debug("Facade call: proxy GET /stock/product-intakes/work-orders -> central");
        try {
            if (productId <= 0) {
                return ResponseEntity.badRequest().body("PRODUCT_STOCK_INTAKE_PRODUCT_REQUIRED");
            }
            return ResponseEntity.ok(centralProductStockIntakesProxyService.fetchWorkOrders(productId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_PRODUCT_STOCK_INTAKES_UNAVAILABLE");
        }
    }

    @PostMapping("/record")
    public ResponseEntity<?> record(@RequestBody ProductStockIntakeTO body) {
        log.debug("Facade call: proxy POST /stock/product-intakes/record -> central");
        try {
            return ResponseEntity.ok(centralProductStockIntakesProxyService.recordIntake(body));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_PRODUCT_STOCK_INTAKES_UNAVAILABLE");
        }
    }
}
