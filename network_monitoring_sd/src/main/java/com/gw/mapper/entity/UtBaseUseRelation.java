package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "UT_Base_UserRelation")
public class UtBaseUseRelation implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "relation_id")
    private Long relationId;

    private static final long serialVersionUID = 1L;
}