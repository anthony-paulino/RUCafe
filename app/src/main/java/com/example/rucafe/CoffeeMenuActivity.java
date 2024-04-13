package com.example.rucafe;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMenuActivity extends AppCompatActivity {

    private OrderManager orderManager;
    private Spinner coffeeSizeSpinner;
    private Spinner coffeeQuantitySpinner;
    private EditText subTotalTextView;
    private ChipGroup addInChipGroup;
    private List<String> selectedAddIns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        // Get the OrderManager object (Singleton Process)
        orderManager = GlobalDataManager.getInstance().getOrderManager();
        // Initialize UI components
        findViewById(R.id.mainMenuButton).setOnClickListener(v -> mainMenuClick());
        initSpinners();
        initChipGroup();
        setupAddOrderButton();
        initialState();
    }

    private void initialState(){
        // Deselect all chips in the ChipGroup
        for (int i = 0; i < addInChipGroup.getChildCount(); i++) {
            View child = addInChipGroup.getChildAt(i);
            if (child instanceof Chip) {
                ((Chip) child).setChecked(false);
            }
        }
        selectedAddIns.clear();
        // Reset spinner selections
        coffeeSizeSpinner.setSelection(0);
        coffeeQuantitySpinner.setSelection(0);
        //update subtotal
        updateSubTotal();
    }
    private void initSpinners() {
        coffeeSizeSpinner = findViewById(R.id.coffeeSizeSpinner);
        coffeeQuantitySpinner = findViewById(R.id.coffeeQuantitySpinner);
        subTotalTextView = findViewById(R.id.coffeeSubTotal);

        // Set up coffee size spinner
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(
                this, R.array.coffee_sizes_array, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeSizeSpinner.setAdapter(sizeAdapter);

        // Set up coffee quantity spinner
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(
                this, R.array.coffee_quantity_array, android.R.layout.simple_spinner_item);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeQuantitySpinner.setAdapter(quantityAdapter);

        // Set the listeners
        initCoffeeSizeSpinnerListener();
        initCoffeeQuantitySpinnerListener();
    }

    private void initCoffeeSizeSpinnerListener() {
        coffeeSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSubTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void initCoffeeQuantitySpinnerListener() {
        coffeeQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSubTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void initChipGroup() {
        addInChipGroup = findViewById(R.id.addInChipGroup);
        // Find each chip by ID and set click listeners
        Chip mochaChip = findViewById(R.id.mochaChip);
        Chip frenchVanillaChip = findViewById(R.id.frenchVanillaChip);
        Chip irishCreamChip = findViewById(R.id.irishCreamChip);
        Chip sweetCreamChip = findViewById(R.id.sweetCreamChip);
        Chip caramelChip = findViewById(R.id.caramelChip);

        mochaChip.setOnClickListener(v -> handleChipClick(mochaChip));
        frenchVanillaChip.setOnClickListener(v -> handleChipClick(frenchVanillaChip));
        irishCreamChip.setOnClickListener(v -> handleChipClick(irishCreamChip));
        sweetCreamChip.setOnClickListener(v -> handleChipClick(sweetCreamChip));
        caramelChip.setOnClickListener(v -> handleChipClick(caramelChip));
    }

    private void handleChipClick(Chip chip) {
        String chipText = chip.getText().toString();
        if (chip.isChecked()) {
            selectedAddIns.add(chipText);
        } else {
            selectedAddIns.remove(chipText);
        }
        updateSubTotal();
    }

    private void updateSubTotal() {
        String selectedSize = coffeeSizeSpinner.getSelectedItem().toString();
        int selectedQuantity = Integer.parseInt(coffeeQuantitySpinner.getSelectedItem().toString());

        // Calculate subtotal based on size, quantity, and selected add-ins
        Coffee coffee = new Coffee(selectedSize, selectedAddIns, selectedQuantity);
        double subtotal = coffee.getPrice();
        System.out.println();
        subTotalTextView.setText(getString(R.string.subTotalText, subtotal));

    }

    private void setupAddOrderButton() {
        Button addOrderButton = findViewById(R.id.addToOrderButton);
        addOrderButton.setOnClickListener(v -> addSelectedCoffeeToOrder());
    }

    private void addSelectedCoffeeToOrder() {
        String selectedSize = coffeeSizeSpinner.getSelectedItem().toString();
        int selectedQuantity = Integer.parseInt(coffeeQuantitySpinner.getSelectedItem().toString());
        List<String> addIns = new ArrayList<>(selectedAddIns); // Create a new ArrayList with the same elements as selectedAddIns
        // Calculate subtotal based on size, quantity, and selected add-ins
        Coffee coffee = new Coffee(selectedSize, addIns, selectedQuantity);
        orderManager.addToCurrentOrder(coffee);
        // Display a toast message indicating that the coffee item was added to the order
        showToast(getString(R.string.coffee_added_to_order_message));
        System.out.println(orderManager.getCurrentOrder().toString());
        // Update to initial state
        initialState();
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void mainMenuClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
