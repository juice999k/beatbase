package cl.duoc.beatbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistaDTO {

    private Integer id;
    private String nombre;
    private int edad ;
    private String sexo;
    private String genero;
    private biografia Biografia;
}