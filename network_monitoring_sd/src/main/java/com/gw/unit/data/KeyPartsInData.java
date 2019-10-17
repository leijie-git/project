package com.gw.unit.data;

import lombok.Data;

/**
 * 重点单位输入实体
 * @author zfg
 *
 */
@Data
public class KeyPartsInData {

	
	private String ID;//主键
	
	private String UnitId;//单位id
	
	private String unitName;//单位名称
	
	private String BuildId;//建筑物id
	
	private String ImportName;//重点部位名称
	
	private String ImportSite;//所在位置
	
	private Integer Floor; //所在层数
	
	private String Height; //所在高度
	
	private String BuildingStructure;//建筑结构
	
	private String Used;//使用功能
	
	private String DTCount;//电梯个数
	
	private String OutCount;//出口个数
	
	private String HZWXX;//火灾危险性
	
	private String BuildArea;//建筑面积
	
	private String NHLevel;//耐火等级
	
	private String FireInfo;//火种情况
	
	private String IsDXFYZDX;//对消防有重大影响
	
	private String IsHZFSHRYSWD;//火灾后发生人员伤亡
	
	private String IsHZHSSD;//火灾后损失大
	
	private String IsYFSHZ;//已发生火灾
	
	private String IsOther;//其他
	
	private String FHBZSLQK;//防火标志设立情况
	
	private String IsBZP;//爆炸品
	
	private String IsYHQHYJHHW;//氧化剂和有机过氧化物
	
	private String IsFSP;//腐蚀品
	
	private String IsYSQT;//压缩气体
	
	private String IsYDP;//有毒品
	
	private String IsZXWXW;//杂项危险物
	
	private String IsYRYT;//易燃液体
	
	private String IsFSX;//放射性
	
	private String IsQT;//其他
	
	private String IsYRGT;//易燃固体
	
	private String ImportImageName;//图片名字
	
	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数
    
    private String keyWord;//搜索关键字
	
	
}
