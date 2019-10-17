package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
public class UtLzRepairInData implements Serializable {
    private String id;
    //执行人
    private String executorID;
    //任务开始时间
    private String taskStartTime;
    //任务结束时间
    private String taskEndTime;

    private static final long serialVersionUID = 1L;

}