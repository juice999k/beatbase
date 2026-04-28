package cl.duoc.beatbase.controller;

import cl.duoc.beatbase.model.Proyecto;
import cl.duoc.beatbase.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> getProyectos() {
        List<Proyecto> proyectos = proyectoService.getProyectos();
        return ResponseEntity.ok(proyectos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable int id) {
        Proyecto proyecto = proyectoService.getProyectoId(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proyecto);
    }

    @PostMapping
    public ResponseEntity<Proyecto> createProyecto(@Valid @RequestBody Proyecto proyecto) {
        Proyecto nuevo = proyectoService.saveProyecto(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable int id, @Valid @RequestBody Proyecto proyecto) {
        proyecto.setId(id);
        Proyecto actualizado = proyectoService.updateProyecto(proyecto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable int id) {
        Proyecto proyecto = proyectoService.getProyectoId(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }
        proyectoService.deleteProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
