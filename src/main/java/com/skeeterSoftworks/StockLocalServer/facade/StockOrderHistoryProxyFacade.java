package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralStockOrderHistoryProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/stock/order-history")
@CrossOrigin(origins = "*")
public class StockOrderHistoryProxyFacade {

    private final CentralStockOrderHistoryProxyService centralStockOrderHistoryProxyService;

    public StockOrderHistoryProxyFacade(
            CentralStockOrderHistoryProxyService centralStockOrderHistoryProxyService) {
        this.centralStockOrderHistoryProxyService = centralStockOrderHistoryProxyService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "assignedAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean asc,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String assignedFrom,
            @RequestParam(required = false) String assignedTo,
            @RequestParam(required = false) String assignedBy) {
        log.debug("Facade call: proxy GET /stock/order-history/search -> central");
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("page", String.valueOf(page));
            params.put("size", String.valueOf(size));
            params.put("sortBy", sortBy);
            params.put("asc", String.valueOf(asc));
            params.put("productType", productType);
            params.put("assignedFrom", assignedFrom);
            params.put("assignedTo", assignedTo);
            params.put("assignedBy", assignedBy);
            return ResponseEntity.ok(centralStockOrderHistoryProxyService.search(params));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_STOCK_ORDER_HISTORY_UNAVAILABLE");
        }
    }
}
