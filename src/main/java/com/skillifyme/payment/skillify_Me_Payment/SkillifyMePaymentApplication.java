package com.skillifyme.payment.skillify_Me_Payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SkillifyMePaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillifyMePaymentApplication.class, args);
	}

}
