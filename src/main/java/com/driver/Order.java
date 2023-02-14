package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String deliveryTimeString;

    public String getDeliveryTimeString() {
        return deliveryTimeString;
    }

    public void setDeliveryTimeString(String deliveryTimeString) {
        this.deliveryTimeString = deliveryTimeString;
    }

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        this.deliveryTimeString = deliveryTime;
        this.deliveryTime = deliveryTimeConvertor(deliveryTime);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    private int deliveryTimeConvertor(String deliveryTime){
        int mins = 0;
        for(int i = 0; i < 2; i++){
            mins += (int)((deliveryTime.charAt(deliveryTime.length()-1-i) - '0')*Math.pow(10, i));
        }
        int hours = 0;
        for(int i = 0; i < 2; i++){
            hours += (int)((deliveryTime.charAt(deliveryTime.length()-1-i-3) - '0')*Math.pow(10, i));
        }
        return hours*60+mins;
    }
}
