package cl.trimons.dcim.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ElementoCaracteristicaDTO {

    @Size(max = 45)
    @ElementoCaracteristicaValorValid
    private String valor;

    @NotNull
    private Integer caracteristica;

    @NotNull
    private Integer elemento;

}
