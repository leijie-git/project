package com.gw.apppush.impl;

import com.gw.apppush.PushAppMessage;
import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * 小米平台推送app通知
 * <p>
 * 参考文档 https://dev.mi.com/console/doc/detail?pId=1278
 **/
public class MiPushAppMsg extends RegisterManager {

    private static final MiPushAppMsg single = new MiPushAppMsg();
    private static Logger log = LoggerFactory.getLogger(PushAppMessage.class);

    public static MiPushAppMsg getInstance() {
        return single;
    }

    /**
     * @param packageName    设置app的包名packageName, packageName必须和开发者网站上申请的结果一致
     * @param through        设置消息是否通过透传的方式至App, 1表示透传消息, 0表示通知栏消息(默认是通知栏消息)
     * @param title          设置在通知栏展示的通知的标题, 不允许全是空白字符, 长度小于50, 一个中英文字符均计算为1(通知栏消息必填)
     * @param description    设置在通知栏展示的通知描述, 不允许全是空白字符, 长度小于128, 一个中英文字符均计算为1(通知栏消息必填)
     * @param messagePayload 设置要发送的消息内容payload, 不允许全是空白字符, 长度小于4KB, 一个中英文字符均计算为1(透传消息回传给APP, 为必填字段, 非透传消息可选)
     * @param notifyType     设置通知类型, type类型支持以下值：1=使用默认提示音提示,2=使用默认震动提示,4=使用默认led灯光提示,
     *                       -1（系统默认值）=以上三种效果都有, 0=以上三种效果都无，即静默推送。并且支持1，2，4的任意OR运算来实现声音、震动和闪光灯的任意组合。
     * @return Message 实例
     */
    public Message buildAndroidMessage(String title, String description, Integer through, Integer notifyType, String packageName, String messagePayload) {
        return new Message.Builder()
                .restrictedPackageName(packageName)
                .title(title)
                .description(description)
                .passThrough(through)
                .payload(messagePayload)
                .notifyType(notifyType)
                //.restrictedPackageNames(packageNames)   设置app的多包名packageNames(多包名发送广播消息), packageNames必须和开发者网站上申请的结果一致, 可以为空, 为空则默认给所有渠道包名推送(不能同时调用restrictedPackageName方法和restrictedPackageNames方法)
                //.timeToLive(milliseconds)   可选项, 消息的生命周期, 若用户离线, 设置消息在服务器保存的时间, 单位: ms(服务器默认最长保留两周)
                //.timeToSend(milliseconds)  可选项, 定时发送消息, timeToSend是以毫秒为单位的时间戳(仅支持七天内的定时消息)
                //.notifyId(Integer id) 可选项, 默认情况下, 通知栏只显示一条推送消息, 如果通知栏要显示多条推送消息, 需要针对不同的消息设置不同的notify_id(相同notify_id的通知栏消息会覆盖之前的)
                //.enableFlowControl(boolean needFlowControl)   可选项, 控制消息是否需要进行[平滑推送](/message/advanced/flow.html)(qps less 3000/second), 默认不支持
                //.extra(String key, String value)    可选项, 对app提供一些[扩展功能](/message/advance/index.html)(除了这些扩展功能, 开发者还可以定义一些key和value来控制客户端的行为, 注：key和value的字符数不能超过1024, 至多可以设置10个key-value键值对)
                .build();
    }

    public Message buildAndroidMessage(String title, String description, String packageName) {
        return new Message.Builder()
                .restrictedPackageName(packageName)
                .title(title)
                .notifyType(1)
                .description(description)
                .build();
    }

    /**
     * 构建 ios 推送消息
     *
     * @param description 通知内容
     * @param sound       指定通知展现时伴随的提醒音文件名。如果找不到指定的文件或者值为 default，那么默认的系统音将会被使用。如果为空，那么将没有声音
     * @param badge       其值为数字，表示当通知到达设备时，应用的角标变为多少。如果没有使用这个字段，那么应用的角标将不会改变。设置为 0 时，会清除应用的角标。
     * @return Message 实例
     */
    public Message buildIOSMessage(String description, String sound, Integer badge) {
        return new Message.IOSBuilder()
                .description(description)
                .soundURL(sound == null ? "default" : sound)
                .badge(badge)
                .category("action")
                //.title(String title)    （IOS10新特性）通知栏展示的标题，此选项不能和description共存。
                //.subtitle(String subtitle)  （IOS10新特性）通知栏展示的子标题，此选项不能和description共存。
                //.body(String body)  （IOS10新特性）通知栏展示的内容，此选项不能和description共存。
                //.mutableContent(String value)   （IOS10新特性）配置通知的mutable-content属性。
                //.timeToLive(int milliseconds)   可选项, 消息的生命周期, 若用户离线, 设置消息在服务器保存的时间, 单位: ms（服务器默认最长保留两周）。
                //.timeToSend(long milliseconds)  可选项, 定时发送消息, timeToSend是用自1970年1月1日以来00:00:00.0UTC时间表示（以毫秒为单位的时间）, 注: 仅支持七天内的定时消息。
                //.category(String category)  可选项, IOS8推送消息快速回复类别。
                //.extra("key", "value")  // 自定义键值对
                .build();
    }

    /**
     * 发送单条消息到单个设备上
     *
     * @param message 消息实例
     * @param regId   设备唯一标识
     * @param retries 重试次数
     * @return 是否发送成功
     */
    public boolean sendMessage(Message message, String regId, Integer retries) {
        List<String> regIds = Collections.singletonList(regId);
        return sendMessage(message, regIds, retries);
    }

    /**
     * 发送消息到一组设备上, regids个数不得超过1000个
     *
     * @param message 消息实例
     * @param regIds  设备唯一标识
     * @param retries 重试次数
     * @return 是否发送成功
     */
    public boolean sendMessage(Message message, List<String> regIds, Integer retries) {
        //Constants.useOfficial();
        try {
            Result result = getSender().send(message, regIds, retries);
            log.info("Server response: {}", "RegId - " + regIds
                    + " * MessageId - " + result.getMessageId()
                    + " * ErrorCode - " + result.getErrorCode().getValue()
                    + " * ErrorDesc - " + result.getErrorCode().getDescription());

            if (result.getMessageId() == null || !result.getErrorCode().equals(ErrorCode.Success))
                return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
