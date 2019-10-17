package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_User_Notify_Rel")
public class UtUserNotifyRel implements Serializable {
    @Id
    @Column(name = "unit_user_id")
    private Long unitUserId;

    @Id
    @Column(name = "notify_id")
    private Long notifyId;
    
    @Column(name = "is_read")
    private String isRead;

    private static final long serialVersionUID = 1L;

    /**
     * @return unit_user_id
     */
    public Long getUnitUserId() {
        return unitUserId;
    }

    /**
     * @param unitUserId
     */
    public void setUnitUserId(Long unitUserId) {
        this.unitUserId = unitUserId;
    }

    /**
     * @return notify_id
     */
    public Long getNotifyId() {
        return notifyId;
    }

    /**
     * @param notifyId
     */
    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
    
}