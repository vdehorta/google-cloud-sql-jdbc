package com.sfeir.cloudsqlpoc.service;

import com.sfeir.cloudsqlpoc.entities.Article;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sfeir
 * Date: 31/01/13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public interface ArticleService {

    public void createArticle(Article a) throws ArticleServiceException;
    public Article getArticle(Long id) throws ArticleServiceException;
    public List getArticles() throws ArticleServiceException;
    public void deleteArticle(Long id) throws ArticleServiceException;
}
