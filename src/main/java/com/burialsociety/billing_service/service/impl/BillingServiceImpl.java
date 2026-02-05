package com.burialsociety.billing_service.service.impl;

import com.burialsociety.billing_service.dto.*;
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
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + requestDto.getMemberId()));

        if (billingAccountRepository.findByMemberId(requestDto.getMemberId()).isPresent()) {
            throw new RuntimeException("Billing Account already exists for this member");
        }

        BillingAccount account = billingMapper.toEntity(requestDto);
        account.setMember(member);
        account.setAccountStatus("ACTIVE");
        account.setNextBillingDate(LocalDate.now().plusMonths(1)); // Default logic
        
        BillingAccount savedAccount = billingAccountRepository.save(account);
        return billingMapper.toDto(savedAccount);
    }

    @Override
    public BillingAccountResponseDto getBillingAccountByMemberId(Long memberId) {
        BillingAccount account = billingAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found for member id: " + memberId));
        return billingMapper.toDto(account);
    }

    @Override
    @Transactional
    public InvoiceDto generateInvoice(Long billingAccountId) {
        BillingAccount account = billingAccountRepository.findById(billingAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found"));

        Invoice invoice = Invoice.builder()
                .billingAccount(account)
                .invoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8))
                .invoiceDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(account.getGracePeriodDays() != null ? account.getGracePeriodDays() : 7))
                .periodStartDate(LocalDate.now())
                .periodEndDate(LocalDate.now().plusMonths(1)) // Assuming monthly
                .amount(account.getBaseContributionAmount())
                .amountPaid(BigDecimal.ZERO)
                .status("UNPAID")
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);
        
        // Update Account Balance (Debit)
        // Ignoring balance update logic for now or assuming separate ledger?
        // Let's assume balance drops (or debt increases). 
        // If balance is "Credit", it goes down. If balance is "Debt", it goes up is ambiguous.
        // Let's assume currentBalance needs to be paid. So Invoice adds to "Debt".
        // But let's stick to simple: Balance is what they HAVE paid in advance or arrears.
        // Positive = Credit. Negative = Arrears.
        // So Invoice SUBTRACTS from balance.
        account.setCurrentBalance(account.getCurrentBalance().subtract(invoice.getAmount()));
        billingAccountRepository.save(account);

        return billingMapper.toDto(savedInvoice);
    }

    @Override
    @Transactional
    public PaymentDto processPayment(PaymentRequestDto paymentRequest) {
        BillingAccount account = billingAccountRepository.findById(paymentRequest.getBillingAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Billing Account not found"));

        Payment payment = billingMapper.toEntity(paymentRequest);
        payment.setBillingAccount(account);
        payment.setReceiptNumber("RCT-" + UUID.randomUUID().toString().substring(0, 8));
        
        Payment savedPayment = paymentRepository.save(payment);

        // Update Account Balance (Credit)
        account.setCurrentBalance(account.getCurrentBalance().add(payment.getAmount()));
        account.setLastPaymentDate(payment.getPaymentDate());
        
        // Update Status if out of arrears (Simple logic)
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
        return paymentRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "paymentDate")).stream()
                .map(billingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingAccountResponseDto> getArrearsAccounts() {
        return billingAccountRepository.findByCurrentBalanceLessThan(BigDecimal.ZERO).stream()
                .map(billingMapper::toDto)
                .collect(Collectors.toList());
    }

    private final com.burialsociety.billing_service.repository.BankTransactionRepository bankTransactionRepository;

    @Override
    public List<com.burialsociety.billing_service.entity.BankTransaction> getBankTransactions() {
        return bankTransactionRepository.findAll();
    }

    @Override
    @Transactional
    public com.burialsociety.billing_service.entity.BankTransaction matchTransaction(Long id) {
        com.burialsociety.billing_service.entity.BankTransaction trx = bankTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        trx.setStatus("MATCHED");
        return bankTransactionRepository.save(trx);
    }

    @Override
    @Transactional
    public void seedBankTransactions() {
        if (bankTransactionRepository.count() == 0) {
            bankTransactionRepository.saveAll(List.of(
                    com.burialsociety.billing_service.entity.BankTransaction.builder()
                            .bankDate(LocalDate.now().minusDays(1))
                            .description("ECOCASH TRANSFER REF 12345")
                            .amount(BigDecimal.valueOf(15.00))
                            .status("MATCHED")
                            .build(),
                    com.burialsociety.billing_service.entity.BankTransaction.builder()
                            .bankDate(LocalDate.now())
                            .description("CASH DEPOSIT BRANCH A")
                            .amount(BigDecimal.valueOf(30.00))
                            .status("UNMATCHED")
                            .build()
            ));
        }
    }
}
