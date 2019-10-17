package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Data
@Table(name = "UT_Base_UserReUnit")
public class UtBaseUserreunit implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "unit_id")
    private Long unitId;

    private static final long serialVersionUID = 1L;

}