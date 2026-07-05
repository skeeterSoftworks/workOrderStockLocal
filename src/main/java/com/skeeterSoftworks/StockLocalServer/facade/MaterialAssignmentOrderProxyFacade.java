package com.skeeterSoftworks.StockLocalServer.facade;

import com.skeeterSoftworks.StockLocalServer.service.CentralMaterialAssignmentOrderProxyService;
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
@RequestMapping("/stock/material-assignment-orders")
@CrossOrigin(origins = "*")
public class MaterialAssignmentOrderProxyFacade {

    private final CentralMaterialAssignmentOrderProxyService centralMaterialAssignmentOrderProxyService;

    public MaterialAssignmentOrderProxyFacade(
            CentralMaterialAssignmentOrderProxyService centralMaterialAssignmentOrderProxyService) {
        this.centralMaterialAssignmentOrderProxyService = centralMaterialAssignmentOrderProxyService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getByCode(@PathVariable String code) {
        log.debug("Facade call: proxy GET /stock/material-assignment-orders/{} -> central", code);
        try {
            return ResponseEntity.ok(centralMaterialAssignmentOrderProxyService.fetchByCode(code));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_MATERIAL_ASSIGNMENT_ORDERS_UNAVAILABLE");
        }
    }

    @PostMapping("/fulfill")
    public ResponseEntity<?> fulfill(@RequestBody Map<String, Object> body) {
        log.debug("Facade call: proxy POST /stock/material-assignment-orders/fulfill -> central");
        try {
            return ResponseEntity.ok(centralMaterialAssignmentOrderProxyService.fulfill(body));
        } catch (WebClientResponseException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(502).body("CENTRAL_MATERIAL_ASSIGNMENT_ORDERS_UNAVAILABLE");
        }
    }
}
