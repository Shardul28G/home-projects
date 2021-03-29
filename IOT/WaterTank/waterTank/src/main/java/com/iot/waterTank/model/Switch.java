package com.iot.waterTank.model;

public class Switch {

	public Switch() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String Status;
	
	

	public String getStatus() {
		return Status;
	}



	public void setStatus(String status) {
		Status = status;
	}



	@Override
	public String toString() {
		return "Switch [Status=" + Status + "]";
	}
	
	

}
