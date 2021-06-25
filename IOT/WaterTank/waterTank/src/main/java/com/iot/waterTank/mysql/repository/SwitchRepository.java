package com.iot.waterTank.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iot.waterTank.mysql.model.Switch;


public interface SwitchRepository extends JpaRepository<Switch, Integer> {
	
	
	@Query("Select s from Switch s where s.name = :name")
	Switch findByName(String name);

}
