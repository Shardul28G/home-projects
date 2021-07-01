package com.iot.waterTank.configuration;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MQTTConfig {
	
	
	@Value("${mqtt.host}")
	private String mqtthost;
	
	@Value("${mqtt.username}")
	private String username;
	
	@Value("${mqtt.password}")
	private String password;
	
	
	@Bean
	public IMqttClient connectToMqttServer() throws MqttException
	{
		String publisherId = UUID.randomUUID().toString();
		IMqttClient publisher = new MqttClient(mqtthost,publisherId);
		
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);
//		MqttCallback clientCallback = new MqttCallback() {
//
//             @Override
//             public void connectionLost(Throwable cause) {
//
//             }
//
//             @Override
//             public void messageArrived(String topic, MqttMessage message) throws Exception {
//            	 System.out.println(message);
//             }
//
//             @Override
//             public void deliveryComplete(IMqttDeliveryToken token) {
//
//             }
//         };
//
//        
//		publisher.setCallback(clientCallback);
		publisher.connect(options);
		
		
		
		return publisher;
	}
	
	

}
