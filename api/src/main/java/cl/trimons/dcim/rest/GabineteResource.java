package cl.trimons.dcim.rest;

import cl.trimons.dcim.model.GabineteDTO;
import cl.trimons.dcim.service.GabineteService;
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
@RequestMapping(value = "/api/gabinetes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GabineteResource {

    private final GabineteService gabineteService;

    public GabineteResource(final GabineteService gabineteService) {
        this.gabineteService = gabineteService;
    }

    @GetMapping
    public ResponseEntity<List<GabineteDTO>> getAllGabinetes() {
        return ResponseEntity.ok(gabineteService.findAll());
    }

    @GetMapping("/{gabineteId}")
    public ResponseEntity<GabineteDTO> getGabinete(
            @PathVariable(name = "gabineteId") final Integer gabineteId) {
        return ResponseEntity.ok(gabineteService.get(gabineteId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createGabinete(
            @RequestBody @Valid final GabineteDTO gabineteDTO) {
        final Integer createdGabineteId = gabineteService.create(gabineteDTO);
        return new ResponseEntity<>(createdGabineteId, HttpStatus.CREATED);
    }

    @PutMapping("/{gabineteId}")
    public ResponseEntity<Integer> updateGabinete(
            @PathVariable(name = "gabineteId") final Integer gabineteId,
            @RequestBody @Valid final GabineteDTO gabineteDTO) {
        gabineteService.update(gabineteId, gabineteDTO);
        return ResponseEntity.ok(gabineteId);
    }

    @DeleteMapping("/{gabineteId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGabinete(
            @PathVariable(name = "gabineteId") final Integer gabineteId) {
        final ReferencedWarning referencedWarning = gabineteService.getReferencedWarning(gabineteId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        gabineteService.delete(gabineteId);
        return ResponseEntity.noContent().build();
    }

}
