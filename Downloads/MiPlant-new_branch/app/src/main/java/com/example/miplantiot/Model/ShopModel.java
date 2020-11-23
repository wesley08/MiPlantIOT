package com.example.miplantiot.Model;

public class ShopModel {
    String  Category,FirstRow,FourthRow,ThirdRow,SecondRow,HeightOfPlant,Image,NameItem, IDItem;
    int Price, Weight;

    public ShopModel(String category, String firstRow, String fourthRow, String thirdRow, String secondRow, String heightOfPlant, String image, String nameItem, String IDItem, int price, int weight) {
        Category = category;
        FirstRow = firstRow;
        FourthRow = fourthRow;
        ThirdRow = thirdRow;
        SecondRow = secondRow;
        HeightOfPlant = heightOfPlant;
        Image = image;
        NameItem = nameItem;
        this.IDItem = IDItem;
        Price = price;
        Weight = weight;
    }

    public ShopModel(int price, String category, String firstRow, String fourthRow, String thirdRow, String secondRow, String heightOfPlant, String image, String nameItem, String idItem) {
        Price = price;
        Category = category;
        FirstRow = firstRow;
        FourthRow = fourthRow;
        ThirdRow = thirdRow;
        SecondRow = secondRow;
        HeightOfPlant = heightOfPlant;
        Image = image;
        NameItem = nameItem;
        IDItem = idItem;
    }

    public ShopModel() {
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public String getIDItem() {
        return IDItem;
    }

    public void setIDItem(String IDItem) {
        this.IDItem = IDItem;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getFirstRow() {
        return FirstRow;
    }

    public void setFirstRow(String firstRow) {
        FirstRow = firstRow;
    }

    public String getFourthRow() {
        return FourthRow;
    }

    public void setFourthRow(String fourthRow) {
        FourthRow = fourthRow;
    }

    public String getThirdRow() {
        return ThirdRow;
    }

    public void setThirdRow(String thirdRow) {
        ThirdRow = thirdRow;
    }

    public String getSecondRow() {
        return SecondRow;
    }

    public void setSecondRow(String secondRow) {
        SecondRow = secondRow;
    }

    public String getHeightOfPlant() {
        return HeightOfPlant;
    }

    public void setHeightOfPlant(String heightOfPlant) {
        HeightOfPlant = heightOfPlant;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNameItem() {
        return NameItem;
    }

    public void setNameItem(String nameItem) {
        NameItem = nameItem;
    }
}
