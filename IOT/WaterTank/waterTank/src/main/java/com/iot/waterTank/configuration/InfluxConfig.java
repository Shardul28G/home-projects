package com.iot.waterTank.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;

@Configuration
public class InfluxConfig {

	@Bean
	public InfluxDBClient connectToInfluxdb() {
		String token = "o2tK5RJQd0lv-5skw-ywG1wwbQn2_z56hFIC7cVyhkxRb0YW5-IKRluTIVtRuoifCVp_u1iFS8zF3IC1rodyWQ==";
		String bucket = "iotDataBucket";
		String org = "IOT";

		InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
		
		
		
		return client;
	}

}
