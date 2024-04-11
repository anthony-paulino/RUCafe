package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // "Singleton" design pattern
    private OrderManager orderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderManager = GlobalDataManager.getInstance().getOrderManager();
        ImageButton coffeeButton = findViewById(R.id.coffeeButton);
        ImageButton currentOrderButton = findViewById(R.id.currentOrderButton);
        ImageButton allOrdersButton = findViewById(R.id.allOrdersButton);

        coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(CoffeeMenuActivity.class);
            }
        });

        currentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(CurrentOrderActivity.class);
            }
        });
        allOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(AllOrdersActivity.class);
            }
        });

    }

    private void navigateTo(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
    }
}