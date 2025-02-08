package com.ecommerce.Order.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id;
    private Long clientId;
    private List<ProductSelection> products;
    private Double total;
    private LocalDateTime date;

    public Order() {}

    public Order(Long id, Long clientId, List<ProductSelection> products, Double total, LocalDateTime date) {
        this.id = id;
        this.clientId = clientId;
        this.products = products;
        this.total = total;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public List<ProductSelection> getProducts() { return products; }
    public void setProducts(List<ProductSelection> products) { this.products = products; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
