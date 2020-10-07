package com.yc.springbootBlog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.yc.springbootBlog.bean.Category;

/**
 * 注解(简单sql)+XML(复杂SQL)配置
 * @author 衡阳吴彦祖
 *
 */
public interface CategoryMapper {
	@Select("select * from category")
	public List<Category> selectAll();
	
	@Select("select * from category where id=#{id}")
	public Category selectById(int id);
	
}
