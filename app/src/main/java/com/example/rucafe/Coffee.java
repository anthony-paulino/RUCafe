package com.example.rucafe;

import java.util.List;

/**
 * The Coffee class represents a coffee item in the cafe menu.
 * It extends the MenuItem class and provides functionality to calculate the price
 * based on the cup size and additional ingredients.
 *
 * @author Anthony Paulino
 */
public class Coffee extends MenuItem {
    private String cupSize;
    private List<String> addIns;
    private int quantity;
    private static final double ADD_IN_COST = 0.30;
    private static final double SHORT_PRICE = 1.99;
    private static final double TALL_PRICE = 2.49;
    private static final double GRANDE_PRICE = 2.99;
    private static final double VENTI_PRICE = 3.49;
    private static final double INVALID = 0.0;


    /**
     * Parameterized constructor, constructs a new Coffee object with the specified cup size, additional ingredients, and quantity.
     *
     * @param cupSize  The size of the coffee.
     * @param addIns   The list of additional ingredients.
     * @param quantity The quantity of coffee items.
     */
    public Coffee(String cupSize, List<String> addIns, int quantity) {
        super("Coffee");
        this.cupSize = cupSize;
        this.addIns = addIns;
        this.quantity = quantity;
    }

    /**
     * Gets the cup size of the coffee.
     *
     * @return The cup size of the coffee.
     */
    public String getCupSize() {
        return cupSize;
    }


    /**
     * Gets the list of additional ingredients added to the coffee.
     *
     * @return The list of additional ingredients.
     */
    public List<String> getAddIns() {
        return addIns;
    }

    /**
     * Gets the quantity of coffee items.
     *
     * @return The quantity of coffee items.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the price of the coffee item based on its type, add ins, and quantity.
     *
     * @return The total price of the donut item.
     */
    @Override
    public double price() {
        // Calculate the base price of the coffee based on cup size
        double basePrice;
        switch (cupSize.toLowerCase()) {
            case "short":
                basePrice = SHORT_PRICE;
                break;
            case "tall":
                basePrice = TALL_PRICE;
                break;
            case "grande":
                basePrice = GRANDE_PRICE;
                break;
            case "venti":
                basePrice = VENTI_PRICE;
                break;
            default:
                basePrice = INVALID; // Default to 0 if cup size is invalid (should never happen)
        }
        // Add extra cost for each add-in
        double addInsCost = addIns.size() * ADD_IN_COST;
        return (basePrice + addInsCost) * quantity; // Total price for the specified quantity
    }

    /**
     * Returns a string representation of the Coffee object.
     *
     * @return A string representing the Coffee object, including cup size, additional ingredients, and quantity.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Coffee - [Size: ").append(cupSize);
        if (!(addIns == null) && !addIns.isEmpty()) {
            stringBuilder.append(", Add-ins: ");
            for (String addIn : addIns) {
                stringBuilder.append("(" + addIn + ")");
            }
        }
        stringBuilder.append(", Quantity: ").append(quantity).append("]");
        return stringBuilder.toString();
    }
}


