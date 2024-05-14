package cl.trimons.dcim.service;

import cl.trimons.dcim.domain.Categoria;
import cl.trimons.dcim.domain.Elemento;
import cl.trimons.dcim.domain.ElementoCaracteristica;
import cl.trimons.dcim.domain.Gabinete;
import cl.trimons.dcim.model.ElementoDTO;
import cl.trimons.dcim.repos.CategoriaRepository;
import cl.trimons.dcim.repos.ElementoCaracteristicaRepository;
import cl.trimons.dcim.repos.ElementoRepository;
import cl.trimons.dcim.repos.GabineteRepository;
import cl.trimons.dcim.util.NotFoundException;
import cl.trimons.dcim.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ElementoService {

    private final ElementoRepository elementoRepository;
    private final CategoriaRepository categoriaRepository;
    private final GabineteRepository gabineteRepository;
    private final ElementoCaracteristicaRepository elementoCaracteristicaRepository;

    public ElementoService(final ElementoRepository elementoRepository,
            final CategoriaRepository categoriaRepository,
            final GabineteRepository gabineteRepository,
            final ElementoCaracteristicaRepository elementoCaracteristicaRepository) {
        this.elementoRepository = elementoRepository;
        this.categoriaRepository = categoriaRepository;
        this.gabineteRepository = gabineteRepository;
        this.elementoCaracteristicaRepository = elementoCaracteristicaRepository;
    }

    public List<ElementoDTO> findAll() {
        final List<Elemento> elementoes = elementoRepository.findAll(Sort.by("elementoId"));
        return elementoes.stream()
                .map(elemento -> mapToDTO(elemento, new ElementoDTO()))
                .toList();
    }

    public ElementoDTO get(final Integer elementoId) {
        return elementoRepository.findById(elementoId)
                .map(elemento -> mapToDTO(elemento, new ElementoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ElementoDTO elementoDTO) {
        final Elemento elemento = new Elemento();
        mapToEntity(elementoDTO, elemento);
        return elementoRepository.save(elemento).getElementoId();
    }

    public void update(final Integer elementoId, final ElementoDTO elementoDTO) {
        final Elemento elemento = elementoRepository.findById(elementoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(elementoDTO, elemento);
        elementoRepository.save(elemento);
    }

    public void delete(final Integer elementoId) {
        elementoRepository.deleteById(elementoId);
    }

    private ElementoDTO mapToDTO(final Elemento elemento, final ElementoDTO elementoDTO) {
        elementoDTO.setElementoId(elemento.getElementoId());
        elementoDTO.setNombre(elemento.getNombre());
        elementoDTO.setDescripcion(elemento.getDescripcion());
        elementoDTO.setCategoria(elemento.getCategoria() == null ? null : elemento.getCategoria().getCategoriaId());
        elementoDTO.setGabinete(elemento.getGabinete() == null ? null : elemento.getGabinete().getGabineteId());
        return elementoDTO;
    }

    private Elemento mapToEntity(final ElementoDTO elementoDTO, final Elemento elemento) {
        elemento.setNombre(elementoDTO.getNombre());
        elemento.setDescripcion(elementoDTO.getDescripcion());
        final Categoria categoria = elementoDTO.getCategoria() == null ? null : categoriaRepository.findById(elementoDTO.getCategoria())
                .orElseThrow(() -> new NotFoundException("categoria not found"));
        elemento.setCategoria(categoria);
        final Gabinete gabinete = elementoDTO.getGabinete() == null ? null : gabineteRepository.findById(elementoDTO.getGabinete())
                .orElseThrow(() -> new NotFoundException("gabinete not found"));
        elemento.setGabinete(gabinete);
        return elemento;
    }

    public ReferencedWarning getReferencedWarning(final Integer elementoId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Elemento elemento = elementoRepository.findById(elementoId)
                .orElseThrow(NotFoundException::new);
        final ElementoCaracteristica elementoElementoCaracteristica = elementoCaracteristicaRepository.findFirstByElemento(elemento);
        if (elementoElementoCaracteristica != null) {
            referencedWarning.setKey("elemento.elementoCaracteristica.elemento.referenced");
            referencedWarning.addParam(elementoElementoCaracteristica.getValor());
            return referencedWarning;
        }
        return null;
    }

}
