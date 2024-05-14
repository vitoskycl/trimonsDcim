package cl.trimons.dcim.service;

import cl.trimons.dcim.domain.Caracteristica;
import cl.trimons.dcim.domain.Elemento;
import cl.trimons.dcim.domain.ElementoCaracteristica;
import cl.trimons.dcim.model.ElementoCaracteristicaDTO;
import cl.trimons.dcim.repos.CaracteristicaRepository;
import cl.trimons.dcim.repos.ElementoCaracteristicaRepository;
import cl.trimons.dcim.repos.ElementoRepository;
import cl.trimons.dcim.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ElementoCaracteristicaService {

    private final ElementoCaracteristicaRepository elementoCaracteristicaRepository;
    private final CaracteristicaRepository caracteristicaRepository;
    private final ElementoRepository elementoRepository;

    public ElementoCaracteristicaService(
            final ElementoCaracteristicaRepository elementoCaracteristicaRepository,
            final CaracteristicaRepository caracteristicaRepository,
            final ElementoRepository elementoRepository) {
        this.elementoCaracteristicaRepository = elementoCaracteristicaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.elementoRepository = elementoRepository;
    }

    public List<ElementoCaracteristicaDTO> findAll() {
        final List<ElementoCaracteristica> elementoCaracteristicas = elementoCaracteristicaRepository.findAll(Sort.by("valor"));
        return elementoCaracteristicas.stream()
                .map(elementoCaracteristica -> mapToDTO(elementoCaracteristica, new ElementoCaracteristicaDTO()))
                .toList();
    }

    public ElementoCaracteristicaDTO get(final String valor) {
        return elementoCaracteristicaRepository.findById(valor)
                .map(elementoCaracteristica -> mapToDTO(elementoCaracteristica, new ElementoCaracteristicaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ElementoCaracteristicaDTO elementoCaracteristicaDTO) {
        final ElementoCaracteristica elementoCaracteristica = new ElementoCaracteristica();
        mapToEntity(elementoCaracteristicaDTO, elementoCaracteristica);
        elementoCaracteristica.setValor(elementoCaracteristicaDTO.getValor());
        return elementoCaracteristicaRepository.save(elementoCaracteristica).getValor();
    }

    public void update(final String valor,
            final ElementoCaracteristicaDTO elementoCaracteristicaDTO) {
        final ElementoCaracteristica elementoCaracteristica = elementoCaracteristicaRepository.findById(valor)
                .orElseThrow(NotFoundException::new);
        mapToEntity(elementoCaracteristicaDTO, elementoCaracteristica);
        elementoCaracteristicaRepository.save(elementoCaracteristica);
    }

    public void delete(final String valor) {
        elementoCaracteristicaRepository.deleteById(valor);
    }

    private ElementoCaracteristicaDTO mapToDTO(final ElementoCaracteristica elementoCaracteristica,
            final ElementoCaracteristicaDTO elementoCaracteristicaDTO) {
        elementoCaracteristicaDTO.setValor(elementoCaracteristica.getValor());
        elementoCaracteristicaDTO.setCaracteristica(elementoCaracteristica.getCaracteristica() == null ? null : elementoCaracteristica.getCaracteristica().getCaracteristicaId());
        elementoCaracteristicaDTO.setElemento(elementoCaracteristica.getElemento() == null ? null : elementoCaracteristica.getElemento().getElementoId());
        return elementoCaracteristicaDTO;
    }

    private ElementoCaracteristica mapToEntity(
            final ElementoCaracteristicaDTO elementoCaracteristicaDTO,
            final ElementoCaracteristica elementoCaracteristica) {
        final Caracteristica caracteristica = elementoCaracteristicaDTO.getCaracteristica() == null ? null : caracteristicaRepository.findById(elementoCaracteristicaDTO.getCaracteristica())
                .orElseThrow(() -> new NotFoundException("caracteristica not found"));
        elementoCaracteristica.setCaracteristica(caracteristica);
        final Elemento elemento = elementoCaracteristicaDTO.getElemento() == null ? null : elementoRepository.findById(elementoCaracteristicaDTO.getElemento())
                .orElseThrow(() -> new NotFoundException("elemento not found"));
        elementoCaracteristica.setElemento(elemento);
        return elementoCaracteristica;
    }

    public boolean valorExists(final String valor) {
        return elementoCaracteristicaRepository.existsByValorIgnoreCase(valor);
    }

}
