package com.gw.mapper.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "task_turn_single")
@Data
public class TaskTurnSingle {

	@Column(name = "task_id")
	private Long task_id;

	@Column(name = "change_id")
	private Long change_id;

	@Column(name = "receive_id")
	private Long receive_id;

	@Column(name = "change_time")
	private Date change_time;

	@Column(name = "receive_time")
	private Date receive_time;

	@Column(name = "task_status")
	private Integer task_status;

}
