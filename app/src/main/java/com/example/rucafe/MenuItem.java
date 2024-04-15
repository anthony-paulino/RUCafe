package com.example.rucafe;

import androidx.annotation.NonNull;

/**
 * Represents a generic menu item in the cafe.
 * This class serves as the base class for specific menu item types
 * such as Coffee, Donut, and Sandwich.
 *
 * @author Anthony Paulino
 */
public abstract class MenuItem {
    private String name;

    /**
     * Constructs a new MenuItem object with the specified name.
     *
     * @param name The name of the menu item.
     */
    public MenuItem(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the menu item.
     *
     * @return The name of the menu item.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the price of the menu item.
     *
     * @return The price of the menu item.
     */
    public double getPrice() {
        return price();
    }

    /**
     * Calculates the price of the menu item.
     *
     * @return The price of the menu item.
     */
    public abstract double price();

    /**
     * Returns a string representation of the MenuItem object.
     *
     * @return A string representing the MenuItem object, which is its name.
     */
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
