package com.monitor.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.monitor.model.Article;

public interface ArticleDao {

	
	//发布信息 	
	public void addArticle(Article article);

	//查找信息	
	public Object queryArticle(Criterion[] criterions);
	
	//查找信息，分页显示
	public List<?> queryArticle(Criterion[] criterions, int start, int limit);

	//查找总数
	public Long countArticle(Criterion[] criterions);
	
	//查找信息
	public List<?> queryArtile(String hql);
	
	//查找信息hql
	public List<?> queryArtile(String hql,int start,int limit);
	//删除信息
	public boolean deleteArticle(Article article);
}
