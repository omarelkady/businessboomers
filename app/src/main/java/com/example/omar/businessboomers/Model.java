package com.example.omar.businessboomers;

/**
 * Created by Omar on 3/1/2018.
 */

public class Model {
    int id;
    String productName;
    String imageUrl;
    int price;

    public Model(){

    }
    public Model(int id, String productName, String imageUrl, int price){
        super();
        this.id = id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
