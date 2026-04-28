package cl.duoc.beatbase.service;

import cl.duoc.beatbase.model.Proyecto;
import cl.duoc.beatbase.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> getProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto saveProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Proyecto getProyectoId(int id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    public Proyecto updateProyecto(Proyecto proyecto) {
        if (!proyectoRepository.existsById(proyecto.getId())) {
            return null;
        }
        return proyectoRepository.save(proyecto);
    }

    public void deleteProyecto(int id) {
        proyectoRepository.deleteById(id);
    }
}
