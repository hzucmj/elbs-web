package com.monitor.dao.orm;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.zengsource.util.spring.dao.orm.HibernateDaoTemplate;

import com.monitor.dao.ArticleDao;
import com.monitor.model.Article;

public class HibernateArticleDao extends HibernateDaoTemplate implements ArticleDao {

	public void addArticle(Article article) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.save(article);
	}

	public Object queryArticle(Criterion[] criterions) {
		// TODO Auto-generated method stub
		return this.queryUnique(criterions);
	}

	public List<?> queryArticle(Criterion[] criterions, int start, int limit) {
		// TODO Auto-generated method stub
		return this.query(criterions, start, limit);
	}

	@Override
	public Class<?> getPrototypeClass() {
		// TODO Auto-generated method stub
		return Article.class;
	}

	public Long countArticle(Criterion[] criterions) {
		// TODO Auto-generated method stub
		return this.queryCountL(criterions);
	}

	public List<?> queryArtile(String hql) {
		// TODO Auto-generated method stub
		return  this.getSessionFactory().openSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

	public List<?> queryArtile(String hql, int start, int limit) {
		// TODO Auto-generated method stub
		return this.getSessionFactory().openSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}

	public boolean deleteArticle(Article article) {
		super.hibernateTemplate.delete(article);
		return false;
	}

}
