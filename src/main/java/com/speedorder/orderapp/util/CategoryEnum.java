package com.speedorder.orderapp.util;

public enum CategoryEnum {
    GAMES("Games"),
    TOYS("Toys"),
    GROCERY("Grocery"),
    BOOKS("Books"),
    BABY("Baby");

    private final String category;

    CategoryEnum(String category) {
        this.category = category;
    }


    public String getCategory() {
        return category;
    }
}
