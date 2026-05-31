package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralStockLocationsProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stock-locations")
@CrossOrigin(origins = "*")
public class StockLocationProxyFacade {

    private final CentralStockLocationsProxyService centralStockLocationsProxyService;

    public StockLocationProxyFacade(CentralStockLocationsProxyService centralStockLocationsProxyService) {
        this.centralStockLocationsProxyService = centralStockLocationsProxyService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        log.debug("Facade call: proxy GET /stock-locations/all -> central");
        try {
            return ResponseEntity.ok(centralStockLocationsProxyService.fetchAll());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_STOCK_LOCATIONS_UNAVAILABLE");
        }
    }
}
