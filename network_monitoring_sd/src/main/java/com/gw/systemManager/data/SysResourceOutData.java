package com.gw.systemManager.data;

import lombok.Data;

/**
 * 输出资源实体
 * @author zfg
 *
 */
@Data
public class SysResourceOutData {

	private String id;

	private String name;

	private String url;

	private Integer seq;

	private String resourceType;

	private String pid;

	private String createDate;

	private String createUser;

	private String updateDate;

	private String updateUser;

	private String pidName;

	private String type;
}
