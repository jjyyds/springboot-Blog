package com.yc.springbootBlog.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.yc.springbootBlog.bean.Article;
import com.yc.springbootBlog.bean.Category;
import com.yc.springbootBlog.dao.ArticleMapper;
import com.yc.springbootBlog.dao.CategoryMapper;
import com.yc.springbootBlog.dao.FlinkMapper;

//@RestController  所有方法默认返回json数据
@Controller //所有的方法默认返回一个视图的名称(字符串)
public class IndexAction {
	@Resource
	private ArticleMapper am;
	
	@Resource
	private CategoryMapper cm;
	
	@Resource
	private FlinkMapper fm;
	
	@GetMapping(path= {"index","index.html","/"})
	//springmvc使用一个model对象传递数据给页面，model通过方法参数注入进来
	public String index(Model m,@RequestParam(defaultValue = "1")int page) {
		//分页查询设置必须在查询方法执行前设定
		PageHelper.startPage(page, 5);
		List<Article> articles=am.selectNewArticle();
		//将查询出的数据添加到model中 发送给页面
		m.addAttribute("articles", articles);
		m.addAttribute("category", cm.selectAll());
		return "index";
	}
	
	@GetMapping("article.html")
	public String article(int id,Model m) {
		m.addAttribute("article", am.selectById(id));
		return "article";
	}
	
	@GetMapping("links.html")
	public String flink(Model m) {
		m.addAttribute("category", cm.selectAll());
		m.addAttribute("links", fm.selectAll());
		return "links";
	}
	
	@GetMapping("category.html")
	public String category(Model m,@RequestParam("id")int id) {
		m.addAttribute("articles", am.selectByCid(id));
		m.addAttribute("category", cm.selectAll());
		m.addAttribute("links", fm.selectAll());
		return "category";
	}
}
