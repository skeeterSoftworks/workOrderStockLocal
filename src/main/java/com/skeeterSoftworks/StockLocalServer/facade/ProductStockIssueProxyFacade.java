package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralProductStockIssueProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/stock/product-issues")
@CrossOrigin(origins = "*")
public class ProductStockIssueProxyFacade {

    private final CentralProductStockIssueProxyService centralProductStockIssueProxyService;

    public ProductStockIssueProxyFacade(CentralProductStockIssueProxyService centralProductStockIssueProxyService) {
        this.centralProductStockIssueProxyService = centralProductStockIssueProxyService;
    }

    @GetMapping("/work-orders")
    public ResponseEntity<?> listEligibleWorkOrders() {
        log.debug("Facade call: proxy GET /stock/product-issues/work-orders -> central");
        try {
            return ResponseEntity.ok(centralProductStockIssueProxyService.listEligibleWorkOrders());
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_PRODUCT_STOCK_ISSUES_UNAVAILABLE");
        }
    }

    @PostMapping("/issue")
    public ResponseEntity<?> issue(@RequestBody Map<String, Object> body) {
        log.debug("Facade call: proxy POST /stock/product-issues/issue -> central");
        try {
            return ResponseEntity.ok(centralProductStockIssueProxyService.issue(body));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_PRODUCT_STOCK_ISSUES_UNAVAILABLE");
        }
    }
}
