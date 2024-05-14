package cl.trimons.dcim.repos;

import cl.trimons.dcim.domain.Categoria;
import cl.trimons.dcim.domain.Elemento;
import cl.trimons.dcim.domain.Gabinete;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ElementoRepository extends JpaRepository<Elemento, Integer> {

    Elemento findFirstByCategoria(Categoria categoria);

    Elemento findFirstByGabinete(Gabinete gabinete);

}
