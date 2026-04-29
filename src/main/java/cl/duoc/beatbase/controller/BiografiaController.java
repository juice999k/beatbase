package cl.duoc.beatbase.controller;

import cl.duoc.beatbase.model.Biografia;
import cl.duoc.beatbase.service.BiografiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/biografias")
public class BiografiaController {

    @Autowired
    private BiografiaService biografiaService;

    @GetMapping
    public ResponseEntity<List<Biografia>> getBiografias() {
        return ResponseEntity.ok(biografiaService.getBiografias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biografia> getBiografiaById(@PathVariable int id) {
        Biografia bio = biografiaService.getBiografiaId(id);
        if (bio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bio);
    }

    @PostMapping
    public ResponseEntity<Biografia> createBiografia(@Valid @RequestBody Biografia biografia) {
        Biografia nueva = biografiaService.saveBiografia(biografia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Biografia> updateBiografia(@PathVariable int id, @Valid @RequestBody Biografia biografia) {
        biografia.setId(id);
        Biografia actualizada = biografiaService.updateBiografia(biografia);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBiografia(@PathVariable int id) {
        if (biografiaService.getBiografiaId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        biografiaService.deleteBiografia(id);
        return ResponseEntity.noContent().build();
    }
}