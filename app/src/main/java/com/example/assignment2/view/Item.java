package com.example.assignment2.view;

public class Item {


    String itemImg,itemName, itemYear;

    public Item(){}


    public Item(String itemImg, String itemName,String itemYear) {
        this.itemImg = itemImg;
        this.itemName = itemName;
        this.itemYear = itemYear;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getItemYear() {
        return itemYear;
    }

    public void setItemYear(String itemYear) {
        this.itemYear = itemYear;
    }
}
