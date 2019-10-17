package com.gw.mapper.entity;

import lombok.Data;

@Data
public class InspectTaskDownlog {
    private Long logId;

    private Long taskId;

    private Long userId;

    public InspectTaskDownlog() {
    }

    public InspectTaskDownlog(Long taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }
}