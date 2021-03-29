package com.iot.waterTank.model;

public class WaterLevel {

	
	
	public WaterLevel() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Integer large_tank_level;
	
	private Integer small_tank_level;

	public Integer getLarge_tank_level() {
		return large_tank_level;
	}

	public void setLarge_tank_level(Integer large_tank_level) {
		this.large_tank_level = large_tank_level;
	}

	public Integer getSmall_tank_level() {
		return small_tank_level;
	}

	public void setSmall_tank_level(Integer small_tank_level) {
		this.small_tank_level = small_tank_level;
	}

	@Override
	public String toString() {
		return "WaterLevel [large_tank_level=" + large_tank_level + ", small_tank_level=" + small_tank_level + "]";
	}
	
	
	
}
