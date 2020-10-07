package com.yc.springbootBlog.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.yc.springbootBlog.bean.User;

public interface UserMapper {
	
	@Select("select count(*) from user where account=#{account}")
	public int countByAccount(User user);
	
	@Select("select * from user where account=#{account} and pwd=#{pwd}")
	public User login(User user);
	
	@Insert("insert into user values(null,#{name},#{account},#{pwd},#{phone},#{email},#{head},now(),#{status},#{type},#{pwdQuestion},#{pwdAnswer})")
	public void insert(User user);
	
	@Select("select * from user where account=#{account}")
	public User selectByAccount(String account);

	@Select("select * from user where account=#{account} and pwd_answer=#{pwdAnswer}")
	public User selectByAccountAndPwdAnswer(@Param("account")String account, @Param("pwdAnswer")String pwdAndswer);
	
	@Select("update user set pwd=#{pwd} where account=#{account}")
	public void resetPwd(@Param("account")String account, @Param("pwd")String pwd);
}
