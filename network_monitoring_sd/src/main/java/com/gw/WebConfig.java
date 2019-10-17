package com.gw;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gw.common.SnowflakeIdWorker;

import com.gw.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final String[] notLoginInterceptPaths = {"/ueditor/**", "/css/**",
			"/download/**", "/image/**", "/html/**", "/js/**",
			"/file/upload/**", "/swagger*/**", "/webjars/**", "/**.ico"};
	@Value("${cbs.imagesPath}")
	private String mImagesPath;
	@Value("${spring.servlet.multipart.maxFileSize}")
	private String maxFileSize;
	@Value("${spring.servlet.multipart.maxRequestSize}")
	private String maxRequestSize;
	@Value("${cbs.sd.images}")
	private String sdImages;
	@Value("${snowflakeIdWorker.workerId}")
	private long workerId;
	@Value("${snowflakeIdWorker.dataCenterId}")
	private long dataCenterId;

	/**
	 * 跨域访问设置
	 * 
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 4
		return new CorsFilter(source);
	}

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
		corsConfiguration.addAllowedHeader("*"); // 2允许任何头
		corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
		return corsConfiguration;
	}

	/**
	 * 添加拦截器 拦截除了static
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**")
				.excludePathPatterns(notLoginInterceptPaths);
	}

	@Bean
	public SecurityInterceptor authenticationInterceptor() {
		return new SecurityInterceptor();
	}

	/**
	 * 后台数据返回null的字段改为空字符串
	 * 
	 * @param builder
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
					throws IOException, JsonProcessingException {
				jsonGenerator.writeString("");
			}
		});
		return objectMapper;
	}

	/**
	 * 访问硬盘文件
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/file/upload/**").addResourceLocations("file:" + mImagesPath);
		registry.addResourceHandler("/images/upload/**").addResourceLocations("file:" + sdImages);
	}

	/**
	 * id生成类 单例
	 * 
	 * @return
	 */
	@Bean
	public SnowflakeIdWorker getSnowflakeIdWorker() {
		return new SnowflakeIdWorker(workerId, dataCenterId);
	}

	/**
	 * 文件上传配置
	 * 
	 * @return MultipartConfigElement
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个文件最大
		factory.setMaxFileSize(maxFileSize);
		// 设置总上传数据总大小
		factory.setMaxRequestSize(maxRequestSize);
		return factory.createMultipartConfig();
	}

	/**
	 * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
	 * 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
	 * 
	 * @return
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	/*
	  * ehcache 主要的管理器
	  */
	 @Bean(name = "cacheManager")
	 public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
	     return new EhCacheCacheManager (bean.getObject ());
	 }

	 /*
	  * 据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
	  */
	 @Bean
	 public EhCacheManagerFactoryBean ehCacheManagerFactoryBeans(){
	     EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
	     cacheManagerFactoryBean.setConfigLocation (new ClassPathResource ("ehcache.xml"));
	     cacheManagerFactoryBean.setShared (true);
	     return cacheManagerFactoryBean;
	 }

}
