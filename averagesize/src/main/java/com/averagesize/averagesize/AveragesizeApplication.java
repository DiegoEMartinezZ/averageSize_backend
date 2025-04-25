package com.averagesize.averagesize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AveragesizeApplication {
	private static final Logger logger = LoggerFactory.getLogger(AveragesizeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AveragesizeApplication.class, args);
		logger.info("Average Size application started successfully");
	}
}
