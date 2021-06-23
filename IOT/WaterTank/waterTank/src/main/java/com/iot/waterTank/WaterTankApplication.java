package com.iot.waterTank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WaterTankApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterTankApplication.class, args);
	}

}
