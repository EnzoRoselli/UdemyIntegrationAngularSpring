package com.udemy.springbootbackendapirest.repositories;

import com.udemy.springbootbackendapirest.models.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface IClientRepository extends CrudRepository<Client, Integer> {
}
