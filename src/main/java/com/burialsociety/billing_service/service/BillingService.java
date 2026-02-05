package com.burialsociety.billing_service.service;

import com.burialsociety.billing_service.dto.*;

import java.util.List;

public interface BillingService {
    
    BillingAccountResponseDto createBillingAccount(BillingAccountRequestDto requestDto);
    
    BillingAccountResponseDto getBillingAccountByMemberId(Long memberId);
    
    InvoiceDto generateInvoice(Long billingAccountId); // Manual trigger
    
    PaymentDto processPayment(PaymentRequestDto paymentRequest);
    
    List<InvoiceDto> getMemberInvoices(Long memberId);
    
    List<PaymentDto> getMemberPayments(Long memberId);

    List<PaymentDto> getAllPayments();

    List<BillingAccountResponseDto> getArrearsAccounts();

    List<com.burialsociety.billing_service.entity.BankTransaction> getBankTransactions();
    
    com.burialsociety.billing_service.entity.BankTransaction matchTransaction(Long id);
    
    void seedBankTransactions(); // Helper
}
