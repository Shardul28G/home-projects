package com.iot.waterTank.mysql.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "watertank")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class WaterTank {

	public WaterTank() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "tank_level")
	private Float tank_level;

	@Column(name = "full_threshold_level")
	private Float full_threshold_level;

	@Column(name = "empty_threshold_level")
	private Float empty_threshold_level;

	@Column(name = "create_timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimestamp = new Date();

	@Column(name = "update_timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTimestamp = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getTank_level() {
		return tank_level;
	}

	public void setTank_level(Float tank_level) {
		this.tank_level = tank_level;
	}

	public Float getFull_threshold_level() {
		return full_threshold_level;
	}

	public void setFull_threshold_level(Float full_threshold_level) {
		this.full_threshold_level = full_threshold_level;
	}

	public Float getEmpty_threshold_level() {
		return empty_threshold_level;
	}

	public void setEmpty_threshold_level(Float empty_threshold_level) {
		this.empty_threshold_level = empty_threshold_level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	@Override
	public String toString() {
		return "WaterTank [id=" + id + ", name=" + name + ", tank_level=" + tank_level + ", full_threshold_level="
				+ full_threshold_level + ", empty_threshold_level=" + empty_threshold_level + ", createTimestamp="
				+ createTimestamp + ", updateTimestamp=" + updateTimestamp + "]";
	}

}
