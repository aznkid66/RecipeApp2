package com.example.patrick.recipeapp2;

public class SearchResultsItem {
    private long id; // database row id
    public final String NAME;
    public final double RATING;
    public final String LINK;

    public SearchResultsItem(String name, double rating, String link) {
        this.id = -1;
        this.NAME = name;
        this.RATING = rating;
        this.LINK = link;
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
}