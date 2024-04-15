package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersActivity extends AppCompatActivity {

    private OrderManager orderManager;
    private Spinner orderNumberSpinner;
    private ListView currentOrderList;
    private EditText totalAmountEditText;
    private Button cancelButton;
    private Button mainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorders);
        // Initialize UI components
        orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        currentOrderList = findViewById(R.id.currentOrderList);
        totalAmountEditText = findViewById(R.id.totalAmountEditText);
        cancelButton = findViewById(R.id.cancelOrderButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        // Get the OrderManager object
        orderManager = GlobalDataManager.getInstance().getOrderManager();
        // Populate the order number spinner
        updateState();
        // Set up event handlers
        cancelButton.setOnClickListener(v -> cancelOrder());
        mainMenuButton .setOnClickListener(v -> mainMenuClick());

        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displaySelectedOrder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }

    private void populateOrderNumbers() {
        List<Integer> orderNumbers = getAllOrderNumbers();
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNumbers);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(spinnerAdapter);
    }

    private void displaySelectedOrder() {
        int selectedOrderNumber = (int) orderNumberSpinner.getSelectedItem();
        Order selectedOrder = orderManager.getOrderFromNumber(selectedOrderNumber);
        if (selectedOrder != null) {
            ArrayAdapter<MenuItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedOrder.getItems());
            currentOrderList.setAdapter(adapter);
            updateTotalAmount(selectedOrder);
        }
    }

    private void cancelOrder() {
        if (orderNumberSpinner.getSelectedItem() != null) {
            int selectedOrderNumber = (int) orderNumberSpinner.getSelectedItem();
            // Get the order number as a string
            String orderNumberString = String.valueOf(selectedOrderNumber);
            // Create the confirmation message including the order number
            String confirmationMessage = "Are you sure you want to cancel order #" + orderNumberString + "?";
            // Create an AlertDialog to confirm cancellation
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cancel Order");
            builder.setMessage(confirmationMessage);
            // Add the "Yes" button, which cancels the order if clicked
            builder.setPositiveButton("Yes", (dialog, which) -> {
                orderManager.cancelOrder(selectedOrderNumber);
                // Refresh the order number spinner and clear the current order list
                populateOrderNumbers();
                currentOrderList.setAdapter(null);
                totalAmountEditText.setText("");
                showToast("Order canceled successfully.");
            });
            // Add the "No" button, which closes the dialog without canceling the order
            builder.setNegativeButton("No", (dialog, which) -> {
                // Do nothing, just close the dialog
            });
            // Create and show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            showToast("There is no selected order to cancel.");
        }
    }

    private List<Integer> getAllOrderNumbers() {
        List<Integer> orderNumbers = new ArrayList<>();
        List<Order> allOrders = orderManager.getAllOrders();
        for (Order order : allOrders) {
            orderNumbers.add(order.getOrderNumber());
        }
        return orderNumbers;

    }

    private void updateState() {
        populateOrderNumbers();
        preselectFirstOrderNumber();
    }

    private void updateTotalAmount(Order selectedOrder) {
        double totalAmount = (selectedOrder != null) ? OrderManager.getTotalAmount(selectedOrder) : 0.00;
        totalAmountEditText.setText((selectedOrder != null) ? getString(R.string.total_amount, totalAmount) : "");
    }

    private boolean isSpinnerEmpty(Spinner spinner) {
        return spinner.getAdapter() == null || spinner.getAdapter().getCount() == 0;
    }

    private void preselectFirstOrderNumber() {
        if (!isSpinnerEmpty(orderNumberSpinner)) {
            orderNumberSpinner.setSelection(0);
            updateTotalAmount(orderManager.getOrderFromNumber((int) orderNumberSpinner.getSelectedItem()));
        } else {
            // If the spinner is empty, set total amount to 0.00
            updateTotalAmount(null);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void mainMenuClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

