package cl.duoc.beatbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistaDTO {

    private String nombre;
    private String genero;
    private String biografia;
}