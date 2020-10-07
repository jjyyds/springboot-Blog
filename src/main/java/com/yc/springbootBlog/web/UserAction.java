package com.yc.springbootBlog.web;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yc.springbootBlog.bean.Result;
import com.yc.springbootBlog.bean.User;
import com.yc.springbootBlog.biz.BizException;
import com.yc.springbootBlog.biz.UserBiz;
import com.yc.springbootBlog.dao.UserMapper;
import com.yc.springbootBlog.util.MD5Utils;

@RestController  //json
public class UserAction {
	
	@Resource
	private UserBiz ub;
	
	@Resource
	private UserMapper um;
	
	@RequestMapping("login") //=>PostMapping
	public Result login(@Valid User user,Errors errors,HttpSession session) {
		//用户输入验证
		if(errors.hasFieldErrors("account") || errors.hasFieldErrors("pwd")) {
			return new Result(0,"字段验证错误",errors.getAllErrors());
		}
		
		//业务逻辑验证
		try {
			// md5加密
			user.setPwd(MD5Utils.stringToMD5(user.getPwd()));
			User dbuser = ub.login(user);
			session.setAttribute("loginedUser", dbuser);
			return new Result(1,"登录成功!");
		} catch (BizException e) {
			e.printStackTrace();
			errors.rejectValue("account", "AccountOrPwdFailure", e.getMessage());
			return new Result(0,"业务逻辑验证错误",errors.getAllErrors());
		}
		
	}
	
	/**
	 * 
	 * @param user
	 * @param errors
	 * @return 要在被RestController标注的控制器里面跳转页面
	 * 			必须返回 ModelAndView 类型的值
	 * 
	 * 	register.html ==> 成功 index.html
	 * 				  ==> 失败 register.html	
	 */
	@RequestMapping("register")
	public ModelAndView register(@Valid User user,Errors errors,ModelAndView mav) {
		// 设置默认（成功）跳转的页面
		// 页面跳转行为： 1. 请求转发（默认）， 2 响应重定向
		// redirect:index 响应重定向跳转，页面的地址栏会变化
		mav.setViewName("redirect:index");
		//验证用户输入的信息
		if (errors.hasErrors()) {
			mav.setViewName("register");
		}else {
			try {
				// md5加密
				user.setPwd(MD5Utils.stringToMD5(user.getPwd()));
				ub.register(user);
			} catch (BizException e) {
				e.printStackTrace();
				errors.rejectValue("account", "AccountFailure", e.getMessage());
				mav.addObject("errors", errors.getAllErrors());
				mav.setViewName("register");
			}
		}
		//将用户对象传回给页面，实现表单回填
		mav.addObject("user", user);
		mav.addObject("errors", errors.getFieldErrors());
		return mav;
	}
	
	@GetMapping("toreg")
	public ModelAndView toregister(ModelAndView mav) {
		mav.setViewName("register");
		return mav;
	}
	
	@GetMapping("forget.html")
	public ModelAndView toforget(ModelAndView mav) {
		mav.setViewName("forget");
		return mav;
	}
	
	@GetMapping("getQuestion")
	public Result getQuestion(String account) {
		if(account==null || account.trim().isEmpty()) {
			return new Result(0,"用户名不能为空");
		}
		User user = um.selectByAccount(account);
		if(user==null) {
			return new Result(0,"该用户不存在!");
		}
		return new Result(1,user.getPwdQuestion());
	}
	
	@GetMapping("answer")
	public Result answer(String account,String pwdAnswer) {
		if(account==null || account.trim().isEmpty()) {
			return new Result(0,"用户名不能为空");
		}
		if(pwdAnswer==null || pwdAnswer.trim().isEmpty()) {
			return new Result(0,"密码回答不能为空");
		}
		User user = um.selectByAccountAndPwdAnswer(account,pwdAnswer);
		if(user==null) {
			return new Result(0,"问题回答不正确!");
		}
		return new Result(1,"问题回答正确!");
	}
	
	@PostMapping("resetPwd")
	public Result resetPwd(String account, String pwd) {
		if(account == null || account.trim().isEmpty()) {
			return new Result(0,"用户名不能为空!");
		}
		if(pwd == null || pwd.trim().isEmpty()) {
			return new Result(0,"密码不能为空!");
		}
		um.resetPwd(account,MD5Utils.stringToMD5(pwd));
		return new Result(1,"密码重置成功!");
	}
	
	/**
	 * 实现文件上传 AJAX
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("uploadImg")
	public Result uploadImg(@RequestParam("img") MultipartFile file) throws IllegalStateException, IOException {
		file.transferTo(new File("d:/blog_img/"+file.getOriginalFilename()));
		//回传图片的web地址
		return new Result(1,"/imgs/"+file.getOriginalFilename());
	}
}
