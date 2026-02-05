package com.burialsociety.billing_service.mapper;

import com.burialsociety.billing_service.dto.*;
import com.burialsociety.billing_service.entity.BillingAccount;
import com.burialsociety.billing_service.entity.Invoice;
import com.burialsociety.billing_service.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "payments", ignore = true)
    BillingAccount toEntity(BillingAccountRequestDto dto);


    @Mapping(source = "member.id", target = "memberId")
    BillingAccountResponseDto toDto(BillingAccount entity);

    InvoiceDto toDto(Invoice entity);

    PaymentDto toDto(Payment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "billingAccount", ignore = true)
    Payment toEntity(PaymentRequestDto dto);
}