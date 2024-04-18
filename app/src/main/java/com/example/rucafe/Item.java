package com.example.rucafe;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines the data structure of an item to be displayed in the RecyclerView
 * @author Anthony Paulino
 */
public class Item implements Serializable {
    private MenuItem item;
    private String tvLabel;
    private int image;
    private String price; // for demo purpose, the unitPrice is of String type

    /**
     * Parameterized constructor.
     *
     * @param item  The menu item.
     * @param image The image resource ID of the item.
     * @param price The price of the item.
     */
    public Item(MenuItem item, int image, String price, String tvLabel) {
        this.item = item;
        this.image = image;
        this.price = price;
        this.tvLabel = tvLabel;
    }

    /**
     * Getter method that returns the menu item.
     * @return The menu item.
     */
    public MenuItem getItem() {
        return item;
    }

    /**
     * Getter method that returns the image resource ID of the item.
     * @return The image resource ID of the item.
     */
    public int getImage() {
        return image;
    }

    /**
     * Getter method that returns the price of the item.
     * @return The price of the item.
     */
    public String getPrice() {
        return price;
    }

    public String getTVLabel() {
        return tvLabel;
    }

    /**
     * Method to retrieve the MenuItem object that has the specified tvLabel field.
     *
     * @param items    The list of items to search through.
     * @param tvLabel  The tvLabel to search for.
     * @return The MenuItem object with the specified tvLabel, or null if not found.
     */
    public static MenuItem getMenuItemByTVLabel(ArrayList<Item> items, String tvLabel) {
        for (Item item : items) {
            //System.out.println(item.toString());
            if (item.getTVLabel().equals(tvLabel)) {
                return item.getItem();
            }
        }
        return null; // Not found
    }
}

