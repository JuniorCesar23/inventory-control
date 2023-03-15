package com.junior.inventorycontrol.repository;

import com.junior.inventorycontrol.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT amount FROM Product p WHERE p.name = ?1")
    Integer findByAmount(String name);

    @Modifying
    @Query("UPDATE Product p SET p.amount = ?1 WHERE p.name = ?2")
    void updateAmount(Integer newAmount, String name);

    Boolean existsByName(String name);

}
