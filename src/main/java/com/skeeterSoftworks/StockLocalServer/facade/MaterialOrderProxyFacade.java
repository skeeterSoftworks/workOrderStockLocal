package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralMaterialOrdersProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/material-orders")
@CrossOrigin(origins = "*")
public class MaterialOrderProxyFacade {

    private final CentralMaterialOrdersProxyService centralMaterialOrdersProxyService;

    public MaterialOrderProxyFacade(CentralMaterialOrdersProxyService centralMaterialOrdersProxyService) {
        this.centralMaterialOrdersProxyService = centralMaterialOrdersProxyService;
    }

    @GetMapping("/open-for-reception")
    public ResponseEntity<?> getOpenForReception() {
        log.debug("Facade call: proxy GET /material-orders/open-for-reception -> central");
        try {
            return ResponseEntity.ok(centralMaterialOrdersProxyService.fetchOpenForReception());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_MATERIAL_ORDERS_UNAVAILABLE");
        }
    }
}
