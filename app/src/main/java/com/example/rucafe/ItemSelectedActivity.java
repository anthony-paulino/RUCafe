package com.example.rucafe;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.rucafe.GlobalDataManager;

public class ItemSelectedActivity extends AppCompatActivity {
    private Button btn_itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);
        btn_itemName = findViewById(R.id.btn1);
        Intent intent = getIntent();
        btn_itemName.setText(intent.getStringExtra("ITEM"));

    }
}