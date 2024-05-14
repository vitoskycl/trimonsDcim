package cl.trimons.dcim.rest;

import cl.trimons.dcim.model.ElementoDTO;
import cl.trimons.dcim.service.ElementoService;
import cl.trimons.dcim.util.ReferencedException;
import cl.trimons.dcim.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/elementos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementoResource {

    private final ElementoService elementoService;

    public ElementoResource(final ElementoService elementoService) {
        this.elementoService = elementoService;
    }

    @GetMapping
    public ResponseEntity<List<ElementoDTO>> getAllElementos() {
        return ResponseEntity.ok(elementoService.findAll());
    }

    @GetMapping("/{elementoId}")
    public ResponseEntity<ElementoDTO> getElemento(
            @PathVariable(name = "elementoId") final Integer elementoId) {
        return ResponseEntity.ok(elementoService.get(elementoId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createElemento(
            @RequestBody @Valid final ElementoDTO elementoDTO) {
        final Integer createdElementoId = elementoService.create(elementoDTO);
        return new ResponseEntity<>(createdElementoId, HttpStatus.CREATED);
    }

    @PutMapping("/{elementoId}")
    public ResponseEntity<Integer> updateElemento(
            @PathVariable(name = "elementoId") final Integer elementoId,
            @RequestBody @Valid final ElementoDTO elementoDTO) {
        elementoService.update(elementoId, elementoDTO);
        return ResponseEntity.ok(elementoId);
    }

    @DeleteMapping("/{elementoId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteElemento(
            @PathVariable(name = "elementoId") final Integer elementoId) {
        final ReferencedWarning referencedWarning = elementoService.getReferencedWarning(elementoId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        elementoService.delete(elementoId);
        return ResponseEntity.noContent().build();
    }

}
