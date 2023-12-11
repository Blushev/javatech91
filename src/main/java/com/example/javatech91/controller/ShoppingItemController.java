package com.example.javatech91.controller;

import com.example.javatech91.model.ShoppingItem;
import com.example.javatech91.repository.ShoppingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-items")
public class ShoppingItemController {

    private final ShoppingItemRepository shoppingItemRepository;

    @Autowired
    public ShoppingItemController(ShoppingItemRepository shoppingItemRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Имя шаблона или "home.html"
    }

    @GetMapping
    public ResponseEntity<List<ShoppingItem>> getAllShoppingItems() {
        List<ShoppingItem> shoppingItems = shoppingItemRepository.findAll();
        return new ResponseEntity<>(shoppingItems, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ShoppingItem> createShoppingItem(@RequestBody ShoppingItem shoppingItem) {
        ShoppingItem createdItem = shoppingItemRepository.save(shoppingItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingItem> updateShoppingItem(@PathVariable Long id, @RequestBody ShoppingItem updatedItem) {
        if (shoppingItemRepository.existsById(id)) {
            updatedItem.setId(id);
            ShoppingItem savedItem = shoppingItemRepository.save(updatedItem);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingItem(@PathVariable Long id) {
        if (shoppingItemRepository.existsById(id)) {
            shoppingItemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingItem> getShoppingItemById(@PathVariable Long id) {
        return shoppingItemRepository.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/purchase")
    public ResponseEntity<ShoppingItem> markAsPurchased(@PathVariable Long id) {
        return shoppingItemRepository.findById(id)
                .map(item -> {
                    item.setPurchased(!item.isPurchased());
                    shoppingItemRepository.save(item);
                    return new ResponseEntity<>(item, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/error")
    public String handleError() {
        // Возвращает имя представления (шаблона) для страницы ошибки
        return "error";
    }

}