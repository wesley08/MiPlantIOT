package com.example.miplantiot.Model;

public class CartModel {

    private String Category,NameProduct, Status, UrlImage,KeyPush;
    private int Price, Qty, Weight;

    public CartModel(String category, String nameProduct, String status, String urlImage, String keyPush, int price, int qty, int weight) {
        Category = category;
        NameProduct = nameProduct;
        Status = status;
        UrlImage = urlImage;
        KeyPush = keyPush;
        Price = price;
        Qty = qty;
        Weight = weight;
    }

    public CartModel() {

    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUrlImage() {
        return UrlImage;
    }

    public void setUrlImage(String urlImage) {
        UrlImage = urlImage;
    }

    public String getKeyPush() {
        return KeyPush;
    }

    public void setKeyPush(String keyPush) {
        KeyPush = keyPush;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
