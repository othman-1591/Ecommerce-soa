package com.ecommerce.Order.repositories;

import com.ecommerce.Order.models.Order;
import com.ecommerce.Order.models.ProductSelection;
import com.ecommerce.Order.utils.DatabaseConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Order saveOrder(Order order) {
        String sql = "INSERT INTO orders (client_id, products, total, date) VALUES (?, ?::jsonb, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String productsJson = objectMapper.writeValueAsString(order.getProducts());

            stmt.setLong(1, order.getClientId());
            stmt.setString(2, productsJson);
            stmt.setDouble(3, order.getTotal());
            stmt.setTimestamp(4, Timestamp.valueOf(order.getDate()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return order;
    }


    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                List<ProductSelection> products = Arrays.asList(
                        objectMapper.readValue(rs.getString("products"), ProductSelection[].class)
                );

                orders.add(new Order(
                        rs.getLong("id"),
                        rs.getLong("client_id"),
                        products,
                        rs.getDouble("total"),
                        rs.getTimestamp("date").toLocalDateTime()
                ));
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> findOrdersByClientId(Long clientId) {
        String sql = "SELECT * FROM orders WHERE client_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Commande trouvée : " + rs.getLong("id")); // Debug

                    String productsJson = rs.getString("products");
                    List<ProductSelection> products = productsJson != null && !productsJson.isEmpty()
                            ? Arrays.asList(objectMapper.readValue(productsJson, ProductSelection[].class))
                            : new ArrayList<>();

                    orders.add(new Order(
                            rs.getLong("id"),
                            rs.getLong("client_id"),
                            products,
                            rs.getDouble("total"),
                            rs.getTimestamp("date").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.err.println("Erreur JSON : " + e.getMessage());
        }
        return orders;
    }

public boolean updateOrder(Long id, Order order) {
    String sql = "UPDATE orders SET client_id = ?, products = ?::jsonb, total = ?, date = ? WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        String productsJson = objectMapper.writeValueAsString(order.getProducts());

        stmt.setLong(1, order.getClientId());
        stmt.setString(2, productsJson);
        stmt.setDouble(3, order.getTotal());
        stmt.setTimestamp(4, Timestamp.valueOf(order.getDate()));
        stmt.setLong(5, id);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException | JsonProcessingException e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean deleteOrder(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
