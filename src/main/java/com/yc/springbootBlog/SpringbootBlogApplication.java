package com.yc.springbootBlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yc.springbootBlog.web.LoginInterceptor;

@SpringBootApplication
@MapperScan("com.yc.springbootBlog.dao")
//SpringMvc 配置拦截器(Springboot 工程)
public class SpringbootBlogApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogApplication.class, args);
	}
	
	/**
	 * 注册拦截器的方法,WebMvcConfigurer提供
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/toAddArticle","/addArticle","/reply.do");
	}
	
	//实现静态资源映射配置
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")//设置web的路径
		.addResourceLocations("classpath:/static/");//设置本地目录路径
		registry.addResourceHandler("/imgs/**")//设置web路径
		.addResourceLocations("file:///d:/blog_img/");//设置本地路径
	}
}
