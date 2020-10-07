package com.yc.springbootBlog.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {
	
	//前置拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getSession().getAttribute("loginedUser")==null) {
			//如果没有登录  判断当前是什么类型的请求
			//Accept application/json,text/plain,*/*   ajax请求头
			//Accept text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8  非ajax请求头
			if(request.getHeader("Accept").startsWith("application/json")) {
				//如果是ajax请求，返回json格式数据集
				String json="{ \"code\":0, \"data\":[{\"defaultMessage\":\"请先登录系统!\"}]}";
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().append(json);
			}else {
				//如果是非ajax请求，则跳转页
				response.sendRedirect("index?login=1");
			}
			//false =>请求从此中断，不去访问action
			return false;
		}else {
			//true =>去访问action
			return true;
		}
	}
}
