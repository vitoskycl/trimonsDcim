package cl.trimons.dcim.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CaracteristicaDTO {

    private Integer caracteristicaId;

    @NotNull
    @Size(max = 45)
    private String nombre;

    @NotNull
    private Boolean vigente;

}
