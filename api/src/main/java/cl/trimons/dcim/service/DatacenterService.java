package cl.trimons.dcim.service;

import cl.trimons.dcim.domain.Datacenter;
import cl.trimons.dcim.domain.Gabinete;
import cl.trimons.dcim.model.DatacenterDTO;
import cl.trimons.dcim.repos.DatacenterRepository;
import cl.trimons.dcim.repos.GabineteRepository;
import cl.trimons.dcim.util.NotFoundException;
import cl.trimons.dcim.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DatacenterService {

    private final DatacenterRepository datacenterRepository;
    private final GabineteRepository gabineteRepository;

    public DatacenterService(final DatacenterRepository datacenterRepository,
            final GabineteRepository gabineteRepository) {
        this.datacenterRepository = datacenterRepository;
        this.gabineteRepository = gabineteRepository;
    }

    public List<DatacenterDTO> findAll() {
        final List<Datacenter> datacenters = datacenterRepository.findAll(Sort.by("datacenterId"));
        return datacenters.stream()
                .map(datacenter -> mapToDTO(datacenter, new DatacenterDTO()))
                .toList();
    }

    public DatacenterDTO get(final Integer datacenterId) {
        return datacenterRepository.findById(datacenterId)
                .map(datacenter -> mapToDTO(datacenter, new DatacenterDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DatacenterDTO datacenterDTO) {
        final Datacenter datacenter = new Datacenter();
        mapToEntity(datacenterDTO, datacenter);
        return datacenterRepository.save(datacenter).getDatacenterId();
    }

    public void update(final Integer datacenterId, final DatacenterDTO datacenterDTO) {
        final Datacenter datacenter = datacenterRepository.findById(datacenterId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(datacenterDTO, datacenter);
        datacenterRepository.save(datacenter);
    }

    public void delete(final Integer datacenterId) {
        datacenterRepository.deleteById(datacenterId);
    }

    private DatacenterDTO mapToDTO(final Datacenter datacenter, final DatacenterDTO datacenterDTO) {
        datacenterDTO.setDatacenterId(datacenter.getDatacenterId());
        datacenterDTO.setNombre(datacenter.getNombre());
        datacenterDTO.setDireccion(datacenter.getDireccion());
        datacenterDTO.setVigente(datacenter.getVigente());
        return datacenterDTO;
    }

    private Datacenter mapToEntity(final DatacenterDTO datacenterDTO, final Datacenter datacenter) {
        datacenter.setNombre(datacenterDTO.getNombre());
        datacenter.setDireccion(datacenterDTO.getDireccion());
        datacenter.setVigente(datacenterDTO.getVigente());
        return datacenter;
    }

    public ReferencedWarning getReferencedWarning(final Integer datacenterId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Datacenter datacenter = datacenterRepository.findById(datacenterId)
                .orElseThrow(NotFoundException::new);
        final Gabinete datacenterGabinete = gabineteRepository.findFirstByDatacenter(datacenter);
        if (datacenterGabinete != null) {
            referencedWarning.setKey("datacenter.gabinete.datacenter.referenced");
            referencedWarning.addParam(datacenterGabinete.getGabineteId());
            return referencedWarning;
        }
        return null;
    }

}
