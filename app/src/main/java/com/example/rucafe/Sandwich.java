package com.example.rucafe;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Represents a sandwich item in the cafe menu.
 * This class extends the MenuItem class and provides functionality
 * to calculate the price based on the type of sandwich and add-ons.
 *
 * @author Anthony Paulino
 */
public class Sandwich extends MenuItem {
    private String bread;
    private String protein;
    private List<String> addOns;
    private int quantity;
    private static final double VEGGIE_COST = 0.30;
    private static final double CHEESE_COST = 1;
    private static final double BEEF_PRICE = 10.99;
    private static final double FISH_PRICE = 9.99;
    private static final double CHICKEN_PRICE = 8.99;
    private static final double INVALID = 0.0;

    /**
     * Constructs a new Sandwich object with the specified bread, protein, add-ons, and quantity.
     *
     * @param bread The type of bread used in the sandwich.
     * @param protein The protein used in the sandwich.
     * @param addOns The list of add-ons included in the sandwich.
     * @param quantity The quantity of sandwich items.
     */
    public Sandwich(String bread, String protein, List<String> addOns, int quantity) {
        super("Sandwich");
        this.bread = bread;
        this.protein = protein;
        this.addOns = addOns;
        this.quantity = quantity;
    }


    /**
     * Calculates the price of the sandwich item based on its protein, add-ons, and quantity.
     *
     * @return The total price of the sandwich item.
     */
    @Override
    public double price() {
        // Define base prices for each protein option
        double basePrice;
        switch (protein.toLowerCase()) {
            case "beef":
                basePrice = BEEF_PRICE;
                break;
            case "chicken":
                basePrice = CHICKEN_PRICE;
                break;
            case "fish":
                basePrice = FISH_PRICE;
                break;
            default:
                basePrice = INVALID; // Default to 0 if protein is invalid (should never happen)
        }
        // Add extra cost for cheese
        double addOnsCost = addOns.contains("Cheese") ? CHEESE_COST : INVALID;
        // Add extra cost for each veggie add-on
        addOnsCost += addOns.stream().filter(addOn -> !addOn.equals("Cheese")).count() * VEGGIE_COST;
        return (basePrice + addOnsCost) * quantity; // Total price for the specified quantity
    }

    /**
     * Returns a string representation of the Sandwich object.
     *
     * @return A string representing the Sandwich object, including bread, protein, add-ons, and quantity.
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sandwich - [Bread: ").append(bread).append(", Protein: ").append(protein);
        if (!(addOns == null) && !addOns.isEmpty()) {
            stringBuilder.append(", Add-ins: ");
            for (String addOn : addOns) {
                stringBuilder.append("(" + addOn + ")");
            }
        }
        stringBuilder.append(", Quantity: ").append(quantity).append("]");
        return stringBuilder.toString();
    }
}
