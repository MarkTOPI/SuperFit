package com.example.superfit;

public class Recipe {
    private String label;
    private String calories;
    private String protein;
    private String fats;
    private String carbs;
    private String imageFoot;

    public Recipe() {
        label = "Unknown";
        calories = "0.00";
        protein = "0.00";
        fats = "0.00";
        carbs = "0.00";
        imageFoot= "";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getImageFoot() {
        return imageFoot;
    }

    public void setImageFoot(String imageFoot) {
        this.imageFoot = imageFoot;
    }
}
