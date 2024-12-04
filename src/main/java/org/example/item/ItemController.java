package org.example.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody Item item) {
        try {
            itemService.addItem(item);
            log.info("Item added: {}", item);
            return ResponseEntity.ok("Item added");
        } catch (Exception e) {
            log.error("Error occurred while adding item: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllItems() {
        try {
            var items = itemService.getAllItems();
            log.info("Fetched all items: {}", items);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            log.error("Error occurred while fetching items: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        try {
            var item = itemService.getItem(id);
            log.info("Fetched item with id {}: {}", id, item);
            return ResponseEntity.of(item);
        } catch (Exception e) {
            log.error("Error occurred while fetching item with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItem(@RequestBody Item item) {
        try {
            itemService.updateItem(item);
            log.info("Item updated: {}", item);
            return ResponseEntity.ok("Item updated");
        } catch (Exception e) {
            log.error("Error occurred while updating item: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            log.info("Item deleted with id {}", id);
            return ResponseEntity.ok("Item deleted");
        } catch (Exception e) {
            log.error("Error occurred while deleting item with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }
}
