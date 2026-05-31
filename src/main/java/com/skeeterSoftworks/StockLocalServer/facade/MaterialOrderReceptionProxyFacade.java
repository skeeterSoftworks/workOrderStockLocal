package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralMaterialOrderReceptionsProxyService;
import com.skeeterSoftworks.StockLocalServer.to.objects.MaterialOrderReceptionTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RestController
@RequestMapping("/material-order-receptions")
@CrossOrigin(origins = "*")
public class MaterialOrderReceptionProxyFacade {

    private final CentralMaterialOrderReceptionsProxyService centralMaterialOrderReceptionsProxyService;

    public MaterialOrderReceptionProxyFacade(
            CentralMaterialOrderReceptionsProxyService centralMaterialOrderReceptionsProxyService) {
        this.centralMaterialOrderReceptionsProxyService = centralMaterialOrderReceptionsProxyService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        log.debug("Facade call: proxy GET /material-order-receptions/all -> central");
        try {
            return ResponseEntity.ok(centralMaterialOrderReceptionsProxyService.fetchAll());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_MATERIAL_ORDER_RECEPTIONS_UNAVAILABLE");
        }
    }

    @PostMapping("/record")
    public ResponseEntity<?> record(@RequestBody MaterialOrderReceptionTO body) {
        log.debug("Facade call: proxy POST /material-order-receptions/record -> central");
        try {
            return ResponseEntity.ok(centralMaterialOrderReceptionsProxyService.recordReception(body));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_MATERIAL_ORDER_RECEPTIONS_UNAVAILABLE");
        }
    }
}
