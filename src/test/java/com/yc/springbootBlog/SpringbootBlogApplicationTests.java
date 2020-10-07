package com.yc.springbootBlog;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.yc.springbootBlog.bean.Category;
import com.yc.springbootBlog.bean.User;
import com.yc.springbootBlog.biz.BizException;
import com.yc.springbootBlog.biz.UserBiz;
import com.yc.springbootBlog.dao.CategoryMapper;

@SpringBootTest
class SpringbootBlogApplicationTests {
	
	@Resource
	private UserBiz ub;
	
	@Resource
	private CategoryMapper cm;

	@Test
	public void contextLoads() {
		for(Category c:cm.selectAll()) {
			System.out.println(c);
		}
	}
	
	@Test
	void test() {
		User user=new User();
		user.setAccount("zhangsan");
		user.setPwd("a");
		try {
			User dbuser = ub.login(user);
			Assert.notNull(dbuser, "dbuser不能为空");
		} catch (BizException e) {
			e.printStackTrace();
		}

		user.setPwd("654321");
		try {
			User dbuser = ub.login(user);
			Assert.isNull(dbuser, "dbuser必须为空");
		} catch (BizException e) {
			e.printStackTrace();
			Assert.hasText(e.getMessage(), "用户名或密码错误");
		}
	}

}
