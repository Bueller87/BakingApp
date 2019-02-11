package com.kevlarcodes.bakingapp.models;

public class IngredientTitleBar {

    private String titleText;
    private String format = "%d Ingredients";

    public IngredientTitleBar(int count) {
        titleText = String.format(format, count);
    }

    public String getHeader() {
        return titleText;
    }
}