package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The main activity of the application. It serves as the entry point for the user interface
 * and provides navigation to 5 different parts of the application.
 * Thais is the Donut, Sandwich and Coffee menu, as well as the current order and all of the orders.
 *
 * @author Anthony Paulino
 */
public class MainActivity extends AppCompatActivity {
    private OrderManager orderManager;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and binding data to lists.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // "Singleton" design pattern
        orderManager = GlobalDataManager.getInstance().getOrderManager();
        // Initialize buttons for different menu activities
        ImageButton coffeeButton = findViewById(R.id.coffeeButton);
        ImageButton sandwichButton = findViewById(R.id.sandwichButton);
        ImageButton donutButton = findViewById(R.id.donutButton);
        ImageButton currentOrderButton = findViewById(R.id.currentOrderButton);
        ImageButton allOrdersButton = findViewById(R.id.allOrdersButton);
        // Set click listeners for navigation
        coffeeButton.setOnClickListener(view -> navigateTo(CoffeeMenuActivity.class));
        sandwichButton.setOnClickListener(view -> navigateTo(SandwichMenuActivity.class));
        donutButton.setOnClickListener(view -> navigateTo(DonutMenuActivity.class));
        currentOrderButton.setOnClickListener(view -> navigateTo(CurrentOrderActivity.class));
        allOrdersButton.setOnClickListener(view -> navigateTo(AllOrdersActivity.class));
    }

    /**
     * Navigates to the specified destination activity.
     *
     * @param destinationClass The class of the destination activity to navigate to.
     */
    private void navigateTo(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
    }
}
