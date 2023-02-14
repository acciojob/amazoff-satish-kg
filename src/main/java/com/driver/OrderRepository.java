package com.driver;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Repository
public class OrderRepository {

    HashMap<String, String> orderPartnerMap;
    HashMap<String, Order> unassignedOrderMap;
    HashMap<String, Order> orderMap;
//    HashMap<String, String> orderTimeStringMap;
    HashMap<String, DeliveryPartner> deliveryPartnerHashMap;
    HashMap<String, List<String>> deliveryPartnerOrderListMap;

//    have to create a hashmap with (OrderId, order) -> then reprogram functions accordingly

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.orderPartnerMap = new HashMap<String, String>();
        this.unassignedOrderMap = new HashMap<String, Order>();
//        this.orderTimeStringMap = new HashMap<String, String>();
        this.deliveryPartnerHashMap = new HashMap<String, DeliveryPartner>();
        this.deliveryPartnerOrderListMap = new HashMap<String, List<String>>();
    }

    public boolean isNewOrder(Order order){
        if(orderMap.containsKey(order.getId())) return false;
//        if(orderPartnerMap.containsKey(order)) return false;
        return true;
    }

    public void addOrder(Order order){
        orderMap.put(order.getId(), order);
        unassignedOrderMap.put(order.getId(), order);
//        orderPartnerMap.put(order, null);
    }

    public boolean isNewDeliveryPartner(String partnerId){
        if(deliveryPartnerHashMap.containsKey(partnerId)) return false;
        return true;
    }

    public void addDeliveryPartner(String partnerId){
        deliveryPartnerHashMap.put(partnerId, new DeliveryPartner(partnerId));
        deliveryPartnerOrderListMap.put(partnerId, new ArrayList<String>(Arrays.asList()));
    }

    public boolean isUnassignedOrder(String orderId){
        if(unassignedOrderMap.containsKey(orderId)) return true;
        return false;
    }

    public void addOrderPartnerPair(String orderId, String partnerId){
        orderPartnerMap.put(orderId, partnerId);
        unassignedOrderMap.remove(orderId);
        int numOfOrders = deliveryPartnerHashMap.get(partnerId).getNumberOfOrders();
        deliveryPartnerHashMap.get(partnerId).setNumberOfOrders(numOfOrders+1);
        deliveryPartnerOrderListMap.get(partnerId).add(orderId);
    }

    public Order getOrderById(String orderId){
        return orderMap.get(orderId);
    }

    public DeliveryPartner getDeliveryPartnerById(String partnerId){
        return deliveryPartnerHashMap.get(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        return deliveryPartnerOrderListMap.get(partnerId);
    }

    public List<String> getAllOrders(){
        List<String> list = new ArrayList<String>(orderMap.keySet());
        return list;
    }

    public int getCountOfUnassignedOrders(){
        return unassignedOrderMap.size();
    }

    public void deletePartnerById(String partnerId){
        deliveryPartnerHashMap.remove(partnerId);
        List<String> list = deliveryPartnerOrderListMap.get(partnerId);
        for(String orderId : list){
            orderPartnerMap.put(orderId, null);
            unassignedOrderMap.put(orderId, orderMap.get(orderId));
        }
        deliveryPartnerOrderListMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId){
        orderMap.remove(orderId);
        if(unassignedOrderMap.containsKey(orderId)) unassignedOrderMap.remove(orderId);
        else{
            String deliveryPartnerId = orderPartnerMap.get(orderId);
            orderPartnerMap.remove(orderId);
            List<String> list = deliveryPartnerOrderListMap.get(deliveryPartnerId);
            for(String order : list){
                if(order.equals(orderId)) list.remove(orderId);
            }
            deliveryPartnerOrderListMap.put(deliveryPartnerId, list);
        }
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int timeInt = timeStringToTimeIntConvertor(time);
        int undeliveredOrders = 0;
        List<String> list = deliveryPartnerOrderListMap.get(partnerId);
        for(String orderId : list){
            if(orderMap.get(orderId).getDeliveryTime()>timeInt) undeliveredOrders++;
        }
        return undeliveredOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int lastDeliveryTime = Integer.MIN_VALUE;
        int time = 0;
        String lastDeliveryTimeString = "";
        List<String> list = deliveryPartnerOrderListMap.get(partnerId);
        for(String orderId : list){
            time = orderMap.get(orderId).getDeliveryTime();
            if(time > lastDeliveryTime) {
                lastDeliveryTime = time;
            }
        }
        return intToString(lastDeliveryTime);
    }

    public String intToString(int intTime){
        String stringTime = "";
        int hours = intTime/60;
        if(hours<10){
            stringTime = stringTime+"0"+hours;
        }else{
            stringTime = stringTime+hours;
        }
        stringTime = stringTime+":";
        intTime = intTime % 60;
        if(intTime < 10){
            stringTime = stringTime+"0"+intTime;
        }else{
            stringTime = stringTime+intTime;
        }
        return stringTime;
    }

    public int timeStringToTimeIntConvertor(String time){
        int mins = 0;
        for(int i = 0; i < 2; i++){
            mins += (int)((time.charAt(time.length()-1-i) - '0')*Math.pow(10, i));
        }
//        System.out.println(mins);
        int hours = 0;
        for(int i = 0; i < 2; i++){
            hours += (int)((time.charAt(time.length()-1-i-3) - '0')*Math.pow(10, i));
        }
//        System.out.println(hours);
        return hours*60+mins;
    }

}
