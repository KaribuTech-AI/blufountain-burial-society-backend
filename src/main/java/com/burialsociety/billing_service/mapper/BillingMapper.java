package com.burialsociety.billing_service.mapper;

import com.burialsociety.billing_service.dto.*;
import com.burialsociety.billing_service.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true) // Set manually
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "payments", ignore = true)
    BillingAccount toEntity(BillingAccountRequestDto dto);

    BillingAccountResponseDto toDto(BillingAccount entity);

    InvoiceDto toDto(Invoice entity);

    PaymentDto toDto(Payment entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "billingAccount", ignore = true)
    Payment toEntity(PaymentRequestDto dto);
}
