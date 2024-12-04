package org.example.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            log.info("Product added: {}", product);
            return ResponseEntity.ok("Product added");
        } catch (Exception e) {
            log.error("Error occurred while adding product: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        try {
            var products = productService.getAllProducts();
            log.info("Fetched all products: {}", products);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Error occurred while fetching products: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            var product = productService.getProduct(id);
            log.info("Fetched product with id {}: {}", id, product);
            return ResponseEntity.of(product);
        } catch (Exception e) {
            log.error("Error occurred while fetching product with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        try {
            productService.updateProduct(product);
            log.info("Product updated: {}", product);
            return ResponseEntity.ok("Product updated");
        } catch (Exception e) {
            log.error("Error occurred while updating product: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            log.info("Product deleted with id {}", id);
            return ResponseEntity.ok("Product deleted");
        } catch (Exception e) {
            log.error("Error occurred while deleting product with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error occurred while handling");
        }
    }
}
