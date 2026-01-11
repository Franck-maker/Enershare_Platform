package com.enershare.common.events;

import lombok.*;
import java.util.*; 

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TradeMatchedEvent {
    private UUID tradeId; 
    //seller
    private UUID sellerId;
    //buyer
    private UUID buyerId;
    // Energy Amount
    private Double kwhAmount;
    // unit price
    private Double pricePerKwh;
    // total price
    private Double totalPrice;
    
}
