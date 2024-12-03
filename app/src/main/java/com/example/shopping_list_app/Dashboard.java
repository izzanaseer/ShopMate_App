package com.example.shopping_list_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {
    Button btnLogout;
    FloatingActionButton fabAdd;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("shopping_items");

        btnLogout = findViewById(R.id.btnLogout);
        fabAdd = findViewById(R.id.fab_add);
        auth = FirebaseAuth.getInstance();


        btnLogout.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        });
        fabAdd.setOnClickListener(v -> showAddItemDialog());
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        TextInputEditText etItemName = dialogView.findViewById(R.id.etItemName);
        TextInputEditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        TextInputEditText etPrice = dialogView.findViewById(R.id.etPrice);

        builder.setTitle("Add New Item")
                .setPositiveButton("Add", null) // Set to null to prevent dialog from dismissing automatically
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Override the positive button click listener to handle validation
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String itemName = etItemName.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();

            // Validation
            if (TextUtils.isEmpty(itemName)) {
                etItemName.setError("Please enter item name");
                return;
            }

            if (TextUtils.isEmpty(quantityStr)) {
                etQuantity.setError("Please enter quantity");
                return;
            }

            if (TextUtils.isEmpty(priceStr)) {
                etPrice.setError("Please enter price");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                // Generate unique ID for the item
                String itemId = databaseReference.push().getKey();

                // Create new shopping item
                ShoppingItem item = new ShoppingItem(itemId, itemName, quantity, price);

                // Save to database
                databaseReference.child(itemId).setValue(item)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(Dashboard.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        })
                        .addOnFailureListener(e -> Toast.makeText(Dashboard.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers for quantity and price", Toast.LENGTH_SHORT).show();
            }
        });
    }
}