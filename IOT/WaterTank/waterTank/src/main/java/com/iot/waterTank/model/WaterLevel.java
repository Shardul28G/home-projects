package com.iot.waterTank.model;

public class WaterLevel {

	
	
	public WaterLevel() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Integer id;
	
	private Integer tank_level;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTank_level() {
		return tank_level;
	}

	public void setTank_level(Integer tank_level) {
		this.tank_level = tank_level;
	}

	@Override
	public String toString() {
		return "WaterLevel [id=" + id + ", tank_level=" + tank_level + "]";
	}

	
	
	
	
}
