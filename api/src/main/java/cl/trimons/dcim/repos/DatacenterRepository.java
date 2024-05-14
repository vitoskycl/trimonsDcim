package cl.trimons.dcim.repos;

import cl.trimons.dcim.domain.Datacenter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DatacenterRepository extends JpaRepository<Datacenter, Integer> {
}
