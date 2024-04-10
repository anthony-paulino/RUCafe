package com.example.rucafe;

/**
 * Represents a donut item in the cafe menu.
 * This class extends the MenuItem class and provides functionality
 * to calculate the price based on the type of donut.
 *
 * @author Anthony Paulino
 */
public class Donut extends MenuItem {
    private String type;
    private String flavor;
    private int quantity;
    private static final double YEAST_PRICE = 1.79;
    private static final double CAKE_PRICE = 1.89;
    private static final double HOLE_PRICE = 0.39;
    private static final double INVALID = 0.0;

    /**
     * Parameterized constructor, constructs a new Donut object with the specified type, flavor, and quantity.
     *
     * @param type     The type of the donut.
     * @param flavor   The flavor of the donut.
     * @param quantity The quantity of donut items.
     */
    public Donut(String type, String flavor, int quantity) {
        super("Donut");
        this.type = type;
        this.flavor = flavor;
        this.quantity = quantity;
    }

    /**
     * Gets the type of the donut.
     *
     * @return The type of the donut.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the flavor of the donut.
     *
     * @return The flavor of the donut.
     */

    public String getFlavor() {
        return flavor;
    }

    /**
     * Calculates the price of the donut item based on its type and quantity.
     *
     * @return The total price of the donut item.
     */
    @Override
    public double price() {
        // Calculate the price based on the type of donut
        double price;
        switch (type.toLowerCase()) {
            case "yeast donuts":
                price = YEAST_PRICE;
                break;
            case "cake donuts":
                price = CAKE_PRICE;
                break;
            case "donut holes":
                price = HOLE_PRICE;
                break;
            default:
                price = INVALID; // Default to 0 if type is invalid (should never happen)
        }
        return price * quantity; // Total price for the specified quantity
    }


    /**
     * Returns a string representation of the Donut object.
     *
     * @return A string representing the Donut object, including type, flavor, and quantity.
     */
    @Override
    public String toString() {
        return "Donut - [Type: " + type + ", Flavor: " + flavor + ", Quantity: " + quantity + "]";
    }
}
