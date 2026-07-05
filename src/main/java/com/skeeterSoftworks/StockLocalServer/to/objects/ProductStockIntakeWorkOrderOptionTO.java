package com.skeeterSoftworks.StockLocalServer.to.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockIntakeWorkOrderOptionTO {
    private Long id;
    private String productReference;
    private String productName;
    private Integer requiredQuantity;
    private Long producedGoodQuantity;
    private Integer receivedToStockQuantity;
    private Integer receivedOrderQuantity;
    private Boolean internalStockDemand;
    /** INCOMPLETE or COMPLETE. */
    private String state;
}
