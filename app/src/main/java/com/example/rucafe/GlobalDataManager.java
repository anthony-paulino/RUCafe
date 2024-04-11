package com.example.rucafe;

import androidx.databinding.ObservableArrayList;

public class GlobalDataManager {
    private static GlobalDataManager instance;
    private OrderManager orderManager;
    private ObservableArrayList<MenuItem> currentOrderItems;


    private GlobalDataManager() {
        orderManager = new OrderManager();
    }

    public static synchronized GlobalDataManager getInstance() {
        if (instance == null) {
            instance = new GlobalDataManager();
        }
        return instance;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }
}