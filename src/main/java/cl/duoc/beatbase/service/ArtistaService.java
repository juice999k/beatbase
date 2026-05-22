package cl.duoc.beatbase.service;

import cl.duoc.beatbase.model.Artista;
import cl.duoc.beatbase.model.Biografia;
import cl.duoc.beatbase.repository.ArtistaRepository;
import cl.duoc.beatbase.repository.BiografiaRepository;
import cl.duoc.beatbase.dto.ArtistaProyectosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;
    @Autowired
    private BiografiaRepository biografiaRepository;

    public List<Artista> getArtistas() {
        System.out.println("[ArtistaService] Obteniendo todos los artistas");
        return artistaRepository.findAll();
    }

    public Artista saveArtista(Artista artista) {
    System.out.println("[ArtistaService] Guardando artista: " + artista.getNombre());
    if (artista.getBiografia() != null && artista.getBiografia().getId() != null) {
        Biografia bio = biografiaRepository.findById(artista.getBiografia().getId()).orElse(null);
        artista.setBiografia(bio);
    }
    return artistaRepository.save(artista);
}

    public Artista getArtistaId(int id) {
        System.out.println("[ArtistaService] Buscando artista con id: " + id);
        return artistaRepository.findById(id).orElse(null);
    }

    public Artista updateArtista(Artista artista) {
        System.out.println("[ArtistaService] Actualizando artista con id: " + artista.getId());
        if (!artistaRepository.existsById(artista.getId())) {
        System.out.println("[ArtistaService] Artista no encontrado con id: " + artista.getId());
        return null;
    }
    return artistaRepository.save(artista);
    }

    public void deleteArtista(int id) {
        System.out.println("[ArtistaService] Eliminando artista con id: " + id);
        artistaRepository.deleteById(id);
    }

public List<ArtistaProyectosDTO> getArtistasConProyectos() {
    return artistaRepository.findAll().stream()
            .map(a -> new ArtistaProyectosDTO(
                    a.getNombre(), 
                    a.getProyectos().stream()
                        .map(p -> p.getTitulo())
                        .toList()
            ))
            .toList(); 
}
}
