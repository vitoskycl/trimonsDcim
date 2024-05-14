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
public class Elemento {

    @Id
    @Column(nullable = false, updatable = false, name="elemento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer elementoId;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(columnDefinition = "longtext")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gabinete_id", nullable = false)
    private Gabinete gabinete;

    @OneToMany(mappedBy = "elemento")
    private Set<ElementoCaracteristica> elementoElementoCaracteristicas;

}
