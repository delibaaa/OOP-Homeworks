package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    private Map<String, Integer> itemsQuantities;

    public Cart() {
        this.itemsQuantities = new HashMap<>();
    }
    public void deleteAll() {
        itemsQuantities.clear();
    }
    public int getTotal() {
        int sum = 0;
        for (int quantity : itemsQuantities.values()) {
            sum += quantity;
        }
        return sum;
    }
    public boolean isEmpty() {
        return itemsQuantities.isEmpty();
    }
    public void updateQuantity(String productId, int newQuantity) {
        if (newQuantity <= 0) {
            itemsQuantities.remove(productId);
        } else {
            itemsQuantities.put(productId, newQuantity);
        }
    }
    public void addProduct(String productId) {
        Integer currentQuantity = itemsQuantities.get(productId);
        if (currentQuantity == null) {
            currentQuantity = 0;
        }
        updateQuantity(productId, currentQuantity + 1);
    }
    public void addProduct(String productId, int quantity) {
        Integer currentQuantity = itemsQuantities.get(productId);
        if (currentQuantity == null) {
            currentQuantity = 0;
        }
        updateQuantity(productId, currentQuantity + quantity);
    }
    public int getQuantity(String productId) {
        return itemsQuantities.getOrDefault(productId, 0);
    }
    public Set<String> getIds() {
        return itemsQuantities.keySet();
    }
}
