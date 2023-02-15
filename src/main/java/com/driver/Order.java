package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
//    private String deliveryTimeString;

//    public String getDeliveryTimeString() {
//        return deliveryTimeString;
//    }
//
//    public void setDeliveryTimeString(String deliveryTimeString) {
//        this.deliveryTimeString = deliveryTimeString;
//    }

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
//        this.deliveryTimeString = deliveryTime;
        this.deliveryTime = Integer.parseInt(deliveryTime.substring(0,2))*60 + Integer.parseInt(deliveryTime.substring(3, 5));
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
