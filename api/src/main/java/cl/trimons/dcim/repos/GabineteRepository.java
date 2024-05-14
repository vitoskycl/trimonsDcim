package cl.trimons.dcim.repos;

import cl.trimons.dcim.domain.Datacenter;
import cl.trimons.dcim.domain.Gabinete;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GabineteRepository extends JpaRepository<Gabinete, Integer> {

    Gabinete findFirstByDatacenter(Datacenter datacenter);

}
