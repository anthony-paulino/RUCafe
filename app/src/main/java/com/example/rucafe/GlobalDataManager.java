package com.example.rucafe;

import java.util.ArrayList;
import java.util.HashMap;

public class GlobalDataManager {
    private static GlobalDataManager instance;
    private OrderManager orderManager;
    private ArrayList<Item> donutItems;

    private HashMap<String, Integer> donutImageMap;

    private GlobalDataManager() {
        orderManager = new OrderManager();
        initializeDonutImageMap();
        donutItems = new ArrayList<>();
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

    public int getDonutImageResource(String donutName) {
        if (donutImageMap.containsKey(donutName)) {
            return donutImageMap.get(donutName);
        } else {
            // Return a default image resource if the donut name is not found
            return -1;
        }
    }

    public ArrayList<Item> getDonutItems() {
        return donutItems;
    }

    public void resetDonutItems(){
        donutItems = new ArrayList<>();
    }

    private void initializeDonutImageMap() {
        donutImageMap = new HashMap<>();
        // Populate the HashMap with donut names and their corresponding image resources
        donutImageMap.put("Yeast Donut: Chocolate Frosted", R.drawable.yeast_chocolatefrosted);
        donutImageMap.put("Yeast Donut: Glazed", R.drawable.yeast_glazed);
        donutImageMap.put("Yeast Donut: Jelly", R.drawable.yeast_jelly);
        donutImageMap.put("Yeast Donut: Lemon Filled", R.drawable.yeast_lemonfilled);
        donutImageMap.put("Yeast Donut: Strawberry Frosted", R.drawable.yeast_strawberryfrosted);
        donutImageMap.put("Yeast Donut: Sugar", R.drawable.yeast_sugar);
        donutImageMap.put("Cake Donut: Blueberry", R.drawable.cake_blueberry);
        donutImageMap.put("Cake Donut: Cinnamon", R.drawable.cake_cinamon);
        donutImageMap.put("Cake Donut: Old Fashion", R.drawable.cake_oldfashion);
        donutImageMap.put("Donut Holes: Cinnamon", R.drawable.hole_cinnamon);
        donutImageMap.put("Donut Holes: Glazed", R.drawable.hole_glazed);
        donutImageMap.put("Donut Holes: Jelly", R.drawable.hole_jelly);
    }
}
