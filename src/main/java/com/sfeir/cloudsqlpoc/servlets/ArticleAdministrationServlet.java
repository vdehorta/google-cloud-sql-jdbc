package com.sfeir.cloudsqlpoc.servlets;

import com.sfeir.cloudsqlpoc.entities.Article;
import com.sfeir.cloudsqlpoc.service.ArticleService;
import com.sfeir.cloudsqlpoc.service.impl.ArticleServiceJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleAdministrationServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(ArticleAdministrationServlet.class.getName());

    //private static ArticleService articleService = new ArticleServiceJPA();
    private static ArticleService articleService = new ArticleServiceJDBC();

    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String articleTitle = request.getParameter("articleTitle");
        String articleText = request.getParameter("articleText");
        if (articleTitle.equals("") || articleText.equals("")) {
            out.println("<html><head></head><body>Missing parameters !</body></html>");
            logger.log(Level.INFO, "Paramètre vide");
        } else {
            logger.log(Level.INFO, "Paramètres valides, requete à executer");

            try {
                articleService.createArticle(new Article(articleTitle, articleText));
                out.println("<html><head></head><body><h1>Article créé !<h1>");
                /*List<Article> articles = articleService.getArticles();
                if (!articles.isEmpty()) {
                    out.println("<h1>Liste des articles en base :</h1>");
                    for (Article a : articles) {
                        out.println("<h3>Article " + a.getId() + " : title="+a.getTitle()+", text="+a.getText()+"</h3>");
                    }
                }*/
                out.println("</body></html>");
            } catch (Exception e) {
                out.println("<html><head></head><body>Error !</body></html>");
            }
        }
    }
}
