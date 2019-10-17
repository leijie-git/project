package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 巡查点检查项配置管理
 */
@Data
@Table(name = "UT_Inspect_Base_Rel")
public class UTInspectBaseRel {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "SiteID")
	private Long SiteID;

	@Column(name = "SiteClassDetialID")
	private Long SiteClassDetialID;

	@Column(name = "SiteClassID")
	private Long SiteClassID;

	@Column(name = "unitId")
	private Long unitId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "Lastupdate")
	private Date lastupdate;
	//描述
	@Column(name ="represent")
	private String Describe;
  	//图片路径
	@Column(name ="Img")
	private String ImagePath;
}
