package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralStockAssignmentOrderProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/stock/assignment-orders")
@CrossOrigin(origins = "*")
public class StockAssignmentOrderProxyFacade {

    private final CentralStockAssignmentOrderProxyService centralStockAssignmentOrderProxyService;

    public StockAssignmentOrderProxyFacade(
            CentralStockAssignmentOrderProxyService centralStockAssignmentOrderProxyService) {
        this.centralStockAssignmentOrderProxyService = centralStockAssignmentOrderProxyService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getByCode(@PathVariable String code) {
        log.debug("Facade call: proxy GET /stock/assignment-orders/{} -> central", code);
        try {
            return ResponseEntity.ok(centralStockAssignmentOrderProxyService.fetchByCode(code));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_STOCK_ASSIGNMENT_ORDERS_UNAVAILABLE");
        }
    }

    @PostMapping("/fulfill")
    public ResponseEntity<?> fulfill(@RequestBody Map<String, Object> body) {
        log.debug("Facade call: proxy POST /stock/assignment-orders/fulfill -> central");
        try {
            return ResponseEntity.ok(centralStockAssignmentOrderProxyService.fulfill(body));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_STOCK_ASSIGNMENT_ORDERS_UNAVAILABLE");
        }
    }
}
