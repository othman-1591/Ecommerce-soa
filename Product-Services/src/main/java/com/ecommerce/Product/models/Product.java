package com.ecommerce.Product.models;

public class Product {

        private Long id;
        private String nom;
        private String description;
        private double prix;
        private String image;
        private int stock;

        public Product() {

        }

        public Product(String nom, String description, double prix, String image, int stock) {
            this.nom = nom;
            this.description = description;
            this.prix = prix;
            this.image = image;
            this.stock = stock;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
        public String getImage() {
        return image;
        }

         public void setImage(String image) {
        this.image = image;
        }


}
