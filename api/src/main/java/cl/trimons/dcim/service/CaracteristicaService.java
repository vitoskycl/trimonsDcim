package cl.trimons.dcim.service;

import cl.trimons.dcim.domain.Caracteristica;
import cl.trimons.dcim.domain.ElementoCaracteristica;
import cl.trimons.dcim.model.CaracteristicaDTO;
import cl.trimons.dcim.repos.CaracteristicaRepository;
import cl.trimons.dcim.repos.ElementoCaracteristicaRepository;
import cl.trimons.dcim.util.NotFoundException;
import cl.trimons.dcim.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CaracteristicaService {

    private final CaracteristicaRepository caracteristicaRepository;
    private final ElementoCaracteristicaRepository elementoCaracteristicaRepository;

    public CaracteristicaService(final CaracteristicaRepository caracteristicaRepository,
            final ElementoCaracteristicaRepository elementoCaracteristicaRepository) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.elementoCaracteristicaRepository = elementoCaracteristicaRepository;
    }

    public List<CaracteristicaDTO> findAll() {
        final List<Caracteristica> caracteristicas = caracteristicaRepository.findAll(Sort.by("caracteristicaId"));
        return caracteristicas.stream()
                .map(caracteristica -> mapToDTO(caracteristica, new CaracteristicaDTO()))
                .toList();
    }

    public CaracteristicaDTO get(final Integer caracteristicaId) {
        return caracteristicaRepository.findById(caracteristicaId)
                .map(caracteristica -> mapToDTO(caracteristica, new CaracteristicaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CaracteristicaDTO caracteristicaDTO) {
        final Caracteristica caracteristica = new Caracteristica();
        mapToEntity(caracteristicaDTO, caracteristica);
        return caracteristicaRepository.save(caracteristica).getCaracteristicaId();
    }

    public void update(final Integer caracteristicaId, final CaracteristicaDTO caracteristicaDTO) {
        final Caracteristica caracteristica = caracteristicaRepository.findById(caracteristicaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(caracteristicaDTO, caracteristica);
        caracteristicaRepository.save(caracteristica);
    }

    public void delete(final Integer caracteristicaId) {
        caracteristicaRepository.deleteById(caracteristicaId);
    }

    private CaracteristicaDTO mapToDTO(final Caracteristica caracteristica,
            final CaracteristicaDTO caracteristicaDTO) {
        caracteristicaDTO.setCaracteristicaId(caracteristica.getCaracteristicaId());
        caracteristicaDTO.setNombre(caracteristica.getNombre());
        caracteristicaDTO.setVigente(caracteristica.getVigente());
        return caracteristicaDTO;
    }

    private Caracteristica mapToEntity(final CaracteristicaDTO caracteristicaDTO,
            final Caracteristica caracteristica) {
        caracteristica.setNombre(caracteristicaDTO.getNombre());
        caracteristica.setVigente(caracteristicaDTO.getVigente());
        return caracteristica;
    }

    public ReferencedWarning getReferencedWarning(final Integer caracteristicaId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Caracteristica caracteristica = caracteristicaRepository.findById(caracteristicaId)
                .orElseThrow(NotFoundException::new);
        final ElementoCaracteristica caracteristicaElementoCaracteristica = elementoCaracteristicaRepository.findFirstByCaracteristica(caracteristica);
        if (caracteristicaElementoCaracteristica != null) {
            referencedWarning.setKey("caracteristica.elementoCaracteristica.caracteristica.referenced");
            referencedWarning.addParam(caracteristicaElementoCaracteristica.getValor());
            return referencedWarning;
        }
        return null;
    }

}
