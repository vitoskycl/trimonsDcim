package cl.trimons.dcim.service;

import cl.trimons.dcim.domain.Categoria;
import cl.trimons.dcim.domain.Elemento;
import cl.trimons.dcim.model.CategoriaDTO;
import cl.trimons.dcim.repos.CategoriaRepository;
import cl.trimons.dcim.repos.ElementoRepository;
import cl.trimons.dcim.util.NotFoundException;
import cl.trimons.dcim.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ElementoRepository elementoRepository;

    public CategoriaService(final CategoriaRepository categoriaRepository,
            final ElementoRepository elementoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.elementoRepository = elementoRepository;
    }

    public List<CategoriaDTO> findAll() {
        final List<Categoria> categorias = categoriaRepository.findAll(Sort.by("categoriaId"));
        return categorias.stream()
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .toList();
    }

    public CategoriaDTO get(final Integer categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoriaDTO categoriaDTO) {
        final Categoria categoria = new Categoria();
        mapToEntity(categoriaDTO, categoria);
        return categoriaRepository.save(categoria).getCategoriaId();
    }

    public void update(final Integer categoriaId, final CategoriaDTO categoriaDTO) {
        final Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoriaDTO, categoria);
        categoriaRepository.save(categoria);
    }

    public void delete(final Integer categoriaId) {
        categoriaRepository.deleteById(categoriaId);
    }

    private CategoriaDTO mapToDTO(final Categoria categoria, final CategoriaDTO categoriaDTO) {
        categoriaDTO.setCategoriaId(categoria.getCategoriaId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setVigente(categoria.getVigente());
        return categoriaDTO;
    }

    private Categoria mapToEntity(final CategoriaDTO categoriaDTO, final Categoria categoria) {
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setVigente(categoriaDTO.getVigente());
        return categoria;
    }

    public ReferencedWarning getReferencedWarning(final Integer categoriaId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(NotFoundException::new);
        final Elemento categoriaElemento = elementoRepository.findFirstByCategoria(categoria);
        if (categoriaElemento != null) {
            referencedWarning.setKey("categoria.elemento.categoria.referenced");
            referencedWarning.addParam(categoriaElemento.getElementoId());
            return referencedWarning;
        }
        return null;
    }

}
