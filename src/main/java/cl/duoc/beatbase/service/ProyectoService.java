package cl.duoc.beatbase.service;

import cl.duoc.beatbase.model.Artista;
import cl.duoc.beatbase.model.Proyecto;
import cl.duoc.beatbase.repository.ArtistaRepository;
import cl.duoc.beatbase.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Proyecto> getProyectos() {
        System.out.println("[ProyectoService] Obteniendo todos los proyectos");
        return proyectoRepository.findAll();
    }

  public Proyecto saveProyecto(Proyecto proyecto) {
    System.out.println("[ProyectoService] Guardando un nuevo proyecto");
    if (proyecto.getArtistaId() != null) {
        Artista artista = artistaRepository.findById(proyecto.getArtistaId()).orElse(null);
        proyecto.setArtista(artista);
    }
    return proyectoRepository.save(proyecto);
}

    public Proyecto getProyectoId(int id) {
        System.out.println("[ProyectoService] Buscando proyecto con id: " + id);
        return proyectoRepository.findById(id).orElse(null);
    }

    public Proyecto updateProyecto(Proyecto proyecto) {
        System.out.println("[ProyectoService] Intentando actualizar proyecto con id: " + proyecto.getId());
        
        if (!proyectoRepository.existsById(proyecto.getId())) {
            System.out.println("[ProyectoService] Error: No se encontro el proyecto con id: " + proyecto.getId());
            return null;
        }
        System.out.println("[ProyectoService] Proyecto actualizado exitosamente");
        return proyectoRepository.save(proyecto);
    }

    public void deleteProyecto(int id) {
        System.out.println("[ProyectoService] Eliminando proyecto con id: " + id);
        proyectoRepository.deleteById(id);
    }
}