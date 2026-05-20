package cl.duoc.beatbase.service;

import cl.duoc.beatbase.model.Biografia;
import cl.duoc.beatbase.repository.BiografiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BiografiaService {

    @Autowired
    private BiografiaRepository biografiaRepository;

    public List<Biografia> getBiografias() {
        System.out.println("[BiografiaService] Obteniendo todas las biografias");
        return biografiaRepository.findAll();
    }

    public Biografia saveBiografia(Biografia biografia) {
        System.out.println("[BiografiaService] Guardando la biografia");
        return biografiaRepository.save(biografia);
    }

    public Biografia getBiografiaId(int id) {
        System.out.println("[BiografiaService] Buscando biografia con id: " + id);
        return biografiaRepository.findById(id).orElse(null);
    }

    public Biografia updateBiografia(Biografia biografia) {
        System.out.println("[BiografiaService] Intentando actualizar biografia con id: " + biografia.getId());
        if (!biografiaRepository.existsById(biografia.getId())) {
            System.out.println("[BiografiaService] Error: No se encontro la biografia con id: " + biografia.getId());
            return null;
        }
        System.out.println("[BiografiaService] Biografia actualizada exitosamente");
        return biografiaRepository.save(biografia);
    }

    public void deleteBiografia(int id) {
        System.out.println("[BiografiaService] Eliminando biografia con id: " + id);
        biografiaRepository.deleteById(id);
    }
}
