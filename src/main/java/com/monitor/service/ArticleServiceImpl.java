package com.monitor.service;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.monitor.dao.ArticleDao;
import com.monitor.model.Article;

public class ArticleServiceImpl implements ArticleService {

	private ArticleDao articleDao;
	
	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}


	public void addArticle(Article article) {
		// TODO Auto-generated method stub
		articleDao.addArticle(article);
	}

	public Object queryArticle(Criterion[] criterions) {
		// TODO Auto-generated method stub
		return articleDao.queryArticle(criterions);
	}

	public List<?> queryArticle(Criterion[] criterions, int start, int limit) {
		// TODO Auto-generated method stub
		return articleDao.queryArticle(criterions, start, limit);
	}

	public Long countArticle(Criterion[] criterions) {
		// TODO Auto-generated method stub
		return articleDao.countArticle(criterions);
	}

	public List<?> queryArticle(String hql) {
		// TODO Auto-generated method stub
		return articleDao.queryArtile(hql);
	}

	public List<?> queryArtile(String hql, int start, int limit) {
		// TODO Auto-generated method stub
		return articleDao.queryArtile(hql, start, limit);
	}

	public boolean deleteArticle(Article article) {
		this.articleDao.deleteArticle(article);
		return true;
	}

}
