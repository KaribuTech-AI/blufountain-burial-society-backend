package com.burialsociety.billing_service.service;

import com.burialsociety.billing_service.dto.*;
import com.burialsociety.billing_service.entity.BillingAccount;
import com.burialsociety.billing_service.entity.Invoice;
import com.burialsociety.billing_service.entity.Payment;
import com.burialsociety.billing_service.mapper.BillingMapper;
import com.burialsociety.billing_service.repository.BillingAccountRepository;
import com.burialsociety.billing_service.repository.InvoiceRepository;
import com.burialsociety.billing_service.repository.PaymentRepository;
import com.burialsociety.billing_service.service.impl.BillingServiceImpl;
import com.burialsociety.member_service.entity.Member;
import com.burialsociety.member_service.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private BillingAccountRepository billingAccountRepository;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BillingMapper billingMapper;

    @InjectMocks
    private BillingServiceImpl billingService;

    private BillingAccount billingAccount;
    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);

        billingAccount = new BillingAccount();
        billingAccount.setId(10L);
        billingAccount.setMember(member);
        billingAccount.setCurrentBalance(BigDecimal.ZERO);
        billingAccount.setBaseContributionAmount(new BigDecimal("15.00"));
        billingAccount.setGracePeriodDays(7);
    }

    @Test
    void createBillingAccount_Success() {
        BillingAccountRequestDto request = new BillingAccountRequestDto();
        request.setMemberId(1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(billingAccountRepository.findByMemberId(1L)).thenReturn(Optional.empty());
        when(billingMapper.toEntity(request)).thenReturn(billingAccount);
        when(billingAccountRepository.save(any(BillingAccount.class))).thenReturn(billingAccount);
        when(billingMapper.toDto(any(BillingAccount.class))).thenReturn(new BillingAccountResponseDto());

        BillingAccountResponseDto response = billingService.createBillingAccount(request);
        
        assertNotNull(response);
        verify(billingAccountRepository).save(any(BillingAccount.class));
    }

    @Test
    void generateInvoice_ShouldUnpaidInvoiceAndDeductBalance() {
        when(billingAccountRepository.findById(10L)).thenReturn(Optional.of(billingAccount));
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(i -> i.getArguments()[0]);
        when(billingMapper.toDto(any(Invoice.class))).thenReturn(new InvoiceDto());

        billingService.generateInvoice(10L);

        // Balance was 0. Invoice 15. New Balance should be -15.
        // Logic in service: account.setCurrentBalance(account.getCurrentBalance().subtract(invoice.getAmount()));
        assertEquals(new BigDecimal("-15.00"), billingAccount.getCurrentBalance());
        verify(invoiceRepository).save(any(Invoice.class));
        verify(billingAccountRepository).save(billingAccount);
    }

    @Test
    void processPayment_ShouldAddBalance() {
        PaymentRequestDto request = new PaymentRequestDto();
        request.setBillingAccountId(10L);
        request.setAmount(new BigDecimal("15.00"));
        request.setPaymentDate(LocalDate.now());

        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("15.00"));
        payment.setPaymentDate(LocalDate.now());

        when(billingAccountRepository.findById(10L)).thenReturn(Optional.of(billingAccount));
        when(billingMapper.toEntity(request)).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(billingMapper.toDto(any(Payment.class))).thenReturn(new PaymentDto());

        // Set initial balance to -15 (Arrears)
        billingAccount.setCurrentBalance(new BigDecimal("-15.00"));

        billingService.processPayment(request);

        // Should return to 0
        assertEquals(new BigDecimal("0.00"), billingAccount.getCurrentBalance());
        verify(paymentRepository).save(any(Payment.class));
        verify(billingAccountRepository).save(billingAccount);
    }
}
