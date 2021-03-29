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
public class WaterLevelController {
	
	
	  @PostMapping("/setWaterLevel")
	  @ResponseBody
	  public WaterLevel setWaterLevel(@RequestBody() WaterLevel wl ) {
		  
		  
	   
		  if(wl.getLarge_tank_level() > GlobalVariables.largeTankPumpStartThreshold 
				  &&  !(wl.getSmall_tank_level() < GlobalVariables.smallTankPumpStoptThreshold))
		  {
			  GlobalVariables.switchStatus ="ON"; 
		  }
		  
		  else if( wl.getLarge_tank_level() < GlobalVariables.largeTankPumpStopThreshold )
		  {
			  GlobalVariables.switchStatus ="OFF"; 
		  }
		  
		  if(wl.getSmall_tank_level() > GlobalVariables.smallTankPumpStartThreshold 
				  && !(wl.getLarge_tank_level() < GlobalVariables.largeTankPumpStopThreshold) )
		  {
			  GlobalVariables.switchStatus ="ON"; 
		  }
		  
		  else if ( wl.getSmall_tank_level() < GlobalVariables.smallTankPumpStoptThreshold)
		  {
			  GlobalVariables.switchStatus ="OFF"; 
		  }
		  
		  GlobalVariables.largeTankLevel = wl.getLarge_tank_level(); 
		  GlobalVariables.smallTankLevel = wl.getSmall_tank_level() ; 
		  
		  System.out.println("Large Tank Level =  " + GlobalVariables.largeTankLevel +
				  "Small Tank Level =  " + GlobalVariables.smallTankLevel );
		  
		  return wl;
	  }
	  
	  @PostMapping("/setThreshold")
	  @ResponseBody
	  public WaterLevel setThreshold(@RequestBody() WaterLevel wl)
	  {
		  GlobalVariables.smallTankPumpStoptThreshold = wl.getSmall_tank_level();
		  GlobalVariables.largeTankPumpStopThreshold = wl.getLarge_tank_level();
		  
		  return wl;
	  }
	  
	  @GetMapping("/getWaterLevel")
	  @ResponseBody
	  public String getWaterLevel() {
	   
		  WaterLevel wl = new WaterLevel();
		  
		  wl.setLarge_tank_level(GlobalVariables.largeTankLevel);
		  wl.setSmall_tank_level(GlobalVariables.smallTankLevel);
		  
		  Integer ltl =  250 -  GlobalVariables.largeTankLevel;
		  Integer stl =  120 -  GlobalVariables.largeTankLevel;
		  
		  
		  return "Large WaterTank Level = " +  ltl + " cm " + "\n"
				  +  " Small WaterTank Level = " +stl + " cm. ";
	  }

}
