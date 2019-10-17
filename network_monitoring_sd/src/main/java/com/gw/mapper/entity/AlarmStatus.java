package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "alarm_status_code")
@Data
public class AlarmStatus {

	@Column(name = "alarm_status")
	private String alarmStatus;

	@Column(name = "alarm_name")
	private String alarmName;


}
