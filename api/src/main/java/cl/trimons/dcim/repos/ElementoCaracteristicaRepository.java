package cl.trimons.dcim.repos;

import cl.trimons.dcim.domain.Caracteristica;
import cl.trimons.dcim.domain.Elemento;
import cl.trimons.dcim.domain.ElementoCaracteristica;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ElementoCaracteristicaRepository extends JpaRepository<ElementoCaracteristica, String> {

    ElementoCaracteristica findFirstByCaracteristica(Caracteristica caracteristica);

    ElementoCaracteristica findFirstByElemento(Elemento elemento);

    boolean existsByValorIgnoreCase(String valor);

}
