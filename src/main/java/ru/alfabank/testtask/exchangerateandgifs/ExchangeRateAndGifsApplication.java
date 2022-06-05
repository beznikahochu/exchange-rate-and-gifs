package ru.alfabank.testtask.exchangerateandgifs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangeRateAndGifsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateAndGifsApplication.class, args);
	}

}
