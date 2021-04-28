package com.example.superfit;

public class Product_List {

    private int callories;
    private int protein;
    private int fat;
    private int carbs;

    public Product_List(int callories, int protein, int fat, int carbs){
        this.callories = callories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public int getCallories(){
        return callories;
    }

    public int getProtein(){
        return protein;
    }

    public int getFat(){
        return fat;
    }

    public int getCarbs(){
        return carbs;
    }
}
