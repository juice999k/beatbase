package cl.duoc.beatbase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    private int codigo;
    private String mensaje;
    private String detalle;
}