package com.udemy.springbootbackendapirest.services.interfaces;

import com.udemy.springbootbackendapirest.models.entities.Client;

import java.util.List;

public interface IClientService {

    public List<Client> findAll();

    public Client findById(Integer id);

    public Client save(Client client);

    public void delete(Integer id);
}
