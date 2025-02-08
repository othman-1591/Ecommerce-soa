package com.ecommerce.client.repositories;

import com.ecommerce.client.models.Client;
import com.ecommerce.client.utils.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientRepository {

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                clients.add(mapClient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public Client getClientById(Long id) {
        String query = "SELECT * FROM client WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapClient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createClient(Client client) {
        String query = "INSERT INTO client(nom, prenom, adresse, num_Tel, ville, email, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getAdresse());
            stmt.setString(4, client.getNumTel());
            stmt.setString(5, client.getVille());
            stmt.setString(6, client.getEmail());
            stmt.setString(7, client.getPassword());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean authenticate(String email, String password) {
        String query = "SELECT * FROM client WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Client mapClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id"));
        client.setNom(rs.getString("nom"));
        client.setPrenom(rs.getString("prenom"));
        client.setAdresse(rs.getString("adresse"));
        client.setNumTel(rs.getString("num_Tel"));
        client.setVille(rs.getString("ville"));
        client.setEmail(rs.getString("email"));
        client.setPassword(rs.getString("mot_de_passe"));
        return client;
    }

    public Client findByEmail(String email) {
        Client client = null;
        String sql = "SELECT * FROM client WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setId(rs.getLong("id"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));
                client.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }


    public boolean updateClient(Long id, Client client) {
        String query = "UPDATE client SET nom = ?, prenom = ?, adresse = ?, num_Tel = ?, ville = ?, email = ?, mot_de_passe = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getAdresse());
            stmt.setString(4, client.getNumTel());
            stmt.setString(5, client.getVille());
            stmt.setString(6, client.getEmail());
            stmt.setString(7, client.getPassword());
            stmt.setLong(8, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClient(Long id) {
        String query = "DELETE FROM client WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
