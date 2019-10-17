package com.gw.generatereport;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Component;

import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;

@Component
public class SendMail {
	
//	@Value("${yx.smtpserver}")
//	private  String SMTPSERVER;
//	@Value("${yx.smtpport}")
//	private  String SMTPPORT;
//	@Value("${yx.account}")
//	private  String ACCOUT;
//	@Value("${yx.pwd}")
//	private  String PWD;
	@Resource
	private PropertiesManageService propertiesManageService;

	public  void testSendEmail(File attaches, String recevice, String title, String content) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		// 创建邮件配置
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", properties.getYxSmtpserver()); // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.port", properties.getYxSmtpport());
//		props.setProperty("mail.smtp.host", "smtp.163.com"); // 发件人的邮箱的 SMTP 服务器地址
//		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.setProperty("mail.smtp.ssl.enable", "true");// 开启ssl

		// 根据邮件配置创建会话，注意session别导错包
		Session session = Session.getDefaultInstance(props);
		// 开启debug模式，可以看到更多详细的输入日志
		session.setDebug(true);
		// 创建邮件
		MimeMessage message = createEmail(session, attaches, recevice, title, content);
		// 获取传输通道
		Transport transport = session.getTransport();
		transport.connect(properties.getYxSmtpserver(), properties.getYxAccount(), properties.getYxPwd());
//		transport.connect("smtp.163.com", "zengfg1997@163.com", "wy5201314");
		// 连接，并发送邮件
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

	}

	public  MimeMessage createEmail(Session session,File attaches, String recevice, String title, String content)
			throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		// 根据会话创建邮件
		MimeMessage msg = new MimeMessage(session);
		// address邮件地址, personal邮件昵称, charset编码方式
		InternetAddress fromAddress = new InternetAddress(properties.getYxAccount(), title, "utf-8");
//		InternetAddress fromAddress = new InternetAddress("zengfg1997@163.com", title, "utf-8");
		// 设置发送邮件方
		msg.setFrom(fromAddress);
		InternetAddress receiveAddress = new InternetAddress(recevice, title, "utf-8");
		// 设置邮件接收方
		msg.setRecipient(RecipientType.TO, receiveAddress);
		// 设置邮件标题
		 msg.setSubject(title, "utf-8");

		Multipart multipart = new MimeMultipart();
		// 设置文本消息部分
		// 邮件正文
		BodyPart contentPart = new MimeBodyPart();
		// contentPart.setContent("测试测试测试", "text/html;charset=utf-8");
//		multipart.addBodyPart(contentPart);
		// 邮件附件
		if (attaches != null) {
			DataSource source = new FileDataSource(attaches);
			contentPart.setDataHandler(new DataHandler(source));
			// 避免中文乱码的处理
			contentPart.setFileName(MimeUtility.encodeWord(attaches.getName()));
			multipart.addBodyPart(contentPart);
		}
//		if (attaches != null) {
//			for (File attachment : attaches) {
//				BodyPart attachmentPart = new MimeBodyPart();
//				DataSource source = new FileDataSource(attachment);
//				attachmentPart.setDataHandler(new DataHandler(source));
//				// 避免中文乱码的处理
//				attachmentPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
//				multipart.addBodyPart(attachmentPart);
//			}
//		}
//		Calendar now = Calendar.getInstance();  
		msg.setContent(multipart);
//		msg.setText(content+"-"+now.get(Calendar.YEAR)+"年"+ (now.get(Calendar.MONTH) + 1)+"月--"+title);
		msg.setSentDate(new Date());
		// 保存设置
		msg.saveChanges();
		return msg;
	}
}
