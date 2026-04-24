package cl.duoc.beatbase.service;

import cl.duoc.beatbase.model.Artista;
import cl.duoc.beatbase.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Artista> getArtistas() {
        return artistaRepository.findAll();
    }

    public Artista saveArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    public Artista getArtistaId(int id) {
        return artistaRepository.findById(id).orElse(null);
    }

    public Artista updateArtista(Artista artista) {
        if (!artistaRepository.existsById(artista.getId())) {
            return null;
        }
        return artistaRepository.save(artista);
    }

    public void deleteArtista(int id) {
        artistaRepository.deleteById(id);
    }
}