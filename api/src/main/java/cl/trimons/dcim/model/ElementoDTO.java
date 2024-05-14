package cl.trimons.dcim.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ElementoDTO {

    private Integer elementoId;

    @NotNull
    @Size(max = 45)
    private String nombre;

    private String descripcion;

    @NotNull
    private Integer categoria;

    @NotNull
    private Integer gabinete;

}
