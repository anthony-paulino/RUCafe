package com.example.rucafe;

public class GlobalDataManager {
    private static final OrderManager orderManager = new OrderManager();
    private GlobalDataManager() {
    }
    public static OrderManager getOrderManager() {
        return orderManager;
    }


}
