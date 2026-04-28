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
        return biografiaRepository.findAll();
    }

    public Biografia saveBiografia(Biografia biografia) {
        return biografiaRepository.save(biografia);
    }

    public Biografia getBiografiaId(int id) {
        return biografiaRepository.findById(id).orElse(null);
    }

    public Biografia updateBiografia(Biografia biografia) {
        if (!biografiaRepository.existsById(biografia.getId())) {
            return null;
        }
        return biografiaRepository.save(biografia);
    }

    public void deleteBiografia(int id) {
        biografiaRepository.deleteById(id);
    }
}
