package cl.trimons.dcim.rest;

import cl.trimons.dcim.model.CaracteristicaDTO;
import cl.trimons.dcim.service.CaracteristicaService;
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
@RequestMapping(value = "/api/caracteristicas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CaracteristicaResource {

    private final CaracteristicaService caracteristicaService;

    public CaracteristicaResource(final CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @GetMapping
    public ResponseEntity<List<CaracteristicaDTO>> getAllCaracteristicas() {
        return ResponseEntity.ok(caracteristicaService.findAll());
    }

    @GetMapping("/{caracteristicaId}")
    public ResponseEntity<CaracteristicaDTO> getCaracteristica(
            @PathVariable(name = "caracteristicaId") final Integer caracteristicaId) {
        return ResponseEntity.ok(caracteristicaService.get(caracteristicaId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createCaracteristica(
            @RequestBody @Valid final CaracteristicaDTO caracteristicaDTO) {
        final Integer createdCaracteristicaId = caracteristicaService.create(caracteristicaDTO);
        return new ResponseEntity<>(createdCaracteristicaId, HttpStatus.CREATED);
    }

    @PutMapping("/{caracteristicaId}")
    public ResponseEntity<Integer> updateCaracteristica(
            @PathVariable(name = "caracteristicaId") final Integer caracteristicaId,
            @RequestBody @Valid final CaracteristicaDTO caracteristicaDTO) {
        caracteristicaService.update(caracteristicaId, caracteristicaDTO);
        return ResponseEntity.ok(caracteristicaId);
    }

    @DeleteMapping("/{caracteristicaId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCaracteristica(
            @PathVariable(name = "caracteristicaId") final Integer caracteristicaId) {
        final ReferencedWarning referencedWarning = caracteristicaService.getReferencedWarning(caracteristicaId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        caracteristicaService.delete(caracteristicaId);
        return ResponseEntity.noContent().build();
    }

}
