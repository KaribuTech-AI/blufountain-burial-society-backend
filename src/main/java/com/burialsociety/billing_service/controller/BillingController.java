package com.burialsociety.billing_service.controller;

import com.burialsociety.billing_service.dto.*;
import com.burialsociety.billing_service.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow all for dev
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/accounts")
    public ResponseEntity<BillingAccountResponseDto> createBillingAccount(@RequestBody BillingAccountRequestDto request) {
        return ResponseEntity.ok(billingService.createBillingAccount(request));
    }

    @GetMapping("/accounts/member/{memberId}")
    public ResponseEntity<BillingAccountResponseDto> getBillingAccount(@PathVariable Long memberId) {
        return ResponseEntity.ok(billingService.getBillingAccountByMemberId(memberId));
    }

    @PostMapping("/invoices/generate")
    public ResponseEntity<InvoiceDto> generateInvoice(@RequestParam Long billingAccountId) {
        return ResponseEntity.ok(billingService.generateInvoice(billingAccountId));
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentRequestDto request) {
        return ResponseEntity.ok(billingService.processPayment(request));
    }

    @GetMapping("/accounts/member/{memberId}/invoices")
    public ResponseEntity<List<InvoiceDto>> getMemberInvoices(@PathVariable Long memberId) {
        return ResponseEntity.ok(billingService.getMemberInvoices(memberId));
    }

    @GetMapping("/accounts/member/{memberId}/payments")
    public ResponseEntity<List<PaymentDto>> getMemberPayments(@PathVariable Long memberId) {
        return ResponseEntity.ok(billingService.getMemberPayments(memberId));
    }
}
