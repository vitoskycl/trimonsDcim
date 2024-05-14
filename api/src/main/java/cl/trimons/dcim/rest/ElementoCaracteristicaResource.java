package cl.trimons.dcim.rest;

import cl.trimons.dcim.model.ElementoCaracteristicaDTO;
import cl.trimons.dcim.service.ElementoCaracteristicaService;
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
@RequestMapping(value = "/api/elementoCaracteristicas", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementoCaracteristicaResource {

    private final ElementoCaracteristicaService elementoCaracteristicaService;

    public ElementoCaracteristicaResource(
            final ElementoCaracteristicaService elementoCaracteristicaService) {
        this.elementoCaracteristicaService = elementoCaracteristicaService;
    }

    @GetMapping
    public ResponseEntity<List<ElementoCaracteristicaDTO>> getAllElementoCaracteristicas() {
        return ResponseEntity.ok(elementoCaracteristicaService.findAll());
    }

    @GetMapping("/{valor}")
    public ResponseEntity<ElementoCaracteristicaDTO> getElementoCaracteristica(
            @PathVariable(name = "valor") final String valor) {
        return ResponseEntity.ok(elementoCaracteristicaService.get(valor));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createElementoCaracteristica(
            @RequestBody @Valid final ElementoCaracteristicaDTO elementoCaracteristicaDTO) {
        final String createdValor = elementoCaracteristicaService.create(elementoCaracteristicaDTO);
        return new ResponseEntity<>('"' + createdValor + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{valor}")
    public ResponseEntity<String> updateElementoCaracteristica(
            @PathVariable(name = "valor") final String valor,
            @RequestBody @Valid final ElementoCaracteristicaDTO elementoCaracteristicaDTO) {
        elementoCaracteristicaService.update(valor, elementoCaracteristicaDTO);
        return ResponseEntity.ok('"' + valor + '"');
    }

    @DeleteMapping("/{valor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteElementoCaracteristica(
            @PathVariable(name = "valor") final String valor) {
        elementoCaracteristicaService.delete(valor);
        return ResponseEntity.noContent().build();
    }

}
