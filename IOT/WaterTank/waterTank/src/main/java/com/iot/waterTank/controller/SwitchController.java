package com.iot.waterTank.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.iot.waterTank.mysql.repository.SwitchRepository;

@Controller
public class SwitchController {

	private static final Logger logger = LogManager.getLogger(SwitchController.class);

	@Autowired
	public IMqttClient mqtt;

	@Autowired
	public SwitchRepository switchRepository;

	@Value("${mqtt.topic.switch}")
	private String mqtt_topic_switch;

	@GetMapping("/switch")
	@ResponseBody
	public Switch sayHello() {

		Switch s = new Switch();

		s.setStatus(GlobalVariables.switchStatus);
		logger.debug("State = ");
		return s;
	}

	@PostMapping("/setSwitch")
	@ResponseBody
	public Switch setSwitchState(@RequestBody() Switch state) throws MqttPersistenceException, MqttException {

		logger.debug("State = " + state);

		Switch s = switchRepository.findByName(state.getName());

		s.setStatus(state.getStatus());
		s.setUpdateTimestamp(new Date());

		switchRepository.save(s);

		mqtt.publish(mqtt_topic_switch, new MqttMessage(s.getStatus().getBytes()));

		return s;
	}

}
