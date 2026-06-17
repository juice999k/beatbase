package cl.duoc.beatbase;

import cl.duoc.beatbase.controller.ArtistaController;
import cl.duoc.beatbase.model.Artista;
import cl.duoc.beatbase.service.ArtistaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistaControllerTest {

    @Mock
    private ArtistaService artistaService;

    @InjectMocks
    private ArtistaController artistaController;

    @Test
    void crearArtista_retorna201_cuandoDatosSonValidos() {

        
        Artista artista = new Artista();
        artista.setId(1);
        artista.setNombre("Duki");
        artista.setEdad(27);
        artista.setSexo("Masculino");
        artista.setGenero("Trap");

        
        when(artistaService.saveArtista(artista))
                .thenReturn(artista);

        
        var respuesta = artistaController.createArtista(artista);

        
        assertNotNull(respuesta);

        
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());

       
        var body = respuesta.getBody();
        assertNotNull(body);

        assertEquals("Duki", body.getNombre());
        assertEquals("Trap", body.getGenero());
    }
}