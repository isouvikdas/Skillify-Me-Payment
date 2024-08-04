package com.skillifyme.payment.skillify_Me_Payment.model.dto;

import lombok.Data;

@Data
public class ValidateTokenResponse {
    private boolean valid;
    private String email;
    private String message;
}
