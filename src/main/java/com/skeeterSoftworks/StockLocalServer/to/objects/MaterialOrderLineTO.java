package com.skeeterSoftworks.StockLocalServer.to.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderLineTO {
    private Long id;
    private Long materialId;
    private String materialName;
    private String materialCode;
    private Integer quantity;
    private Boolean received;
    private Float materialDiameter;
    private Float materialWeight;
    private Float materialLength;
    private Float materialWidth;
}
