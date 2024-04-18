package com.example.rucafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * This is an example of an Activity where a RecyclerView is used.
 * @author Lily Chang
 */
public class DonutMenuActivity extends AppCompatActivity {
    private ArrayList<Item> donutItems;
    private GlobalDataManager globalDataManager;


    /**
     * Get the references of all instances of Views defined in the layout file, set up the list of
     * items to be display in the RecyclerView.
     * @param savedInstanceState
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
     * Helper method to set up the data (the Model of the MVC).
     */
    private void setupMenuItems() {
        globalDataManager.resetDonutItems();
        donutItems = globalDataManager.getDonutItems();
        String [] donutTVLabel = getResources().getStringArray(R.array.tv_donutLabels);
        String [] donutFlavors = getResources().getStringArray(R.array.donutFlavors);
        for (int i = 0; i < 6; i++) {
            Donut donut = new Donut("Yeast Donuts",donutFlavors[i],1);
            int image = GlobalDataManager.getInstance().getDonutImageResource(donutTVLabel[i]);
            String price = String.format("%.2f", donut.getPrice());
            String[] parts = donutTVLabel[i].split(":");
            String label = parts[0] + ":\n" + parts[1];
            donutItems.add(new Item(donut, image ,price, label));
        }
        for (int i = 6; i < 9; i++) {
            Donut donut = new Donut("Cake Donuts",donutFlavors[i],1);
            int image = GlobalDataManager.getInstance().getDonutImageResource(donutTVLabel[i]);
            String price = String.format("%.2f", donut.getPrice());
            String[] parts = donutTVLabel[i].split(":");
            String label = parts[0] + ":\n" + parts[1];
            donutItems.add(new Item(donut, image ,price, label));
        }
        for (int i = 9; i < 12; i++) {
            Donut donut = new Donut("Donut Holes",donutFlavors[i],1);
            int image = GlobalDataManager.getInstance().getDonutImageResource(donutTVLabel[i]);
            String price = String.format("%.2f", donut.getPrice());
            String[] parts = donutTVLabel[i].split(":");
            String label = parts[0] + ":\n" + parts[1];
            donutItems.add(new Item(donut, image ,price, label));
        }
    }
    public void mainMenuClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String updateSubTotal() {
        double totalSubtotal = 0.0;
        Order order = globalDataManager.getOrderManager().getPotentialOrder();
        for (Donut donut : globalDataManager.getOrderManager().filterDonutItems(order)) {
            totalSubtotal += donut.getPrice();
        }
        return "$" + String.format("%.2f", totalSubtotal);
    }

    public void addItemsSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add the selected items");
        builder.setMessage("Are you sure you want to add the selected items with a subtotal [" + updateSubTotal() + "] to the order?");

        // Add the "Yes" button, which removes the item if clicked
        builder.setPositiveButton("Yes", (dialog, which) -> {
            globalDataManager.getOrderManager().setCurrentToPotential();

            // Reload the activity
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            globalDataManager.resetDonutItems();
            donutItems = globalDataManager.getDonutItems();
            // Dismiss the dialog
            dialog.dismiss();
        });

        // Add the "No" button, which closes the dialog without doing anything
        builder.setNegativeButton("No", (dialog, which) -> {
            // Do nothing, just close the dialog
            dialog.dismiss();
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
