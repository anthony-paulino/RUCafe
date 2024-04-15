package com.example.rucafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    private Context context;
    private ArrayList<Item> items;
    private GlobalDataManager globalDataManager;
    private ArrayList<Chip> chipList;
    private ArrayList<Spinner> spinnerList;



    public ItemsAdapter(Context context, GlobalDataManager globalDataManager, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
        this.globalDataManager = globalDataManager;
        this.chipList = new ArrayList<>();
        this.spinnerList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ItemsHolder(view, globalDataManager);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.tv_name.setText(currentItem.getTVLabel());
        holder.tv_price.setText(currentItem.getPrice());
        holder.im_item.setImageResource(currentItem.getImage());
        holder.globalDataManager = this.globalDataManager; // Pass GlobalDataManager object to ItemsHolder
        // Populate spinner with data
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(spinnerAdapter);
        // Preselect quantity to 1
        holder.spinner.setSelection(0);
        // Set item click listener for the spinner to update the quantity
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // Update the quantity of the current item
                int quantity = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                Donut donut = (Donut) currentItem.getItem();
                donut.setQuantity(quantity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        // Add chip view to the list when it is bound
        chipList.add(holder.chip_selected);
        spinnerList.add(holder.spinner);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Method to reset both chip selections and spinner selections
    public void resetAllViews() {
        for (Chip chip : chipList) {
            chip.setSelected(false);
        }
        for (Spinner spinner : spinnerList) {
            spinner.setSelection(0);
        }
        notifyDataSetChanged(); // Notify adapter that data has changed
    }
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price;
        private ImageView im_item;
        private Chip chip_selected;
        private ConstraintLayout parentLayout;
        private Spinner spinner;
        private GlobalDataManager globalDataManager;

        public ItemsHolder(@NonNull View itemView, GlobalDataManager globalDataManager) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_flavor);
            tv_price = itemView.findViewById(R.id.tv_price);
            im_item = itemView.findViewById(R.id.im_item);
            chip_selected = itemView.findViewById(R.id.btn_add);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            spinner = itemView.findViewById(R.id.spinner_quantity);
            this.globalDataManager = globalDataManager;
            chip_selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chip_selected.setSelected(!chip_selected.isSelected());
                    String tvLabel = tv_name.getText().toString();
                    Donut donut = (Donut) Item.getMenuItemByTVLabel(globalDataManager.getDonutItems(), tvLabel);

                    if (chip_selected.isSelected()) {
                        // Add item to order using GlobalDataManager
                        globalDataManager.getOrderManager().addToPotentialOrder(donut);
                        Toast.makeText(itemView.getContext(), tvLabel + " selected", Toast.LENGTH_SHORT).show();
                    } else {
                        // Remove item from order using GlobalDataManager
                        globalDataManager.getOrderManager().removeFromPotentialOrder(donut);
                        Toast.makeText(itemView.getContext(), tvLabel + " unselected", Toast.LENGTH_SHORT).show();
                    }
                    System.out.println(globalDataManager.getOrderManager().getPotentialOrder().toString());
                }
            });
        }
    }
}

