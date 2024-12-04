package org.example.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        try {
            clientService.addClient(client);
            log.info("Client added: {}", client);
            return ResponseEntity.ok("Client added");
        } catch (Exception e) {
            log.error("Error occurred while adding client: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllClients() {
        try {
            var clients = clientService.getAllClients();
            log.info("Fetched all clients: {}", clients);
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            log.error("Error occurred while fetching clients: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        try {
            var client = clientService.getClient(id);
            log.info("Fetched client with id {}: {}", id, client);
            return ResponseEntity.of(client);
        } catch (Exception e) {
            log.error("Error occurred while fetching client with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        try {
            clientService.updateClient(client);
            log.info("Client updated: {}", client);
            return ResponseEntity.ok("Client updated");
        } catch (Exception e) {
            log.error("Error occurred while updating client: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            log.info("Client deleted with id {}", id);
            return ResponseEntity.ok("Client deleted");
        } catch (Exception e) {
            log.error("Error occurred while deleting client with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }
}
