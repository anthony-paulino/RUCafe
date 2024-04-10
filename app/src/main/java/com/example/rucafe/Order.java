package com.example.rucafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order placed in the cafe, consisting of multiple menu items and an order number.
 *
 * @author Anthony Paulino
 */
public class Order {
    private int orderNumber;
    private List<MenuItem> items;

    /**
     * Constructs a new Order object with the specified order number and list of items.
     *
     * @param orderNumber The order number.
     * @param items       The list of menu items in the order.
     */
    public Order(int orderNumber, List<MenuItem> items) {
        this.orderNumber = orderNumber;
        this.items = items;
    }

    /**
     * Gets the order number.
     *
     * @return The order number.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Gets the list of menu items in the order.
     *
     * @return The list of menu items in the order.
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * Returns a string representation of the Order object.
     *
     * @return A string representing the Order object, which is a string representation of its list of items.
     */
    @Override
    public String toString() {
        return items.toString();
    }

}