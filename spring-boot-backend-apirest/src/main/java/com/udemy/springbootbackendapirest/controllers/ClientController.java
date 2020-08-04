package com.udemy.springbootbackendapirest.controllers;

import com.udemy.springbootbackendapirest.models.entities.Client;
import com.udemy.springbootbackendapirest.services.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    IClientService clientService;

    @GetMapping("/clients")
    public List<Client> getClients(){
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> showClient(@PathVariable Integer id){

        Client client = null;
        Map<String, Object> response = new HashMap<>();

        try{
            client = clientService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error en la DDBB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (client == null){
            response.put("mensaje", "El cliente con el ID: ".concat(id.toString().concat(" no existe.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }

    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result){
        Client cliente = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " +err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            cliente = clientService.save(client);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la DDBB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido creado con exito");
        response.put("cliente", cliente);


        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id, @Valid @RequestBody Client client, BindingResult result){
        Client clientInDB = clientService.findById(id);
        Client clientSaved = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " +err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (client == null){
            response.put("mensaje", "El cliente con el ID: ".concat(id.toString().concat(" no existe.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clientInDB.setName(client.getName());
            clientInDB.setSurname(client.getSurname());
            clientInDB.setEmail(client.getEmail());

            clientSaved = clientService.save(clientInDB);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar en la DDBB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Client>(clientSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();
        System.out.println("prueba");
        try {
            clientService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el cliente en la DDBB.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public List<String> validateErrors(BindingResult result){
        List<String> errors = null;

        if (result.hasErrors()){

            errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " +err.getDefaultMessage())
                    .collect(Collectors.toList());
        }

        return errors;
    }
}
