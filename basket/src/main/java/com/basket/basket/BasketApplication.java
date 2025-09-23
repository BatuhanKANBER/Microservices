package com.basket.basket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching
public class BasketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasketApplication.class, args);
		System.out.println("******************************");
		System.out.println("BASKET SERVICE UPPPPPPP");
		System.out.println("******************************");
	}

}
