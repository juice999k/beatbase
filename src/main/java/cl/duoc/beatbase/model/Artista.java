package cl.duoc.beatbase.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nombre;

    private int edad;

    @NotBlank
    private String sexo;

    @NotNull
    private String genero;

    @OneToOne()
    @JoinColumn(name = "biografia_id")
    private Biografia biografia;

    @OneToMany(mappedBy = "artista", fetch = FetchType.EAGER)
    private List<Proyecto> proyectos;

    @JsonCreator
    public static Artista fromId(@JsonProperty("id") Integer id) {
    Artista a = new Artista();
    a.setId(id);
    return a;
}


}
