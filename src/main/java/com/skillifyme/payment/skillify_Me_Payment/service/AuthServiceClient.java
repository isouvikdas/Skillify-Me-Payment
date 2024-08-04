package com.skillifyme.payment.skillify_Me_Payment.service;

import com.skillifyme.payment.skillify_Me_Payment.model.dto.ValidateTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "skillifymeauth")
public interface AuthServiceClient {

    @PostMapping("/auth/validate/instructor")
    ResponseEntity<ValidateTokenResponse> validateInstructor(@RequestBody Map<String, String> payload);

    @PostMapping("/auth/validate/user")
    ResponseEntity<ValidateTokenResponse> validateUser(@RequestBody Map<String, String> payload);
}
