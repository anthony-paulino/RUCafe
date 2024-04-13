package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * This is an example of an Activity where a RecyclerView is used.
 * @author Lily Chang
 */
public class DonutMenuActivity extends AppCompatActivity {
    private ArrayList<Item> donutItems = new ArrayList<>();

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
        setupMenuItems(); //add the list of items to the ArrayList
        ItemsAdapter adapter = new ItemsAdapter(this, donutItems); //create the adapter
        rcview.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        rcview.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Helper method to set up the data (the Model of the MVC).
     */
    private void setupMenuItems() {
        String [] donutTVLabel = getResources().getStringArray(R.array.tv_donutLabels);
        String [] donutFlavors = getResources().getStringArray(R.array.donutFlavors);
        for (int i = 0; i < 3; i++) {
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
            donutItems.add(new Item(donut, image ,price, donutTVLabel[i]));
        }
        for (int i = 9; i < 12; i++) {
            Donut donut = new Donut("Donut Holes",donutFlavors[i],1);
            int image = GlobalDataManager.getInstance().getDonutImageResource(donutTVLabel[i]);
            String price = String.format("%.2f", donut.getPrice());
            donutItems.add(new Item(donut, image ,price, donutTVLabel[i]));
        }
    }
}
