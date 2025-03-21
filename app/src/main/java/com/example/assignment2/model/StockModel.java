package com.example.assignment2.model;

public class StockModel {

    private String name;
    private String usdPrice;
    private String jpyPrice;
    private String euroPrice;

    public StockModel(){}

    public StockModel(String name, String usdPrice) {
        this.name = name;
        this.usdPrice = usdPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsdPrice() {
        return usdPrice;
    }

    public void setUsdPrice(String usdPrice) {
        this.usdPrice = usdPrice;
    }

    public String getJpyPrice() {
        return jpyPrice;
    }

    public void setJpyPrice(String jpyPrice) {
        this.jpyPrice = jpyPrice;
    }

    public String getEuroPrice() {
        return euroPrice;
    }

    public void setEuroPrice(String euroPrice) {
        this.euroPrice = euroPrice;
    }
}
