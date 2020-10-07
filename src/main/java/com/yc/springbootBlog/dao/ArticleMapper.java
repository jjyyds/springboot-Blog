package com.yc.springbootBlog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yc.springbootBlog.bean.Article;

public interface ArticleMapper {
	@Select("select * from article order by id desc")
	@Results(id="rmAct",value={@Result(column = "categoryid",property = "category",
					one = @One(select="com.yc.springbootBlog.dao.CategoryMapper.selectById"))})
	public List<Article> selectNewArticle();
	
	@Select("select * from article where id=#{id}")
	//引用关联查询的配置
	@ResultMap("rmAct")
	public Article selectById(int id);
	
	@Select("select * from article where categoryid=#{id}")
	@ResultMap("rmAct")
	public List<Article> selectByCid(int id);
	
	@Insert("insert into article values(null,#{author},#{title},#{content},null,null,#{categoryid},#{label},null,null,now(),0,0)")
	//获取自增列主键值
	@Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
	public int insert(Article article);
}
