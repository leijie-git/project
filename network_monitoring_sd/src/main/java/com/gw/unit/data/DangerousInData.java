package com.gw.unit.data;

import lombok.Data;
/**
 * 单位危险品输入实体
 * @author zfg
 *
 */
@Data
public class DangerousInData {

	private String ID;//危险品ID
	
	private String UnitID;//单位ID
	
	private String unitName;//单位名称
	
	private String DangerousName;//危险品名称
	
	private String DangerousCount;//危险品数量
	
	private String CountUnit;//数量单位
	
	private String Site;//存储位置
	
	private String DangerousLevel;//危险品等级
	
	private String DangerousUsed;//危险品用途
	
	private String DangerousCZCS;//危险品处置措施
	
	private String JSFZR;//技术负责人
	
	private String JSFZRDH;//技术负责人电话
	
	private String AQGLR;//安全管理人
	
	private String AQGLRDH;//安全管理人电话
	
	private String IsXFBS;//是否有消防标识
	
	private String OperateDesc;//操作使用说明
	
	private String DangerousType;//危险品类型
	
	private String DangerousState;//危险品状态
	
	private String DangerousImageName;//图片名字
	
	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数
    
    private Integer keyWord;//搜索关键字
	
}
