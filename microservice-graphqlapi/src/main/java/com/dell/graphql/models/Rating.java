package com.dell.graphql.models;

public class Rating {
    private String title;
    private Integer avgStars;

    public Rating(Integer avgStars) {
        this.avgStars = avgStars;
    }

    public Rating(String title, Integer stars) {
        this.title = title;
        this.avgStars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStars() {
        return avgStars;
    }

    public void setStars(Integer avgStars) {
        this.avgStars = avgStars;
    }
}
