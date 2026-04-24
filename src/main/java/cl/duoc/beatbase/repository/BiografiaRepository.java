package cl.duoc.beatbase.repository;
import cl.duoc.beatbase.model.Biografia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiografiaRepository extends JpaRepository<Biografia, Integer> {
}