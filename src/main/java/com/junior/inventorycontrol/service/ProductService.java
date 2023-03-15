package com.junior.inventorycontrol.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junior.inventorycontrol.entity.Product;
import com.junior.inventorycontrol.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        log.info("<<< Buscando todos os produtos disponiveis >>>");
        return productRepository.findAll();
    }

    public Optional<Product> findById(String id){
        if (productRepository.existsById(id) == false){
            log.info("Id {} nao correspondo a nenhum produto registrado.", id);
            return null;
        }
        return productRepository.findById(id);
    }

    public Boolean addAmount(String name, Integer amount){
        try {
            Integer amountCurrent = productRepository.findByAmount(name);
            log.info("Quantidade atual: " + amountCurrent);
            log.info("<<< Adicionando mais {} {} >>>", amount, name);
            Integer newAmount = amountCurrent + amount;
            productRepository.updateAmount(newAmount, name);
            log.info("Nova quantidade: {}", newAmount);
            return true;
        } catch (Exception e) {
            log.info("Erro ao tentar adicionar mais no estoque: " + e.getMessage());
        }
        return false;
    }

    public Boolean removeAmount(String name, Integer amount){
        try {
            Integer amountCurrent = productRepository.findByAmount(name);
            log.info("<<< Removendo {} {} >>>", amount, name);
            if (amountCurrent == 0){
                log.info("Produto {} esta com o estoque zerado.", name);
                return false;
            }
            if (amount > amountCurrent){
                log.info("Quantidade pedida acima da disponivel.");
                return false;
            }
            Integer newAmount = amountCurrent - amount;
            productRepository.updateAmount(newAmount, name);
            log.info("Quantidade ainda disponivel: {}", newAmount);
            return true;
        } catch (Exception e) {
            log.info("Erro ao tentar remover produtos: {}", e.getMessage());
        }
        return false;
    }

    public Boolean save(String name, Double price, Integer amount){
        try {
            Product product = new Product(name, price, amount);
            log.info("<<< Registrando {} como novo produto >>>", product.getName());
            if (productRepository.existsByName(product.getName())){
                log.info("Produto {} ja registrado!", name);
                return false;
            }
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            log.info("Erro ao tentar registrar novo produto: {}", e.getMessage());
        }
        return false;
    }

}
