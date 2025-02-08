package com.ecommerce.Product.repositories;

import com.ecommerce.Product.models.Product;
import com.ecommerce.Product.utils.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

        public List<Product> getAllProducts() {
            List<Product> produits = new ArrayList<>();
            String query = "SELECT * FROM produits";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Product produit = new Product();
                    produit.setId(rs.getLong("id"));
                    produit.setNom(rs.getString("nom"));
                    produit.setDescription(rs.getString("description"));
                    produit.setImage(rs.getString("image"));
                    produit.setPrix(rs.getDouble("prix"));
                    produit.setStock(rs.getInt("stock"));
                    produits.add(produit);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return produits;
        }

        public Product getProductById(Long id) {
            Product produit = null;
            String query = "SELECT * FROM produits WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    produit = new Product();
                    produit.setId(rs.getLong("id"));
                    produit.setNom(rs.getString("nom"));
                    produit.setDescription(rs.getString("description"));
                    produit.setImage(rs.getString("image"));
                    produit.setPrix(rs.getDouble("prix"));
                    produit.setStock(rs.getInt("stock"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return produit;
        }

        public List<Product> getProductsByIds(List<Long> ids) {
            List<Product> products = new ArrayList<>();
            if (ids == null || ids.isEmpty()) {
                return products;
            }

            StringBuilder query = new StringBuilder("SELECT * FROM produits WHERE id IN (");
            for (int i = 0; i < ids.size(); i++) {
                query.append("?");
                if (i < ids.size() - 1) {
                    query.append(", ");
                }
            }
            query.append(")");

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                for (int i = 0; i < ids.size(); i++) {
                    stmt.setLong(i + 1, ids.get(i));
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setNom(rs.getString("nom"));
                    product.setDescription(rs.getString("description"));
                    product.setImage(rs.getString("image"));
                    product.setPrix(rs.getDouble("prix"));
                    product.setStock(rs.getInt("stock"));

                    products.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return products;
        }



    public boolean createProduct(Product produit) {
        String query = "INSERT INTO produits (nom, description, prix, stock, image) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, produit.getNom());
            stmt.setString(2, produit.getDescription());
            stmt.setDouble(3, produit.getPrix());
            stmt.setInt(4, produit.getStock());
            stmt.setString(5, produit.getImage());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        public boolean updateProduct(Product produit) {
            String query = "UPDATE produits SET nom = ?, description = ?, prix = ?, stock = ?, image = ? WHERE id = ?";


            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, produit.getNom());
                stmt.setString(2, produit.getDescription());
                stmt.setDouble(3, produit.getPrix());
                stmt.setInt(4, produit.getStock());
                stmt.setString(5, produit.getImage());
                stmt.setLong(6, produit.getId());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean deleteProduct(Long id) {
            String query = "DELETE FROM produits WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setLong(1, id);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


}
