package com.tasks.bnn;

import com.tasks.bnn.config.ActiveDirectoryConfig;
import com.tasks.bnn.config.PowerBiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BnnApplication {

	public static void main(String[] args) {
		SpringApplication.run(BnnApplication.class, args);
	}

//	@Bean
//	public ActiveDirectoryConfig activeDirectoryConfig() {
//		return new ActiveDirectoryConfig();
//	}
//
//	@Bean
//	public PowerBiConfig powerBiConfig() {
//		return new PowerBiConfig();
//	}

}
