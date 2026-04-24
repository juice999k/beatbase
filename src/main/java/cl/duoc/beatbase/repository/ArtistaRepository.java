package cl.duoc.beatbase.repository;
import cl.duoc.beatbase.repository.model.artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Integer> {
}