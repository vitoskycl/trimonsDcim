package cl.trimons.dcim.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GabineteDTO {

    private Integer gabineteId;

    @NotNull
    @Size(max = 45)
    private String nombre;

    private String descripcion;

    @NotNull
    private Boolean vigente;

    @NotNull
    private Integer datacenter;

}
