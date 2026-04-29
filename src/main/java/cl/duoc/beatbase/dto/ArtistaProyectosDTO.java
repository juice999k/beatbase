package cl.duoc.beatbase.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistaProyectosDTO {
    private String nombre;
    private List<String> proyectos;
}