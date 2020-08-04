package com.udemy.springbootbackendapirest.services;

import com.udemy.springbootbackendapirest.models.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.udemy.springbootbackendapirest.repositories.IClientRepository;
import com.udemy.springbootbackendapirest.services.interfaces.IClientService;

import java.util.List;

@Service
public class ClientService implements IClientService {
    @Autowired
    private IClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        clientRepository.deleteById(id);
    }
}
