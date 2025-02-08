package com.ecommerce.Product.services;

import com.ecommerce.Product.models.Product;
import com.ecommerce.Product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

        @Autowired
        private ProductRepository productRepository;

        public List<Product> getAllProducts() {
            return productRepository.getAllProducts();
        }

        public Product getProductById(Long id) {
            return productRepository.getProductById(id);
        }

        public List<Product> getProductsByIds(List<Long> ids) {
        return productRepository.getProductsByIds(ids);
        }
        public boolean createProduct(Product produit) {
        return productRepository.createProduct(produit);
        }

        public boolean updateProduct(Product product) {
            return productRepository.updateProduct(product);
        }

        public boolean deleteProduct(Long id) {
            return productRepository.deleteProduct(id);
        }

}
