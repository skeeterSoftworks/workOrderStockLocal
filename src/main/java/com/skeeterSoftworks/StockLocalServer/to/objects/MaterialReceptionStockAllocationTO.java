package com.skeeterSoftworks.StockLocalServer.to.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialReceptionStockAllocationTO {
    private Long stockLocationId;
    private Integer quantity;
}
