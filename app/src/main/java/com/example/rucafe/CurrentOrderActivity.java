package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;

/**
 * Activity displaying the current order and allowing modifications.
 *
 * @author Anthony Paulino
 */
public class CurrentOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private OrderManager orderManager;
    private ObservableArrayList<MenuItem> currentOrderItems;
    private ListView currentOrderList;
    private EditText subTotalTextField;
    private EditText salesTaxTextField;
    private EditText totalAmountTextField;
    private ArrayAdapter<MenuItem> adapter;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentorder);
        // Initialize UI components
        currentOrderList = findViewById(R.id.currentOrderList);
        subTotalTextField = findViewById(R.id.currentOrderSubTotal);
        salesTaxTextField = findViewById(R.id.salesTaxEditText);
        totalAmountTextField = findViewById(R.id.totalAmountEditText);
        // Get the OrderManager object
        orderManager = GlobalDataManager.getInstance().getOrderManager();
        // Initialize the list view with the current order
        currentOrderItems = new ObservableArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentOrderItems);
        currentOrderList.setAdapter(adapter);
        // Set item click listener
        currentOrderList.setOnItemClickListener(this);
        // Update subtotal, sales tax, and total amount text fields, ListView
        updateView();
        // Set up place order button click listener
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        placeOrderButton.setOnClickListener(v -> placeOrder());
        // set up main menu button
        findViewById(R.id.mainMenuButton).setOnClickListener(v -> mainMenuClick());
    }

    /**
     * Update and refresh the view including the current order list, subtotal, sales tax, and total amount.
     */
    private void updateView() {
        updateObservableList();
        updateTotals();
        adapter.notifyDataSetChanged();
    }

    /**
     * Update and set the observable list and fill it with the current order items.
     */
    private void updateObservableList() {
        currentOrderItems.clear();
        currentOrderItems.addAll(orderManager.getCurrentOrder().getItems());
    }

    /**
     * Update the subtotal, sales tax, and total amount text fields based on the current order.
     */
    private void updateTotals() {
        double subtotal = OrderManager.calculateSubtotal(orderManager.getCurrentOrder());
        double salesTax = OrderManager.calculateSalesTax(subtotal);
        double totalAmount = OrderManager.calculateTotalAmount(subtotal, salesTax);
        subTotalTextField.setText(getString(R.string.subTotalText, subtotal));
        salesTaxTextField.setText(getString(R.string.sales_tax, salesTax));
        totalAmountTextField.setText(getString(R.string.total_amount, totalAmount));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Item");
        builder.setMessage("Are you sure you want to remove this item?");
        // Add the "Yes" button, which removes the item if clicked
        builder.setPositiveButton("Yes", (dialog, which) -> {
            MenuItem selectedItem = currentOrderItems.get(position);
            orderManager.removeFromCurrentOrder(selectedItem);
            updateView(); // Update the view after removing the item
        });
        // Add the "No" button, which closes the dialog without doing anything
        builder.setNegativeButton("No", (dialog, which) -> {
            // Do nothing, just close the dialog
        });
        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Place the current order, show a toast for successfully placing the order or if the current order is empty.
     */
    private void placeOrder() {
        if (orderManager.getCurrentOrder().getItems().isEmpty()) {
            showToast("Order is empty.");
        } else {
            orderManager.placeCurrentOrder();
            showToast(getString(R.string.order_placed));
        }
        updateView();
    }

    /**
     * Display a toast message.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handle the click event from the main menu button, it navigates back to the main menu.
     */
    public void mainMenuClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}