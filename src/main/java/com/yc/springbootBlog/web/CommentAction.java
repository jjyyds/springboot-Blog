package com.yc.springbootBlog.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.yc.springbootBlog.bean.Comment;
import com.yc.springbootBlog.bean.Result;
import com.yc.springbootBlog.bean.User;
import com.yc.springbootBlog.dao.CommentMapper;

@RestController
public class CommentAction {
	
	@Resource
	private CommentMapper cm;
	
	@GetMapping("queryComm")
	public Result selectByArticleId(int articleid) {
		return new Result(1,"评论查询成功",cm.selectByArticleId(articleid));
	}
	
	@PostMapping("reply.do")
	public Result create(@Valid Comment comment,Errors errors,@SessionAttribute User loginedUser) {
		if(errors.hasErrors()) {
			return new Result(0,"评论验证错误",errors.getAllErrors());
		}
		comment.setCreateby(loginedUser.getId());
		cm.insert(comment);
		return new Result(1,"评论发表成功",comment);
	}
}
