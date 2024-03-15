package com.elpidoroun.financialportfolio.converters;

import com.elpidoroun.financialportfolio.exceptions.IllegalArgumentException;
import com.elpidoroun.financialportfolio.model.PaymentType;
import com.elpidoroun.financialportfolio.generated.dto.PaymentTypeDto;

public class PaymentTypeConverter {

    public static PaymentTypeDto toDto(PaymentType paymentType){
        return switch (paymentType) {
            case YEARLY -> PaymentTypeDto.YEARLY;
            case MONTHLY -> PaymentTypeDto.MONTHLY;
            default -> throw new IllegalArgumentException("PaymentType: " + paymentType + " not supported.");
        };
    }

    public static PaymentType toDomain(PaymentTypeDto paymentTypeDto){
        return switch (paymentTypeDto) {
            case MONTHLY -> PaymentType.MONTHLY;
            case YEARLY -> PaymentType.YEARLY;
            default -> throw new IllegalArgumentException("PaymentTypeDto " + paymentTypeDto + " not supported");
        };
    }
}
