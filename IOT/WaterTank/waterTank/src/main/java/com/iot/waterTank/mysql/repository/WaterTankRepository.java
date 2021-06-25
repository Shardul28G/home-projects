package com.iot.waterTank.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iot.waterTank.mysql.model.WaterTank;

public interface WaterTankRepository extends JpaRepository<WaterTank, Integer> {
	
	@Query("Select wt from WaterTank wt where wt.name = :name")
	WaterTank findByName(String name);

}
