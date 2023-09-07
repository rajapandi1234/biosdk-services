package io.mosip.biosdk.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "io.mosip.biosdk.services.*" })
@SpringBootApplication
public class SdkApplication {
	public static void main(String[] args) {
		SpringApplication.run(SdkApplication.class, args);
	}
}