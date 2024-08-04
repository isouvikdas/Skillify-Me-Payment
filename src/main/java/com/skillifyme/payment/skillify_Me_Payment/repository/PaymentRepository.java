package com.skillifyme.payment.skillify_Me_Payment.repository;

import com.skillifyme.payment.skillify_Me_Payment.model.entity.PaymentRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentRecord, ObjectId> {
    PaymentRecord findByPaymentId(String paymentId);
}
