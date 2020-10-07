package com.yc.springbootBlog.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.springbootBlog.bean.User;
import com.yc.springbootBlog.dao.UserMapper;

@Service  //语义组件注解
public class UserBiz {
	
	@Resource
	private UserMapper um;
	
	/**
	 * 登录方法
	 * @param user 用户提交的数据
	 * @return 查询到的用户信息
	 * @throws BizException 
	 */
	public User login(User user) throws BizException {
		User dbuser=um.login(user);
		if(dbuser==null) {
			throw new BizException("用户名或密码错误");
		}
		return dbuser;
	}
	
	public void register(User user) throws BizException {
		//判断该账号是否已经被注册
		if(um.countByAccount(user)>0) {
			throw new BizException("该用户已经存在");
		}
		//将该用户写入数据库
		um.insert(user);
	}
}
