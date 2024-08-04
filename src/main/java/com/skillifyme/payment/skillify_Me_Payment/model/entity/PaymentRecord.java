package com.skillifyme.payment.skillify_Me_Payment.model.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "payments")
@Data
public class PaymentRecord {

    private ObjectId id;
    private String paymentId;
    private String payerId;
    private String paymentStatus;
    private String email;
    private double total;
    private String currency;
    private String description;
    private LocalDateTime createdDate;
}