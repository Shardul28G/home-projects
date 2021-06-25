package com.iot.waterTank.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iot.waterTank.globalVaraibles.GlobalVariables;
import com.iot.waterTank.mysql.model.WaterTank;
import com.iot.waterTank.mysql.repository.WaterTankRepository;

@Controller
public class WaterLevelController {

	private static final Logger logger = LogManager.getLogger(WaterLevelController.class);

	@Autowired
	public IMqttClient mqtt;

	@Autowired
	public WaterTankRepository waterTankRepository;

	@Value("${mqtt.topic.switch}")
	private String mqtt_topic_switch;

	@Value("${mqtt.topic.waterlevelsenor}")
	private String mqtt_topic_waterlevelsenor;

	@PostMapping("/setWaterLevel")
	@ResponseBody
	public WaterTank setWaterLevel(@RequestBody() WaterTank wl) throws MqttPersistenceException, MqttException {

//	   
//		  if(wl.getLarge_tank_level() > GlobalVariables.largeTankPumpStartThreshold 
//				  &&  !(wl.getSmall_tank_level() < GlobalVariables.smallTankPumpStoptThreshold))
//		  {
//			  GlobalVariables.switchStatus ="ON"; 
//		  }
//		  
//		  else if( wl.getLarge_tank_level() < GlobalVariables.largeTankPumpStopThreshold )
//		  {
//			  GlobalVariables.switchStatus ="OFF"; 
//		  }
//		  
//		  if(wl.getSmall_tank_level() > GlobalVariables.smallTankPumpStartThreshold 
//				  && !(wl.getLarge_tank_level() < GlobalVariables.largeTankPumpStopThreshold) )
//		  {
//			  GlobalVariables.switchStatus ="ON"; 
//		  }
//		  
//		  else if ( wl.getSmall_tank_level() < GlobalVariables.smallTankPumpStoptThreshold)
//		  {
//			  GlobalVariables.switchStatus ="OFF"; 
//		  }
//		  
//		  GlobalVariables.largeTankLevel = wl.getLarge_tank_level(); 
//		  GlobalVariables.smallTankLevel = wl.getSmall_tank_level() ; 

		logger.debug("Large Tank Level =  " + GlobalVariables.largeTankLevel + "Small Tank Level =  "
				+ GlobalVariables.smallTankLevel);

		mqtt.publish(mqtt_topic_switch, new MqttMessage((GlobalVariables.switchStatus).getBytes()));

		return wl;
	}

	@PostMapping("/setThreshold")
	@ResponseBody
	public WaterTank setThreshold(@RequestBody() WaterTank wl) {

		WaterTank wt = waterTankRepository.findByName(wl.getName());

		if (wl.getEmpty_threshold_level() != null) {
			wt.setEmpty_threshold_level(wl.getEmpty_threshold_level());
		}
		if (wl.getFull_threshold_level() != null) {
			wt.setFull_threshold_level(wl.getFull_threshold_level());
		}

		wt.setUpdateTimestamp(new Date());

		return wl;
	}

	@GetMapping("/getWaterLevel/{name}")
	@ResponseBody
	public WaterTank getWaterLevel(@RequestParam String name)
			throws MqttPersistenceException, MqttException, InterruptedException {

		WaterTank wt = waterTankRepository.findByName(name);

		Date comparisonDate = new Date();

		// wl.setLarge_tank_level(GlobalVariables.largeTankLevel);
		// wl.setSmall_tank_level(GlobalVariables.smallTankLevel);

		mqtt.publish(mqtt_topic_waterlevelsenor, new MqttMessage("T".getBytes()));

		Integer count = 0;

		while (wt.getUpdateTimestamp().getTime() - comparisonDate.getTime() > 0) {
			logger.debug("Waiting");
			Thread.sleep(500);
			count += 1;

			if (count == 120) {
				logger.debug("TimeOut");
				break;
			}
		}

		wt = waterTankRepository.findByName(name);

		return wt;
	}

	@Bean
	public void subscribeToWaterLevelSensor() throws MqttException {

		mqtt.subscribe(mqtt_topic_waterlevelsenor, new IMqttMessageListener() {
			public void messageArrived(final String topic, final MqttMessage message) throws Exception {
				final String payload = new String(message.getPayload());

				logger.debug("Received operation " + payload);
				// ID:Name:Value:Action|
				List<String> separatedMess = separateSubscribeMessage(payload);
				Float tank_level = Float.parseFloat(separatedMess.get(2));
				WaterTank wl = waterTankRepository.findByName(separatedMess.get(1));
				logger.debug("WaterTank " + wl);

				if (tank_level <= wl.getFull_threshold_level()) { // sensor value will be opposite fullthreshold will be
																	// small and emptythreshold will be large
					mqtt.publish(mqtt_topic_switch, new MqttMessage("OFF".getBytes()));
				} else if (tank_level >= wl.getEmpty_threshold_level()) {
					mqtt.publish(mqtt_topic_switch, new MqttMessage("ON".getBytes()));
				}

				wl.setTank_level(tank_level);
				wl.setUpdateTimestamp(new Date());
				waterTankRepository.save(wl);

			}
		});
	}

	public List<String> separateSubscribeMessage(String message) {
		List<String> mess = new ArrayList<String>();

		Integer index = 0;

		String temp = "";
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == ':') {
				mess.add(index, temp);
				index += 1;
				temp = "";
			} else if (message.charAt(i) == '|') {
				break;
			} else {
				temp += message.charAt(i);
			}

		}

		return mess;
	}

}
