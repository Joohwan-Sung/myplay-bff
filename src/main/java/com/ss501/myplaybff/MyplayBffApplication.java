package com.ss501.myplaybff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MyplayBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyplayBffApplication.class, args);
	}

}
