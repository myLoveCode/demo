package com.example;

import javax.servlet.MultipartConfigElement;

import org.apache.catalina.Context;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	/**
     * 文件上传配置
     * 
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("20480KB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("20480KB");
        return factory.createMultipartConfig();
    }
    

//  测试Intellij IDEA
//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//		TomcatContextCustomizer contextCustomizer = new TomcatContextCustomizer() {
//			@Override
//			public void customize(Context context) {
//				context.addWelcomeFile("index.jsp");
//				context.setWebappVersion("3.1");
//			}
//		};
//		factory.addContextCustomizers(contextCustomizer);
//		return factory;
//	}
}
