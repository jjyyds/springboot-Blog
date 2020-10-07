package com.yc.springbootBlog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.yc.springbootBlog.bean.Flink;

public interface FlinkMapper {
	@Select("select * from flink")
	public List<Flink> selectAll();
}
