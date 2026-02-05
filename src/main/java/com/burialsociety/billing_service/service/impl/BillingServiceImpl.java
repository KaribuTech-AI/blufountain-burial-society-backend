package com.burialsociety.billing_service.service.impl;

import com.burialsociety.billing_service.dto.*;
import com.burialsociety.billing_service.entity.BankTransaction;
import com.burialsociety.billing_service.entity.BillingAccount;
import com.burialsociety.billing_service.entity.Invoice;
import com.burialsociety.billing_service.entity.Payment;
import com.burialsociety.billing_service.mapper.BillingMapper;
import com.burialsociety.billing_service.repository.BillingAccountRepository;
import com.burialsociety.billing_service.repository.InvoiceRepository;
import com.burialsociety.billing_service.repository.PaymentRepository;
import com.burialsociety.billing_service.service.BillingService;
import com.burialsociety.member_service.entity.Member;
import com.burialsociety.member_service.exception.ResourceNotFoundException;
import com.burialsociety.member_service.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;




@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingAccountRepository billingAccountRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final BillingMapper billingMapper;

    @Override
    @Transactional
    public BillingAccountResponseDto createBillingAccount(BillingAccountRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        BillingAccount account = billingMapper.toEntity(requestDto);
        account.setMember(member);

        // Only set defaults if they are NULL in the request
        if (account.getAccountStatus() == null) {
            account.setAccountStatus("ACTIVE");
        }

        if (account.getNextBillingDate() == null) {
            account.setNextBillingDate(LocalDate.now().plusMonths(1));
        }

        BillingAccount savedAccount = billingAccountRepository.save(account);
        return billingMapper.toDto(savedAccount);
    }
    @Override
    public BillingAccountResponseDto getBillingAccountByMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("Member ID must not be null");
        }

        BillingAccount account = billingAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found for member id: " + memberId));
        return billingMapper.toDto(account);
    }

    @Override
    @Transactional
    public InvoiceDto generateInvoice(Long billingAccountId) {
        if (billingAccountId == null) {
            throw new IllegalArgumentException("Billing Account ID must not be null");
        }

        BillingAccount account = billingAccountRepository.findById(billingAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found"));

        Invoice invoice = Invoice.builder()
                .billingAccount(account)
                .invoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8))
                .invoiceDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(account.getGracePeriodDays() != null ? account.getGracePeriodDays() : 7))
                .periodStartDate(LocalDate.now())
                .periodEndDate(LocalDate.now().plusMonths(1))
                .amount(account.getBaseContributionAmount())
                .amountPaid(BigDecimal.ZERO)
                .status("UNPAID")
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Logic: Invoice amount is owed, so we subtract from the balance
        BigDecimal currentBal = account.getCurrentBalance() != null ? account.getCurrentBalance() : BigDecimal.ZERO;
        account.setCurrentBalance(currentBal.subtract(invoice.getAmount()));

        billingAccountRepository.save(account);

        return billingMapper.toDto(savedInvoice);
    }

    @Override
    @Transactional
    public PaymentDto processPayment(PaymentRequestDto paymentRequest) {
        if (paymentRequest == null || paymentRequest.getBillingAccountId() == null) {
            throw new IllegalArgumentException("Payment details and Account ID are required");
        }

        BillingAccount account = billingAccountRepository.findById(paymentRequest.getBillingAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found"));

        Payment payment = billingMapper.toEntity(paymentRequest);
        payment.setBillingAccount(account);
        payment.setReceiptNumber("RCT-" + UUID.randomUUID().toString().substring(0, 8));

        Payment savedPayment = paymentRepository.save(payment);

        // Update Account Balance (Credit)
        BigDecimal currentBal = account.getCurrentBalance() != null ? account.getCurrentBalance() : BigDecimal.ZERO;
        account.setCurrentBalance(currentBal.add(payment.getAmount()));
        account.setLastPaymentDate(payment.getPaymentDate());

        // Status update logic: If balance is 0 or positive, they are no longer in arrears
        if (account.getCurrentBalance().compareTo(BigDecimal.ZERO) >= 0) {
            account.setAccountStatus("ACTIVE");
        }

        billingAccountRepository.save(account);

        return billingMapper.toDto(savedPayment);
    }

    @Override
    public List<InvoiceDto> getMemberInvoices(Long memberId) {
        BillingAccount account = billingAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found"));

        return invoiceRepository.findByBillingAccountId(account.getId()).stream()
                .map(billingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getMemberPayments(Long memberId) {
        BillingAccount account = billingAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found"));

        return paymentRepository.findByBillingAccountId(account.getId()).stream()
                .map(billingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return List.of();
    }

    @Override
    public List<BillingAccountResponseDto> getArrearsAccounts() {
        return List.of();
    }

    @Override
    public List<BankTransaction> getBankTransactions() {
        return List.of();
    }

    @Override
    public BankTransaction matchTransaction(Long id) {
        return null;
    }

    @Override
    public void seedBankTransactions() {

    }
}