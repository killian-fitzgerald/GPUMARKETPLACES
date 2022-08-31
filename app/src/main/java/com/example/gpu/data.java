package com.example.gpu;

public class data {

    String image ,description,price,name,discount,key;

    data() {}

    public data(String image, String description, String price, String name, String discount) {
        this.image = image;
        this.description = description;
        this.price = price;
        this.name = name;
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }
    public String getDescription() {
        return description;
    }
    public String getDiscount() {
        return discount;
    }

    public String getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
