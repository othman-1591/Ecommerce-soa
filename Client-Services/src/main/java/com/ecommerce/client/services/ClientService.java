package com.ecommerce.client.services;

import com.ecommerce.client.models.Client;
import com.ecommerce.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    public Client getClientById(Long id) {
        return clientRepository.getClientById(id);
    }

    public Boolean createClient(Client client) {
       return clientRepository.createClient(client);
    }

    public boolean updateClient(Long id, Client client) {
        return clientRepository.updateClient(id, client);
    }
    public boolean deleteClient(Long id) {
        return clientRepository.deleteClient(id);
    }

    public boolean authenticate(String email, String password) {
        return clientRepository.authenticate(email, password);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

}