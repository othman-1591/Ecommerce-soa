package com.ecommerce.client.controller;

import com.ecommerce.client.constants.ClientConstant;
import com.ecommerce.client.models.Client;
import com.ecommerce.client.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public String createClient(@RequestBody Client client) {
        if (clientService.createClient(client)) {
            return "Client créé avec succès !";
        } else {
            return "Erreur lors de la création du client.";
        }
    }



    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {
        boolean isAuthenticated = clientService.authenticate(email, password);

        if (isAuthenticated) {
            Client client = clientService.findByEmail(email);

            if (client != null) {
                ClientConstant.userId = client.getId();
                ClientConstant.userNom = client.getNom();
                ClientConstant.userPrenom = client.getPrenom();
                return "Connexion réussie!";
            }
        }
        return "Échec de l'authentification, vérifiez vos identifiants.";

    }

    @GetMapping("/session-info")
    public String getSessionInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String userNom = (String) session.getAttribute("userNom");
        String userPrenom = (String) session.getAttribute("userPrenom");

        if (userId == null) {
            return "Aucun utilisateur connecté.";
        }

        return "Client ID: " + userId + ", Nom: " + userNom + ", Prénom: " + userPrenom;
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpSession session) {
        Map<String, Object> userInfo = new HashMap<>();
        Long id = ClientConstant.userId;
        String nom = ClientConstant.userNom;
        String prenom = ClientConstant.userPrenom;

        if (id != null && nom != null && prenom != null) {
            userInfo.put("id", id);
            userInfo.put("nom", nom);
            userInfo.put("prenom", prenom);
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userInfo);
        }
    }

    @PutMapping("/{id}")
    public String updateClient(@PathVariable Long id, @RequestBody Client client) {
        if (clientService.updateClient(id, client)) {
            return "Client mis à jour avec succès";
        } else {
            return "Client non trouvé, mise à jour impossible";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        if (clientService.deleteClient(id)) {
            return "Client supprimé avec succès";
        } else {
            return "Client non trouvé, suppression impossible";
        }
    }
}
