package cl.duoc.beatbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDTO {

    private String titulo;
    private String tipo; 
    private int anioLanzamiento;
}