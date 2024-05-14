package cl.trimons.dcim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Gabinete {

    @Id
    @Column(nullable = false, updatable = false, name="gabinete_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gabineteId;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(columnDefinition = "longtext")
    private String descripcion;

    @Column(nullable = false)
    private Boolean vigente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datacenter_id", nullable = false)
    private Datacenter datacenter;

    @OneToMany(mappedBy = "gabinete")
    private Set<Elemento> gabineteElementos;

}
