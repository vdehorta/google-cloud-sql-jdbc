package com.sfeir.cloudsqlpoc.service.impl;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.sfeir.cloudsqlpoc.entities.Article;
import com.sfeir.cloudsqlpoc.service.ArticleService;
import com.sfeir.cloudsqlpoc.service.ArticleServiceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleServiceJDBC implements ArticleService {

    private static final Logger LOGGER = Logger.getLogger(ArticleServiceJDBC.class.getName());

    /*private static final String INSTANCE_PATH = "jdbc:google:rdbms://sfeir.com:test-google-cloud-sql:trial-cloudsql-instance-vivien/vega";*/
    private static final String INSTANCE_PATH = "nom_complet_instance_cloudsql"; //Récupérable à l'adresse https://code.google.com/apis/console

    public ArticleServiceJDBC() {
        try {
            DriverManager.registerDriver(new AppEngineDriver());
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Error while registering the JDBC driver");
        }
    }

    @Override
    public void createArticle(Article a) throws ArticleServiceException {
        Connection c = null;
        try {
            c = getConnection();
            String statement ="INSERT INTO Article (title, text) VALUES(? , ? );";
            PreparedStatement stmt = c.prepareStatement(statement);
            stmt.setString(1, a.getTitle());
            stmt.setString(2, a.getText());
            int success = stmt.executeUpdate();
            if(success == 1) {
                LOGGER.info("Article of title '" + a.getTitle() + "' and text '" + a.getText() + "' created successfully");
            } else if (success == 0) {
                LOGGER.info("Error in SQL request while creating article of title '" + a.getTitle() + "' and text '" + a.getText() + "'");
                throw new ArticleServiceException("Error in SQL request while creating article of title '" + a.getTitle() + "' and text '" + a.getText() + "'");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Error while creating article of title '" + a.getTitle() + "' and text '" + a.getText() + "'");
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    @Override
    public Article getArticle(Long id) throws ArticleServiceException {
        Connection c = null;
        ResultSet result = null;
        Article a = null;
        try {
            c = getConnection();
            String statement ="SELECT * FROM Article a WHERE a.id = ?;";
            PreparedStatement stmt = c.prepareStatement(statement);
            stmt.setString(1, id.toString());
            result = stmt.executeQuery();
            if (result.next()) {
                a = new Article();
                a.setId(result.getLong("id"));
                a.setTitle(result.getString("title"));
                a.setText(result.getString("text"));
                LOGGER.info("Got article of id '" + id + "'");
            } else {
                LOGGER.info("No existing article with id " + id);
                throw new ArticleServiceException("No existing article with id " + id);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Error while getting article of id '" + a.getId() + "'");
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ignore) {}
            }
        }
        return a;
    }

    @Override
    public List getArticles() throws ArticleServiceException {
        Connection c = null;
        ResultSet result = null;
        List<Article> articles = null;
        Article a = null;
        try {
            c = getConnection();
            String statement ="SELECT * FROM Article;";
            PreparedStatement stmt = c.prepareStatement(statement);
            result = stmt.executeQuery();
            articles = new ArrayList<Article>();
            while (result.next()) {
                a = new Article();
                a.setId(result.getLong("id"));
                a.setTitle(result.getString("title"));
                a.setText(result.getString("text"));
                articles.add(a);
            }
            LOGGER.info("Got articles list, size of list = " + articles.size());
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Error while getting articles list");
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ignore) {}
            }
        }
        return articles;
    }

    @Override
    public void deleteArticle(Long id) throws ArticleServiceException {
        Connection c = null;
        try {
            c = getConnection();
            String statement = "DELETE FROM Article WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(statement);
            stmt.setString(1, id.toString());
            int success = stmt.executeUpdate();
            if(success == 1) {
                LOGGER.info("Article of id '" + id + "' deleted successfully");
            } else if (success == 0) {
                LOGGER.info("Error in SQL request while deleting article of id '" + id + "'");
                throw new ArticleServiceException("Error in SQL request while deleting article of id '" + id + "'");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Error while deleting article of id '" + id + "'");
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(INSTANCE_PATH);
    }
}
