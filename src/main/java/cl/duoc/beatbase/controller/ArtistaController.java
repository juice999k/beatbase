package cl.duoc.beatbase.controller;

import cl.duoc.beatbase.model.Artista;
import cl.duoc.beatbase.service.ArtistaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;


    @GetMapping
    public ResponseEntity<List<Artista>> getArtistas() {
        List<Artista> artistas = artistaService.getArtistas();
        return ResponseEntity.ok(artistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> getArtistaById(@PathVariable int id) {
        Artista artista = artistaService.getArtistaId(id);
        if (artista == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(artista);
    }


    @PostMapping
    public ResponseEntity<Artista> createArtista(@Valid @RequestBody Artista artista) {
        Artista nuevo = artistaService.saveArtista(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Artista> updateArtista(@PathVariable int id, @Valid @RequestBody Artista artista) {
        artista.setId(id);
        Artista actualizado = artistaService.updateArtista(artista);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtista(@PathVariable int id) {
        Artista artista = artistaService.getArtistaId(id);
        if (artista == null) {
            return ResponseEntity.notFound().build();
        }
        artistaService.deleteArtista(id);
        return ResponseEntity.noContent().build();
    }
    
    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping("/proyectos")
    public List<ArtistaProyectosDTO> listarArtistasConProyectos() {
        return artistaService.getArtistasConProyectos();
    }

}
