package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // "Singleton" design pattern
    private OrderManager orderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderManager = GlobalDataManager.getInstance().getOrderManager();
        ImageButton coffeeButton = findViewById(R.id.coffeeButton);
        ImageButton sandwichButton = findViewById(R.id.sandwichButton);
        ImageButton donutButton = findViewById(R.id.donutButton);
        ImageButton currentOrderButton = findViewById(R.id.currentOrderButton);
        ImageButton allOrdersButton = findViewById(R.id.allOrdersButton);

        coffeeButton.setOnClickListener(view -> navigateTo(CoffeeMenuActivity.class));
        sandwichButton.setOnClickListener(view -> navigateTo(SandwichMenuActivity.class));
        donutButton.setOnClickListener(view -> navigateTo(DonutMenuActivity.class));

        currentOrderButton.setOnClickListener(view -> navigateTo(CurrentOrderActivity.class));
        allOrdersButton.setOnClickListener(view -> navigateTo(AllOrdersActivity.class));

    }

    private void navigateTo(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
    }
}