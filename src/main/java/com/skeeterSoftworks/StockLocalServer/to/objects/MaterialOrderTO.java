package com.skeeterSoftworks.StockLocalServer.to.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderTO {
    private Long id;
    private String code;
    private Integer quantity;
    private Long materialId;
    private String materialName;
    private String materialCode;
    private Long materialProviderId;
    private String materialProviderName;
    private String status;
    private LocalDateTime lastChanged;
    private LocalDateTime createdAt;
    private LocalDateTime rejectedAt;
    private Float materialDiameter;
    private Float materialWeight;
    private Float materialLength;
    private Float materialWidth;
}
