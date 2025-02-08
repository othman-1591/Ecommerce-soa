package com.ecommerce.Order.models;

public class ProductSelection {
    private Long id;
    private int quantite;

    public ProductSelection() {}

    public ProductSelection(Long id, int quantite) {
        this.id = id;
        this.quantite = quantite;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
