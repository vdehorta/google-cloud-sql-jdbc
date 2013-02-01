package com.sfeir.cloudsqlpoc.entities;

/**
 * Entity class or POJO representing a Contact
 * @author saroop
 *
 */
public class Article {
    private Long id;
    private String title;
    private String text;

    public Article() {
    }

    public Article(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}