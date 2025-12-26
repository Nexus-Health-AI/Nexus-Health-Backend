package com.uciap.medicrea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.uciap.medicrea", "com.smartcare.hms"})
public class MedicreaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicreaApplication.class, args);
	}

}
