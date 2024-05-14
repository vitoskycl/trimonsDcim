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
public class Datacenter {

    @Id
    @Column(nullable = false, updatable = false, name="datacenter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer datacenterId;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String direccion;

    @Column(nullable = false)
    private Boolean vigente;

    @OneToMany(mappedBy = "datacenter")
    private Set<Gabinete> datacenterGabinetes;

}
