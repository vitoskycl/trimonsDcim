package cl.trimons.dcim.service;

import cl.trimons.dcim.domain.Datacenter;
import cl.trimons.dcim.domain.Elemento;
import cl.trimons.dcim.domain.Gabinete;
import cl.trimons.dcim.model.GabineteDTO;
import cl.trimons.dcim.repos.DatacenterRepository;
import cl.trimons.dcim.repos.ElementoRepository;
import cl.trimons.dcim.repos.GabineteRepository;
import cl.trimons.dcim.util.NotFoundException;
import cl.trimons.dcim.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GabineteService {

    private final GabineteRepository gabineteRepository;
    private final DatacenterRepository datacenterRepository;
    private final ElementoRepository elementoRepository;

    public GabineteService(final GabineteRepository gabineteRepository,
            final DatacenterRepository datacenterRepository,
            final ElementoRepository elementoRepository) {
        this.gabineteRepository = gabineteRepository;
        this.datacenterRepository = datacenterRepository;
        this.elementoRepository = elementoRepository;
    }

    public List<GabineteDTO> findAll() {
        final List<Gabinete> gabinetes = gabineteRepository.findAll(Sort.by("gabineteId"));
        return gabinetes.stream()
                .map(gabinete -> mapToDTO(gabinete, new GabineteDTO()))
                .toList();
    }

    public GabineteDTO get(final Integer gabineteId) {
        return gabineteRepository.findById(gabineteId)
                .map(gabinete -> mapToDTO(gabinete, new GabineteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final GabineteDTO gabineteDTO) {
        final Gabinete gabinete = new Gabinete();
        mapToEntity(gabineteDTO, gabinete);
        return gabineteRepository.save(gabinete).getGabineteId();
    }

    public void update(final Integer gabineteId, final GabineteDTO gabineteDTO) {
        final Gabinete gabinete = gabineteRepository.findById(gabineteId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(gabineteDTO, gabinete);
        gabineteRepository.save(gabinete);
    }

    public void delete(final Integer gabineteId) {
        gabineteRepository.deleteById(gabineteId);
    }

    private GabineteDTO mapToDTO(final Gabinete gabinete, final GabineteDTO gabineteDTO) {
        gabineteDTO.setGabineteId(gabinete.getGabineteId());
        gabineteDTO.setNombre(gabinete.getNombre());
        gabineteDTO.setDescripcion(gabinete.getDescripcion());
        gabineteDTO.setVigente(gabinete.getVigente());
        gabineteDTO.setDatacenter(gabinete.getDatacenter() == null ? null : gabinete.getDatacenter().getDatacenterId());
        return gabineteDTO;
    }

    private Gabinete mapToEntity(final GabineteDTO gabineteDTO, final Gabinete gabinete) {
        gabinete.setNombre(gabineteDTO.getNombre());
        gabinete.setDescripcion(gabineteDTO.getDescripcion());
        gabinete.setVigente(gabineteDTO.getVigente());
        final Datacenter datacenter = gabineteDTO.getDatacenter() == null ? null : datacenterRepository.findById(gabineteDTO.getDatacenter())
                .orElseThrow(() -> new NotFoundException("datacenter not found"));
        gabinete.setDatacenter(datacenter);
        return gabinete;
    }

    public ReferencedWarning getReferencedWarning(final Integer gabineteId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Gabinete gabinete = gabineteRepository.findById(gabineteId)
                .orElseThrow(NotFoundException::new);
        final Elemento gabineteElemento = elementoRepository.findFirstByGabinete(gabinete);
        if (gabineteElemento != null) {
            referencedWarning.setKey("gabinete.elemento.gabinete.referenced");
            referencedWarning.addParam(gabineteElemento.getElementoId());
            return referencedWarning;
        }
        return null;
    }

}
