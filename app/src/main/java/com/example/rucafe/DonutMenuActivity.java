package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Activity displaying the donut menu and allowing users to select items.
 *
 * @author Anthony Paulino
 */
public class DonutMenuActivity extends AppCompatActivity {
    private ArrayList<Item> donutItems;
    private GlobalDataManager globalDataManager;


    /**
     * Get the references of all instances of Views defined in the layout file, set up the list of
     * items to be display in the RecyclerView.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);
        RecyclerView rcview = findViewById(R.id.rcView_menu);
        globalDataManager = GlobalDataManager.getInstance();
        setupMenuItems(); //add the list of items to the ArrayList
        ItemsAdapter adapter = new ItemsAdapter(this, globalDataManager, donutItems); //create the adapter
        rcview.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        rcview.setLayoutManager(new LinearLayoutManager(this));
        // set up main menu button
        findViewById(R.id.mainMenuButton).setOnClickListener(v -> mainMenuClick());
        findViewById(R.id.itemsSelected).setOnClickListener(v -> addItemsSelected());

    }

    /**
     * Helper method to set up the data (Set up the list of donut menu items).
     */
    private void setupMenuItems() {
        donutItems = globalDataManager.restDonutList();
        globalDataManager.getOrderManager().resetPotentialOrder();
        String[] donutTVLabel = getResources().getStringArray(R.array.tv_donutLabels);
        String[] donutFlavors = getResources().getStringArray(R.array.donutFlavors);
        for (int i = 0; i < 6; i++) {
            Donut donut = new Donut("Yeast Donuts", donutFlavors[i], 1);
            int image = GlobalDataManager.getInstance().getDonutImageResource(donutTVLabel[i]);
            String price = String.format("$%.2f", donut.getPrice());
            System.out.println(price);
            String[] parts = donutTVLabel[i].split(":");
            String label = parts[0] + ":\n" + parts[1];
            donutItems.add(new Item(donut, image, price, label));
        }
        for (int i = 6; i < 9; i++) {
            Donut donut = new Donut("Cake Donuts", donutFlavors[i], 1);
            int image = GlobalDataManager.getInstance().getDonutImageResource(donutTVLabel[i]);
            String price = String.format("$%.2f", donut.getPrice());
            String[] parts = donutTVLabel[i].split(":");
            String label = parts[0] + ":\n" + parts[1];
            donutItems.add(new Item(donut, image, price, label));
        }
        for (int i = 9; i < 12; i++) {
            Donut donut = new Donut("Donut Holes", donutFlavors[i], 1);
            int image = GlobalDataManager.getInstance().getDonutImageResource(donutTVLabel[i]);
            String price = String.format("$%.2f", donut.getPrice());
            String[] parts = donutTVLabel[i].split(":");
            String label = parts[0] + ":\n" + parts[1];
            donutItems.add(new Item(donut, image, price, label));
        }
    }

    /**
     * Handles the click event for the main menu button which will navigate back to the main menu.
     */
    public void mainMenuClick() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * returns the most updated running subtotal of the selected donut items.
     *
     * @return The updated subtotal of the selected donut items.
     */
    private String updateSubTotal() {
        double totalSubtotal = 0.0;
        Order order = globalDataManager.getOrderManager().getPotentialOrder();
        for (Donut donut : globalDataManager.getOrderManager().filterDonutItems(order)) {
            totalSubtotal += donut.getPrice();
        }
        return "$" + String.format("%.2f", totalSubtotal);
    }

    /**
     * Handles the click event for the add item selected to order button.
     * Adds the selected donut items to the order and restarting the activity to get it to its initial state.
     */
    public void addItemsSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(globalDataManager.getOrderManager().getPotentialOrder().getItems().isEmpty()){
            Toast.makeText(this, "please select an item first!", Toast.LENGTH_SHORT).show();
            return;
        }
        builder.setTitle("Add the selected items");
        builder.setMessage("Are you sure you want to add the selected items with a subtotal [" + updateSubTotal() + "] to the order?");
        // Add the "Yes" button, which removes the item if clicked
        builder.setPositiveButton("Yes", (dialog, which) -> {
            globalDataManager.getOrderManager().setCurrentToPotential();
            // Reload the activity
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            donutItems = globalDataManager.restDonutList();
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
