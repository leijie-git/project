package com.gw.front.couplet.data;

import com.gw.eqdetail.data.AnalogPortVo;
import com.gw.eqdetail.data.DigitalPortVo;
import lombok.Data;

import java.util.List;

@Data
public class FrontInterfaceAppOutData extends FrontInterFaceStatusOutData {
	private List<DigitalPortVo> dataList;//数字量端口数据
	private List<AnalogPortVo> data;//模拟量端口数据
}
