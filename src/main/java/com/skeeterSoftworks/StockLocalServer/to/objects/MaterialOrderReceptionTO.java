package com.skeeterSoftworks.StockLocalServer.to.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderReceptionTO {
    private Long id;
    private Long materialOrderId;
    private Long materialOrderLineId;
    private String materialOrderCode;
    private String materialCode;
    private String materialName;
    private String materialProviderName;
    private LocalDateTime receivedAt;
    private Integer receivedQuantity;
    private MaterialOrderReceptionInternalControlTO internalControl;
    private Float materialDiameter;
    private Float materialWeight;
    private Float materialLength;
    private Float materialWidth;
    private java.util.List<MaterialReceptionStockAllocationTO> stockAllocations;
    private Boolean certificatePresent;
}
