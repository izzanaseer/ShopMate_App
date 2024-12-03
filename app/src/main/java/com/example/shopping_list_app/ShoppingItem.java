package com.example.shopping_list_app;

public class ShoppingItem {
    private String id;
    private String itemName;
    private int quantity;
    private double price;

    // Empty constructor required for Firebase
    public ShoppingItem() {
    }

    public ShoppingItem(String id, String itemName, int quantity, double price) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
