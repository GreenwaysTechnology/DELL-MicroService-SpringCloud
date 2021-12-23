package com.dell.graphql.models;

public class Movie {
    private String id;
    private String title;
    private Hero hero;

    public Movie(String id, String title, Hero hero) {
        this.id = id;
        this.title = title;
        this.hero = hero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
