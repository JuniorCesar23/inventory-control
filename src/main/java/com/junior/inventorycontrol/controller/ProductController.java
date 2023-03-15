package com.junior.inventorycontrol.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.inventorycontrol.entity.Product;
import com.junior.inventorycontrol.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable String id){
        Optional<Product> response = productService.findById(id);
        if (response == null){
            return null;
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Boolean response = productService.save(product.getName(), product.getPrice(), product.getAmount());
        if (response == false){
            return null;
        }
        return ResponseEntity.ok().body(product);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Boolean> addProduct(@RequestBody Product product) {
        Boolean response = productService.addAmount(product.getName(), product.getAmount());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/remove")
    public ResponseEntity<Boolean> removeProduct(@RequestBody Product product) {
        Boolean response = productService.removeAmount(product.getName(), product.getAmount());
        return ResponseEntity.ok().body(response);
    }

}
