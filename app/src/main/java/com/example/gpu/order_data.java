package com.example.gpu;

import android.graphics.Bitmap;

public class order_data {


    Bitmap ima;
    String ordername,descrpition,orderquantity,orderprice,totalprice;

    public order_data(Bitmap ima, String ordername, String descrpition, String orderquantity, String orderprice, String totalprice) {
        this.ima = ima;
        this.ordername = ordername;
        this.descrpition = descrpition;
        this.orderquantity = orderquantity;
        this.orderprice = orderprice;
        this.totalprice = totalprice;
    }


}

