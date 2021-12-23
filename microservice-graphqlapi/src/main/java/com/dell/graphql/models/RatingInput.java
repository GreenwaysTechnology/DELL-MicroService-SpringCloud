package com.dell.graphql.models;


public class RatingInput {
    private String title;
    private Integer stars;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
