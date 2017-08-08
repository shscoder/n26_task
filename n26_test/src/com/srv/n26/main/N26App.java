package com.srv.n26.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.srv.n26"})
public class N26App {

	public static void main(String[] args) {
		SpringApplication.run(N26App.class, args);
	}

}
