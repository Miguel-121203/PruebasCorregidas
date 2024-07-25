package com.example.Final.Controller;

import com.example.Final.Entity.Product;
import com.example.Final.IService.IProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  private IProductService service;

  @GetMapping()
  public ResponseEntity<List<Product>> findAll() {
    List<Product> products = service.findAll();
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> findById(@PathVariable Long id) {
    return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping()
  public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
    Product newProduct = service.save(product);
    return ResponseEntity.ok(newProduct);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product productoDetails) {
    Product updatedProducto = service.update(productoDetails, id);
    return ResponseEntity.ok(updatedProducto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/activar/{id}")
  public ResponseEntity<String> activateProducto(@PathVariable Long id) {
    try {
      service.activateProduct(id);
      return ResponseEntity.ok("Producto activado exitosamente");
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }
  }

  @PutMapping("/desactivar/{id}")
  public ResponseEntity<String> desactivateProducto(@PathVariable Long id) {
    try {
      service.desactivateProduct(id);
      return ResponseEntity.ok("Producto desactivado exitosamente");
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }
  }

  @PutMapping("/cancelar/{id}")
  public ResponseEntity<String> cancelateProducto(@PathVariable Long id) {
    try {
      service.cancelateProduct(id);
      return ResponseEntity.ok("Producto cancelado exitosamente");
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
