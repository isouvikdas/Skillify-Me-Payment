package com.skillifyme.payment.skillify_Me_Payment.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.skillifyme.payment.skillify_Me_Payment.model.dto.PaymentRequest;
import com.skillifyme.payment.skillify_Me_Payment.model.dto.PaymentResponse;
import com.skillifyme.payment.skillify_Me_Payment.model.entity.PaymentRecord;
import com.skillifyme.payment.skillify_Me_Payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentResponse createPayment(PaymentRequest paymentRequest) throws PayPalRESTException {
        Payment payment = getPayment(paymentRequest);

        Payment createdPayment = payment.create(apiContext);

        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setPaymentId(createdPayment.getId());
//        paymentRecord.setPayerId(payer.getPayerInfo().getPayerId());
        paymentRecord.setPaymentStatus(createdPayment.getState());
        paymentRecord.setEmail(paymentRequest.getUserEmail());
        paymentRecord.setTotal(paymentRequest.getAmount().doubleValue());
        paymentRecord.setCurrency(paymentRequest.getCurrency());
        paymentRecord.setDescription(paymentRequest.getDescription());
        paymentRecord.setCreatedDate(LocalDateTime.now());

        paymentRepository.save(paymentRecord);

        PaymentResponse response = new PaymentResponse();
        response.setSuccess(true);
        response.setPaymentId(createdPayment.getId());
//        response.setPayerId(payer.getPayerInfo().getPayerId());
        response.setMessage("PaymentRecord created successfully");
        String approvalUrl = createdPayment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .findFirst()
                .map(Links::getHref)
                .orElse("");
        response.setApprovalUrl(approvalUrl);

        return response;
    }

    private static Payment getPayment(PaymentRequest paymentRequest) {
        Amount amount = new Amount();
        amount.setCurrency(paymentRequest.getCurrency());
        amount.setTotal(String.format("%.2f", paymentRequest.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setDescription(paymentRequest.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(paymentRequest.getMethod());

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paymentRequest.getCancelUrl());
        redirectUrls.setReturnUrl(paymentRequest.getSuccessUrl());

        Payment payment = new Payment();
        payment.setIntent(paymentRequest.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        Payment executedPayment = payment.execute(apiContext, paymentExecution);

        PaymentRecord paymentRecord = paymentRepository.findByPaymentId(paymentId);
        paymentRecord.setPaymentStatus(executedPayment.getState());
        paymentRepository.save(paymentRecord);

        return executedPayment;
    }
}
