package com.example.shopping_list_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Dashboard extends AppCompatActivity {
    Button btnLogout;
    FloatingActionButton fabAdd;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<ShoppingItem, ShoppingItemViewHolder> adapter;


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
        auth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btnLogout);
        fabAdd = findViewById(R.id.fab_add);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupRecyclerView();

        btnLogout.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        });
        fabAdd.setOnClickListener(v -> showAddItemDialog());
    }

    private void setupRecyclerView() {
        Query query = databaseReference.orderByChild("itemName");
        FirebaseRecyclerOptions<ShoppingItem> options =
                new FirebaseRecyclerOptions.Builder<ShoppingItem>()
                        .setQuery(query, ShoppingItem.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<ShoppingItem, ShoppingItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ShoppingItemViewHolder holder, int position, @NonNull ShoppingItem model) {
                holder.tvItemName.setText(model.getItemName());
                holder.tvQuantity.setText(String.valueOf(model.getQuantity()));
                holder.tvPrice.setText(String.format("%.2f", model.getPrice()));

                holder.btnDelete.setOnClickListener(v -> {
                    String itemId = getRef(position).getKey();
                    deleteItem(itemId);
                });
            }

            @NonNull
            @Override
            public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.shopping_item, parent, false);
                return new ShoppingItemViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void deleteItem(String itemId) {
        if (itemId != null) {
            databaseReference.child(itemId).removeValue()
                    .addOnSuccessListener(aVoid -> Toast.makeText(Dashboard.this, "Item deleted successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(Dashboard.this, "Error deleting item", Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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