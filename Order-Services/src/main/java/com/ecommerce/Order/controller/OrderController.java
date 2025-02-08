package com.ecommerce.Order.controller;

import com.ecommerce.Order.models.Order;
import com.ecommerce.Order.models.ProductSelection;
import com.ecommerce.Order.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam Long clientId,
            @RequestParam List<Long> productIds,
            @RequestParam List<Integer> quantities,
            @RequestParam Double total) {




        Order order = new Order();
        order.setClientId(clientId);
        order.setTotal(total);
        order.setDate(LocalDateTime.now());

        List<ProductSelection> products = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            ProductSelection product = new ProductSelection();
            product.setId(productIds.get(i));
            product.setQuantite(quantities.get(i));
            products.add(product);
        }

        order.setProducts(products);

        Order createdOrder = orderService.addOrder(order);
        return ResponseEntity.ok(createdOrder);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        boolean isUpdated = orderService.updateOrder(id, order);
        if (isUpdated) {
            return ResponseEntity.ok("Order updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long id) {
        boolean isDeleted = orderService.deleteOrder(id);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getOrdersByClientId(@PathVariable Long clientId) {
        System.out.println("Requête reçue pour clientId: " + clientId);
        List<Order> orders = orderService.getOrdersByClientId(clientId);

        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.status(404).body("Aucune commande trouvée pour le client ID : " + clientId);
        }
        return ResponseEntity.ok(orders);
    }
    }



