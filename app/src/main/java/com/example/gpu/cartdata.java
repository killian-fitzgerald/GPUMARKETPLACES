package com.example.gpu;

public class cartdata {

    String imagecart,namecart,quantitycart,pricecart,totalpricecart,description,key,datetime;


    public cartdata(){}

    public cartdata(String imagecart, String namecart, String quantitycart, String pricecart, String totalpricecart, String description ,String datetime) {
        this.imagecart = imagecart;
        this.namecart = namecart;
        this.quantitycart = quantitycart;
        this.pricecart = pricecart;
        this.totalpricecart = totalpricecart;
        this.description = description;
        this.datetime =datetime;
    }

    public String getDatetime() {
        return datetime;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getImagecart() {
        return imagecart;
    }

    public void setImagecart(String imagecart) {
        this.imagecart = imagecart;
    }

    public String getNamecart() {
        return namecart;
    }

    public void setNamecart(String namecart) {
        this.namecart = namecart;
    }
    public String getQuantitycart() {
        return quantitycart;
    }

    public void setQuantitycart(String quantitycart) {
        this.quantitycart = quantitycart;
    }

    public String getPricecart() {
        return pricecart;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPricecart(String pricecart) {
        this.pricecart = pricecart;
    }
    public String getTotalpricecart() {
        return totalpricecart;
    }
    public void setTotalpricecart(String totalpricecart) {
        this.totalpricecart = totalpricecart;
    }
}
