package com.example.rucafe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The OrderManager class manages orders within the cafe system. It handles operations such as adding, removing, and placing orders,
 * as well as calculating prices and saving orders to a file.
 * It maintains the current order, potential order, and a list of all orders placed. The class also provides methods for
 * calculating subtotal, sales tax, and total amount for an order.
 * <p>OrderManager allows users to manipulate orders by adding or removing items from the current order, moving items between
 * the current and potential orders, and placing orders.
 *
 * @author Anthony Paulino
 */
public class OrderManager {
    private int orderNumber = 1;
    private Order currentOrder = new Order(orderNumber, new ArrayList<>());
    private Order potentialOrder = new Order(orderNumber, new ArrayList<>());
    private List<Order> allOrders = new ArrayList<>();
    private static final double SALES_TAX_RATE = 0.06625;

    /**
     * Constructor method, constructs an orderManager object.
     */
    public OrderManager() {
    }

    /**
     * Moves items from the potential order to the current order.
     */
    public void setCurrentToPotential() {
        List<MenuItem> items = currentOrder.getItems();
        items.addAll(potentialOrder.getItems());
        potentialOrder = new Order(orderNumber, new ArrayList<>());
    }

    /**
     * Adds a menu item to the current order.
     *
     * @param menuItem The menu item to be added.
     */
    public void addToCurrentOrder(MenuItem menuItem) {
        currentOrder.getItems().add(menuItem);
    }

    /**
     * Removes a menu item from the current order.
     *
     * @param menuItem The menu item to be removed.
     */
    public void removeFromCurrentOrder(MenuItem menuItem) {
        currentOrder.getItems().remove(menuItem);
    }

    /**
     * Adds a menu item to the potential order.
     *
     * @param menuItem The menu item to be added.
     */
    public void addToPotentialOrder(MenuItem menuItem) {
        potentialOrder.getItems().add(menuItem);
    }

    /**
     * Removes a menu item from the potential order.
     *
     * @param menuItem The menu item to be removed.
     */
    public void removeFromPotentialOrder(MenuItem menuItem) {
        potentialOrder.getItems().remove(menuItem);
    }

    /**
     * Places the current order, adding it to the list of all orders.
     */
    public void placeCurrentOrder() {
        allOrders.add(currentOrder);
        orderNumber++;
        currentOrder = new Order(orderNumber, new ArrayList<>());
    }


    /**
     * Retrieves an order by its order number.
     *
     * @param orderNumber The order number to search for.
     * @return The order with the specified order number, or null if not found.
     */
    public Order getOrderFromNumber(int orderNumber) {
        for (Order order : allOrders) {
            if (order.getOrderNumber() == (orderNumber)) {
                return order;
            }
        }
        return null; // Return null if the order number is not found
    }

    /**
     * Retrieves a list of all orders placed.
     *
     * @return The list of all orders.
     */
    public List<Order> getAllOrders() {
        return allOrders;
    }

    /**
     * Retrieves the current order.
     *
     * @return The current order.
     */

    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Retrieves the potential order.
     *
     * @return The potential order.
     */
    public Order getPotentialOrder() {
        return potentialOrder;
    }

    /**
     * Cancels an order by removing it from the list of all orders.
     *
     * @param orderNumber The order number of the order to cancel.
     */
    public void cancelOrder(int orderNumber) {
        for (Order order : allOrders) {
            if (order.getOrderNumber() == orderNumber) {
                allOrders.remove(order);
                break;
            }
        }
    }

    /**
     * Calculates the subtotal for an order.
     *
     * @param order The order for which to calculate the subtotal.
     * @return The subtotal of the order.
     */
    public static double calculateSubtotal(Order order) {
        double subtotal = 0;
        for (MenuItem item : order.getItems()) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }

    /**
     * Calculates the sales tax for a given subtotal.
     *
     * @param subtotal The subtotal for which to calculate the sales tax.
     * @return The sales tax amount.
     */
    public static double calculateSalesTax(double subtotal) {
        return subtotal * SALES_TAX_RATE;
    }

    /**
     * Calculates the total amount for an order including sales tax.
     *
     * @param subtotal The subtotal of the order.
     * @param salesTax The sales tax amount.
     * @return The total amount of the order including sales tax.
     */
    public static double calculateTotalAmount(double subtotal, double salesTax) {
        return subtotal + salesTax;
    }

    /**
     * Retrieves the total amount for an order including sales tax.
     *
     * @param order The order for which to calculate the total amount.
     * @return The total amount of the order including sales tax.
     */
    public static double getTotalAmount(Order order) {
        double subtotal = calculateSubtotal(order);
        double salesTax = calculateSalesTax(subtotal);
        double totalAmount = calculateTotalAmount(subtotal, salesTax);
        return totalAmount;
    }

    /**
     * Filters coffee items from the current order.
     *
     * @param currentOrder The current order from which to filter coffee items.
     * @return A list of coffee items in the current order.
     */
    public List<Coffee> filterCoffeeItems(Order currentOrder) {
        return currentOrder.getItems().stream()
                .filter(item -> item instanceof Coffee)
                .map(item -> (Coffee) item)
                .collect(Collectors.toList());
    }

    /**
     * Filters donut items from the current order.
     *
     * @param currentOrder The current order from which to filter donut items.
     * @return A list of donut items in the current order.
     */
    public List<Donut> filterDonutItems(Order currentOrder) {
        return currentOrder.getItems().stream()
                .filter(item -> item instanceof Donut)
                .map(item -> (Donut) item)
                .collect(Collectors.toList());
    }

    /**
     * Filters sandwich items from the current order.
     *
     * @param currentOrder The current order from which to filter sandwich items.
     * @return A list of sandwich items in the current order.
     */
    public List<Sandwich> filterSandwichItems(Order currentOrder) {
        return currentOrder.getItems().stream()
                .filter(item -> item instanceof Sandwich)
                .map(item -> (Sandwich) item)
                .collect(Collectors.toList());
    }
}
