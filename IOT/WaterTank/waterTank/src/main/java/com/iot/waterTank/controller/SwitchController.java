package com.iot.waterTank.controller;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iot.waterTank.globalVaraibles.GlobalVariables;
import com.iot.waterTank.mysql.model.Switch;

@Controller
public class SwitchController {
	
	@Autowired
	public IMqttClient mqtt;
	
	@Value("${mqtt.topic.switch}")
	private String mqtt_topic_switch;

	  @GetMapping("/switch")
	  @ResponseBody
	  public Switch sayHello() {
	   
		  Switch s = new Switch();
		  
		  s.setStatus(GlobalVariables.switchStatus);
		  System.out.println("State = ");
		  return s;
	  }
	  
	  
	  @PostMapping("/setSwitch")
	  @ResponseBody
	  public Switch setSwitchState(@RequestBody() String state) throws MqttPersistenceException, MqttException
	  {
		  GlobalVariables.switchStatus = state;		
		  System.out.println("State = " + state);
		  
		  Switch s = new Switch();
		  
		  s.setStatus(GlobalVariables.switchStatus);
		  
		  mqtt.publish(mqtt_topic_switch, new MqttMessage((GlobalVariables.switchStatus).getBytes()));
		  
		  return s;
	  }
	  
	  

}
