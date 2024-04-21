package com.example.rucafe;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class for the RecyclerView displaying items.
 *
 * @author Anthony Paulino
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    private Context context;
    private ArrayList<Item> items;
    private GlobalDataManager globalDataManager;

    /**
     * Constructor for ItemsAdapter.
     *
     * @param context           The context of the activity or fragment.
     * @param globalDataManager The GlobalDataManager instance.
     * @param items             The list of items to be displayed.
     */
    public ItemsAdapter(Context context, GlobalDataManager globalDataManager, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
        this.globalDataManager = globalDataManager;
    }

    /**
     * Called when RecyclerView needs a new {@link ItemsHolder} of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ItemsHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ItemsHolder(view, globalDataManager);
    }

    /**
     * Initializes and sets the listener for the spinner, displays the image of the item, and sets the text fields for the row with the items name and price.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        Item currentItem = items.get(position);
        setupHolder(holder, position);
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Event handler for when an quantity is selected from the spinner.
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param pos The position of the view in the adapter
             * @param id The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int quantity = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                Donut donut = (Donut) currentItem.getItem();
                donut.setQuantity(quantity);
            }

            /**
             * Event handler when nothing is selected from the spinner.
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Helps with the initialization process for the onBindViewHolder method.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    private void setupHolder(@NonNull ItemsHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.tv_name.setText(currentItem.getTVLabel());
        holder.tv_price.setText(currentItem.getPrice());
        holder.im_item.setImageResource(currentItem.getImage());
        holder.globalDataManager = this.globalDataManager; // Pass GlobalDataManager object to ItemsHolder
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(spinnerAdapter);
        holder.spinner.setSelection(0);
    }

    /**
     * Retrieve the size of the items in the list.
     *
     * @return the integer of the amount of items in the list.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ViewHolder class for individual item views in the RecyclerView.
     */
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price;
        private ImageView im_item;
        private Button btn;
        private ConstraintLayout parentLayout;
        private Spinner spinner;
        private GlobalDataManager globalDataManager;

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
         * Constructor for ItemsHolder.
         *
         * @param itemView          The item view inflated from the layout file.
         * @param globalDataManager The GlobalDataManager instance.
         */
        public ItemsHolder(@NonNull View itemView, GlobalDataManager globalDataManager) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_flavor);
            tv_price = itemView.findViewById(R.id.tv_price);
            im_item = itemView.findViewById(R.id.im_item);
            btn = itemView.findViewById(R.id.btn_add);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            spinner = itemView.findViewById(R.id.spinner_quantity);
            setAddButtonOnClick(itemView);
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         *
         * @param itemView
         */
        private void setAddButtonOnClick(@NonNull View itemView) {
            btn.setOnClickListener(view -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                alert.setTitle("Select or unselect the donut item");
                alert.setMessage(tv_name.getText().toString());
                String tvLabel = tv_name.getText().toString();
                Donut donut = (Donut) Item.getMenuItemByTVLabel(globalDataManager.getDonutItems(), tvLabel);
                alert.setPositiveButton("Select", (dialog, which) -> {
                    if (globalDataManager.getOrderManager().isDonutItemInPotentialOrder(donut)) {
                        Toast.makeText(itemView.getContext(), "Donut was previously selected", Toast.LENGTH_LONG).show();
                    } else {
                        globalDataManager.getOrderManager().addToPotentialOrder(donut);
                        Toast.makeText(itemView.getContext(), "Donut selected, Sub-total: " + updateSubTotal(), Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Unselect", (dialog, which) -> {
                    if (globalDataManager.getOrderManager().isDonutItemInPotentialOrder(donut)) {
                        globalDataManager.getOrderManager().removeFromPotentialOrder(donut);
                        Toast.makeText(itemView.getContext(), "Donut unselected, Sub-total: " + updateSubTotal(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(itemView.getContext(), "Donut has not been selected yet", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            });
        }
    }
}

