package com.skeeterSoftworks.StockLocalServer.to.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockIntakeTO {
    private Long id;
    private Long productId;
    private String productReference;
    private String productName;
    private String stickerNumber;
    /** PIECES, GRAM, or KILOGRAM. */
    private String unitOfMeasure;
    private Integer quantity;
    /** ISO-8601 date-time. */
    private String receivedAt;
}
