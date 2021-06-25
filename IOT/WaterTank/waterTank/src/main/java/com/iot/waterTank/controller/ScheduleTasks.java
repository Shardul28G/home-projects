package com.iot.waterTank.controller;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;

@Component
public class ScheduleTasks {
	
	@Autowired
	public IMqttClient mc;
	
	@Autowired
	public InfluxDBClient influxClient;
	
	//@Scheduled(fixedDelay = 5000)
	public void publishmqtt() throws MqttPersistenceException, MqttException
	{
		String bucket = "iotDataBucket";
		String org = "IOT";
		Integer temp = (int) (Math.random() * 10 + 10);
		System.out.println("Sending temperature measurement (" + temp + ") ...");
		String data = "mem,host=host1 used_percent=" + temp.toString();
		try (WriteApi writeApi = influxClient.getWriteApi()) {
		  writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
		}
	}

}
