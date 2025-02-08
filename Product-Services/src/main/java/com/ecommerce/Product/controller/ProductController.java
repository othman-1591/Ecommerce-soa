package com.ecommerce.Product.controller;

import com.ecommerce.Product.models.Product;
import com.ecommerce.Product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produits")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {

    private static final String UPLOAD_DIR = "uploads/images/";
        @Autowired
        private ProductService produitService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public List<Product> getAllProducts() {
            return produitService.getAllProducts();
        }

        @GetMapping("/{id}")
        public Product getProductById(@PathVariable Long id) {
            return produitService.getProductById(id);
        }


    @PostMapping
    public String createProduct(@RequestParam("nom") String nom,
                                 @RequestParam("prix") Double prix,
                                 @RequestParam("stock") Integer stock,
                                 @RequestParam("description") String description,
                                 @RequestParam("image") MultipartFile image) throws IOException {
        String imagePath = saveImage(image);
        Product produit = new Product(nom, description, prix, imagePath, stock);
        produitService.createProduct(produit);

        return "Produit ajouté avec succès!";
    }

    private String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/images/" + fileName;
    }


    @PutMapping("/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("prix") Double prix,
            @RequestParam("stock") Integer stock,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = saveImage(image);
        }
        Product produit = new Product(nom, description, prix, imagePath, stock);
        produit.setId(id);
        boolean isUpdated = produitService.updateProduct(produit);

        return isUpdated ? "Produit mis à jour avec succès!" : "Erreur lors de la mise à jour du produit.";
    }

    @DeleteMapping("/{id}")
        public String deleteProduct(@PathVariable Long id) {
            boolean isDeleted = produitService.deleteProduct(id);
            return isDeleted ? "Product supprimé avec succès!" : "Erreur lors de la suppression du produit.";
        }
    @PostMapping("/details")
    public List<Product> getProductsByIds(@RequestBody List<Long> ids) {
        return produitService.getProductsByIds(ids);
    }

}
