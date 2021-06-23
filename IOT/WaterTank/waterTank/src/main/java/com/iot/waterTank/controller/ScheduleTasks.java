package com.iot.waterTank.controller;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTasks {
	
	@Autowired
	public IMqttClient mc;
	
	
	public void publishmqtt() throws MqttPersistenceException, MqttException
	{
		int temp = (int) (Math.random() * 10 + 10);
		System.out.println("Sending temperature measurement (" + temp + ") ...");
		mc.publish("testpub", new MqttMessage(("211," + temp).getBytes()));
	}

}
