package com.gw.wechat.controller;

import com.test.BaseControllerTest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AppControllerTest extends BaseControllerTest {



    @Test
    public void downLoadTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/app/downLoadTask")
                .param("userId","470235485474652160")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void upLoadTask() throws Exception {
        String json = "[{\"taskdetailid\":\"\",\"taskid\":\"582557513124151296\",\"planid\":\"582557428860583936\",\"plandetailid\":\"582557474507194368\",\"planname\":\"巡查演示\",\"starttime\":\"2019-05-27 00:00:00\",\"endtime\":\"2019-06-02 23:59:59\",\"sitename\":\"办公室203\",\"sitecode\":\"0003\",\"siteclassid\":\"582556290199322624\",\"siteid\":\"582557090417999872\",\"unitid\":\"545994213129453568\",\"inspectcycletype\":\"1\",\"changeuserid\":\"\",\"ischange\":\"0\",\"changetime\":\"\",\"receiveuserid\":\"\",\"isreceive\":\"0\",\"receivetime\":\"\",\"siteClassDetailList\":[{\"id\":\"582556344834326528\",\"checkInfo\":\"办公室电源\",\"checkMethod\":\"\"},{\"id\":\"582556386261467136\",\"checkInfo\":\"办公室卫生\",\"checkMethod\":\"\"}]},{\"taskdetailid\":\"\",\"taskid\":\"582557513069625344\",\"planid\":\"582557428860583936\",\"plandetailid\":\"582557474507194368\",\"planname\":\"巡查演示\",\"starttime\":\"2019-05-27 00:00:00\",\"endtime\":\"2019-06-02 23:59:59\",\"sitename\":\"办公室203\",\"sitecode\":\"0003\",\"siteclassid\":\"582556290199322624\",\"siteid\":\"582557090417999872\",\"unitid\":\"545994213129453568\",\"inspectcycletype\":\"1\",\"changeuserid\":\"\",\"ischange\":\"0\",\"changetime\":\"\",\"receiveuserid\":\"\",\"isreceive\":\"0\",\"receivetime\":\"\",\"siteClassDetailList\":[{\"id\":\"582556344834326528\",\"checkInfo\":\"办公室电源\",\"checkMethod\":\"\"},{\"id\":\"582556386261467136\",\"checkInfo\":\"办公室卫生\",\"checkMethod\":\"\"}]},{\"taskdetailid\":\"\",\"taskid\":\"582557510880198656\",\"planid\":\"582557428860583936\",\"plandetailid\":\"582557474473639936\",\"planname\":\"巡查演示\",\"starttime\":\"2019-05-31 00:00:00\",\"endtime\":\"2019-05-31 23:59:59\",\"sitename\":\"监控室-F3CD-500\",\"sitecode\":\"0002\",\"siteclassid\":\"582556121533775872\",\"siteid\":\"582556982624387072\",\"unitid\":\"545994213129453568\",\"inspectcycletype\":\"0\",\"changeuserid\":\"\",\"ischange\":\"0\",\"changetime\":\"\",\"receiveuserid\":\"\",\"isreceive\":\"0\",\"receivetime\":\"\",\"siteClassDetailList\":[{\"id\":\"582556225690927104\",\"checkInfo\":\"设备在离线\",\"checkMethod\":\"\"}]},{\"taskdetailid\":\"\",\"taskid\":\"569526124246204416\",\"planid\":\"569525896055095296\",\"plandetailid\":\"569526085977374720\",\"planname\":\"消防巡查计划\",\"starttime\":\"2019-05-31 00:00:00\",\"endtime\":\"2019-05-31 23:59:59\",\"sitename\":\"监控室\",\"sitecode\":\"002\",\"siteclassid\":\"569525024071876608\",\"siteid\":\"569525719558782976\",\"unitid\":\"568084530880053248\",\"inspectcycletype\":\"0\",\"changeuserid\":\"\",\"ischange\":\"0\",\"changetime\":\"\",\"receiveuserid\":\"\",\"isreceive\":\"0\",\"receivetime\":\"\",\"siteClassDetailList\":[]},{\"taskdetailid\":\"\",\"taskid\":\"569526114842574848\",\"planid\":\"569525896055095296\",\"plandetailid\":\"569526085935431680\",\"planname\":\"消防巡查计划\",\"starttime\":\"2019-05-31 00:00:00\",\"endtime\":\"2019-05-31 23:59:59\",\"sitename\":\"212办公室\",\"sitecode\":\"001\",\"siteclassid\":\"569525024071876608\",\"siteid\":\"569525517296861184\",\"unitid\":\"568084530880053248\",\"inspectcycletype\":\"0\",\"changeuserid\":\"\",\"ischange\":\"0\",\"changetime\":\"\",\"receiveuserid\":\"\",\"isreceive\":\"0\",\"receivetime\":\"\",\"siteClassDetailList\":[]}]";
        mvc.perform(MockMvcRequestBuilders.post("/app/upLoadTask")
                .param("tasks",json)
                .param("taskID","507566981688328192")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getInspectTaskList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/app/getInspectTaskList"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void bindingNFC() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/app/bindingNFC")
                .param("nfcCode","ncfCode")
                .param("taskID","507566981688328192")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getInspectDetail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/app/inspect/getDetailByNfc?nfcCode=ncfCode"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}