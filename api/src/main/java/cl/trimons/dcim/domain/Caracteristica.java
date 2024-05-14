package cl.trimons.dcim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Caracteristica {

    @Id
    @Column(nullable = false, updatable = false, name="caracteristica_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caracteristicaId;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(nullable = false)
    private Boolean vigente;

    @OneToMany(mappedBy = "caracteristica")
    private Set<ElementoCaracteristica> caracteristicaElementoCaracteristicas;

}
