package com.iot.waterTank.configuration;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;


@Controller
public class MQTTSubscribeConfig {
	
	@Autowired
	public IMqttClient mqtt;
	
	@Value("${mqtt.topic.waterlevelsenor}")
	private String mqtt_topic_waterlevelsenor;
	
	@Bean
	public void subscribeToWaterLevelSensor() throws MqttException
	{
			
		mqtt.subscribe("test", new IMqttMessageListener() {
            public void messageArrived (final String topic, final MqttMessage message) throws Exception {
                final String payload = new String(message.getPayload());

                System.out.println("Received operation " + payload);
                
            }
		});
	}

}
