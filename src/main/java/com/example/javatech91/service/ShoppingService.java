package com.example.javatech91.service;

import com.example.javatech91.model.ShoppingItem;
import com.example.javatech91.repository.ShoppingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingService {

    private final ShoppingItemRepository shoppingItemRepository;

    @Autowired
    public ShoppingService(ShoppingItemRepository shoppingItemRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
    }

    public List<ShoppingItem> getAllItems() {
        return shoppingItemRepository.findAll();
    }

    public Optional<ShoppingItem> getItemById(Long id) {
        return shoppingItemRepository.findById(id);
    }

    public ShoppingItem createItem(ShoppingItem item) {
        return shoppingItemRepository.save(item);
    }

    public Optional<ShoppingItem> updateItem(Long id, ShoppingItem updatedItem) {
        if (shoppingItemRepository.existsById(id)) {
            updatedItem.setId(id);
            return Optional.of(shoppingItemRepository.save(updatedItem));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteItem(Long id) {
        if (shoppingItemRepository.existsById(id)) {
            shoppingItemRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean markAsPurchased(Long id) {
        Optional<ShoppingItem> optionalItem = shoppingItemRepository.findById(id);
        if (optionalItem.isPresent()) {
            ShoppingItem item = optionalItem.get();
            item.setPurchased(true);
            shoppingItemRepository.save(item);
            return true;
        } else {
            return false;
        }
    }
}