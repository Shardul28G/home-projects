package com.iot.waterTank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iot.waterTank.globalVaraibles.GlobalVariables;
import com.iot.waterTank.model.Switch;
import com.iot.waterTank.model.WaterLevel;

@Controller
public class SwitchController {
	
	

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
	  public Switch setSwitchState(@RequestBody() String state)
	  {
		  GlobalVariables.switchStatus = state;		
		  System.out.println("State = " + state);
		  
		  Switch s = new Switch();
		  
		  s.setStatus(GlobalVariables.switchStatus);
		  
		  return s;
	  }
	  
	  

}
