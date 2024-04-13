package com.elpidoroun.financialportfolio.mappers;

import com.elpidoroun.financialportfolio.generated.dto.BillingIntervalDto;
import com.elpidoroun.financialportfolio.model.BillingInterval;

public class BillingIntervalMapper {

    public static BillingIntervalDto toDto(BillingInterval billingInterval){
        return switch (billingInterval) {
            case MONTHLY -> BillingIntervalDto.MONTHLY;
            case BI_MONTHLY -> BillingIntervalDto.BI_MONTHLY;
            case QUARTERLY -> BillingIntervalDto.QUARTERLY;
            case YEARLY -> BillingIntervalDto.YEARLY;
        };
    }

    public static BillingInterval toDomain(BillingIntervalDto billingIntervalDto){
        return switch (billingIntervalDto) {
            case MONTHLY -> BillingInterval.MONTHLY;
            case BI_MONTHLY -> BillingInterval.BI_MONTHLY;
            case QUARTERLY -> BillingInterval.QUARTERLY;
            case YEARLY -> BillingInterval.YEARLY;
        };
    }
}