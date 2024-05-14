package cl.trimons.dcim;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cl.trimons.dcim.model.DatacenterDTO;
import cl.trimons.dcim.service.DatacenterService;
import jakarta.annotation.PostConstruct;


@SpringBootApplication
@EnableConfigurationProperties
public class DcimApplication {

    @Autowired
    DatacenterService datacenterService;

    @Autowired
    DatacenterDTO datacenterDTO;
    
    @PostConstruct
    public void init(){
        List<DatacenterDTO> listado = datacenterService.findAll();
        if (listado!=null && listado.size()==0){
            DatacenterDTO ejemplo = new DatacenterDTO();
            ejemplo.setDireccion("Direccion");
            ejemplo.setNombre("Nombre");
            ejemplo.setVigente(false);
            datacenterService.create(ejemplo);
        }
    }

    public static void main(final String[] args) {
        SpringApplication.run(DcimApplication.class, args);
    }

}
