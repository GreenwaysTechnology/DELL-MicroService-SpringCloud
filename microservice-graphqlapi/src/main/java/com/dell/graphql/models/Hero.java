package com.dell.graphql.models;

public class Hero {
    private String heroId;
    private String name;
    private String city;

    public Hero(String heroId, String name, String city) {
        this.heroId = heroId;
        this.name = name;
        this.city = city;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
