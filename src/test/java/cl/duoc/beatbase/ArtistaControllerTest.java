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
    @Test
    void buscarArtista_retorna200_cuandoExiste() {
 
        Artista artista = new Artista();
        artista.setId(1);
        artista.setNombre("Duki");
        artista.setEdad(27);
        artista.setSexo("Masculino");
        artista.setGenero("Trap");
 
        when(artistaService.getArtistaId(1)).thenReturn(artista);
 
        var respuesta = artistaController.getArtistaById(1);
 
        assertNotNull(respuesta);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
 
        var body = respuesta.getBody();
        assertNotNull(body);
        assertEquals("Duki", body.getNombre());
        assertEquals(1, body.getId());
    }
 

    @Test
    void editarArtista_retorna200_cuandoSeActualizaCorrectamente() {
 
        Artista artistaActualizado = new Artista();
        artistaActualizado.setId(1);
        artistaActualizado.setNombre("Duki");
        artistaActualizado.setEdad(28);
        artistaActualizado.setSexo("Masculino");
        artistaActualizado.setGenero("Rap");
 
        when(artistaService.updateArtista(artistaActualizado)).thenReturn(artistaActualizado);
 
        var respuesta = artistaController.updateArtista(1, artistaActualizado);
 
        assertNotNull(respuesta);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
 
        var body = respuesta.getBody();
        assertNotNull(body);
        assertEquals("Rap", body.getGenero());
        assertEquals(28, body.getEdad());
    }
 
    @Test
    void eliminarArtista_retorna204_cuandoExiste() {
 
        Artista artista = new Artista();
        artista.setId(1);
        artista.setNombre("Duki");
        artista.setEdad(27);
        artista.setSexo("Masculino");
        artista.setGenero("Trap");
 
        when(artistaService.getArtistaId(1)).thenReturn(artista);
 
        var respuesta = artistaController.deleteArtista(1);
 
        assertNotNull(respuesta);
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
    }
}