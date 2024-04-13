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

public class SandwichMenuActivity extends AppCompatActivity {

    private OrderManager orderManager;
    private Spinner breadSpinner;
    private Spinner proteinSpinner;
    private EditText subTotalTextView;
    private ChipGroup addOnChipGroup;
    private List<String> selectedAddOns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich);
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
        for (int i = 0; i < addOnChipGroup.getChildCount(); i++) {
            View child = addOnChipGroup.getChildAt(i);
            if (child instanceof Chip) {
                ((Chip) child).setChecked(false);
            }
        }
        selectedAddOns.clear();
        // Reset spinner selections
        breadSpinner.setSelection(0);
        proteinSpinner.setSelection(0);
        //update subtotal
        updateSubTotal();
    }
    private void initSpinners() {
      breadSpinner = findViewById(R.id.breadSpinner);
        proteinSpinner = findViewById(R.id.proteinSpinner);
        subTotalTextView = findViewById(R.id.sandwichSubTotal);
        // Set up bread spinner
        ArrayAdapter<CharSequence> breadAdapter = ArrayAdapter.createFromResource(
                this, R.array.sandwich_bread_array, android.R.layout.simple_spinner_item);
        breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      breadSpinner.setAdapter(breadAdapter);
        // Set up protein spinner
        ArrayAdapter<CharSequence> proteinAdapter = ArrayAdapter.createFromResource(
                this, R.array.sandwich_protein_array, android.R.layout.simple_spinner_item);
        proteinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proteinSpinner.setAdapter(proteinAdapter);
        // Set the listeners
        initBreadSpinnerListener();
        initProteinSpinnerListener();
    }

    private void initBreadSpinnerListener() {
      breadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initProteinSpinnerListener() {
        proteinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        addOnChipGroup = findViewById(R.id.addOnChipGroup);
        // Find each chip by ID and set click listeners
        Chip lettuceChip = findViewById(R.id.lettuceChip);
        Chip tomatoChip = findViewById(R.id.tomatoChip);
        Chip cheeseChip = findViewById(R.id.cheeseChip);
        Chip onionChip = findViewById(R.id.onionChip);

        lettuceChip.setOnClickListener(v -> handleChipClick(lettuceChip));
        tomatoChip.setOnClickListener(v -> handleChipClick(tomatoChip));
        cheeseChip.setOnClickListener(v -> handleChipClick(cheeseChip));
        onionChip.setOnClickListener(v -> handleChipClick(onionChip));
    }

    private void handleChipClick(Chip chip) {
        String chipText = chip.getText().toString();
        if (chip.isChecked()) {
            selectedAddOns.add(chipText);
        } else {
            selectedAddOns.remove(chipText);
        }
        updateSubTotal();
    }

    private void updateSubTotal() {
        String selectedBread = breadSpinner.getSelectedItem().toString();
        String selectedProtein = proteinSpinner.getSelectedItem().toString();
        // Calculate subtotal based on bread, protein, and selected add-ons
        Sandwich sandwich = new Sandwich(selectedBread, selectedProtein, selectedAddOns ,1);
        double subtotal = sandwich.getPrice();
        subTotalTextView.setText(getString(R.string.subTotalText, subtotal));

    }

    private void setupAddOrderButton() {
        Button addOrderButton = findViewById(R.id.addToOrderButton);
        addOrderButton.setOnClickListener(v -> addSelectedSandwichToOrder());
    }

    private void addSelectedSandwichToOrder() {
        String selectedBread = breadSpinner.getSelectedItem().toString();
        String selectedProtein = proteinSpinner.getSelectedItem().toString();
        List<String> addOns = new ArrayList<>(selectedAddOns); // Create a new ArrayList with the same elements as selectedAddOns
        // Calculate subtotal based on bread, protein, and selected add-ons
        Sandwich sandwich = new Sandwich(selectedBread, selectedProtein, addOns ,1);
        orderManager.addToCurrentOrder(sandwich);
        // Display a toast message indicating that the sandwich item was added to the order
        showToast(getString(R.string.sandwich_added_to_order_message));
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