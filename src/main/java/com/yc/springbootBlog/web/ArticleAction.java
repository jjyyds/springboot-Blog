package com.yc.springbootBlog.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.yc.springbootBlog.bean.Article;
import com.yc.springbootBlog.bean.User;
import com.yc.springbootBlog.dao.ArticleMapper;
import com.yc.springbootBlog.dao.CategoryMapper;

@Controller
public class ArticleAction {
	
	@Resource
	private ArticleMapper am;
	
	@Resource
	private CategoryMapper cm;
	
	@GetMapping("toAddArticle")
	public String toAddArticle(Model m) {
		m.addAttribute("cList",cm.selectAll());
		return "addArticle";
	}
	
	@PostMapping("addArticle")
	public ModelAndView addArticle(@Valid Article article,@SessionAttribute User loginedUser,Errors errors,ModelAndView mav) {
		//业务层暂时忽略
		//作者名字
		article.setAuthor(loginedUser.getName());
		if(errors.hasErrors()) {
			mav.setViewName("addArticle");
		}else {
			am.insert(article);
			//响应重定向跳转到文章详情
			mav.setViewName("redirect:article.html?id="+article.getId());
		}
		mav.addObject("article", article);
		return mav;
	}
}
