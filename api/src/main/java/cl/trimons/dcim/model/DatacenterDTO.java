package cl.trimons.dcim.model;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class DatacenterDTO {

    private Integer datacenterId;

    @NotNull
    @Size(max = 45)
    private String nombre;

    @NotNull
    @Size(max = 500)
    private String direccion;

    @NotNull
    private Boolean vigente;

}
