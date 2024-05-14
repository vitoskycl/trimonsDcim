package cl.trimons.dcim.rest;

import cl.trimons.dcim.model.DatacenterDTO;
import cl.trimons.dcim.service.DatacenterService;
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
@RequestMapping(value = "/api/datacenters", produces = MediaType.APPLICATION_JSON_VALUE)
public class DatacenterResource {

    private final DatacenterService datacenterService;

    public DatacenterResource(final DatacenterService datacenterService) {
        this.datacenterService = datacenterService;
    }

    @GetMapping
    public ResponseEntity<List<DatacenterDTO>> getAllDatacenters() {
        return ResponseEntity.ok(datacenterService.findAll());
    }

    @GetMapping("/{datacenterId}")
    public ResponseEntity<DatacenterDTO> getDatacenter(
            @PathVariable(name = "datacenterId") final Integer datacenterId) {
        return ResponseEntity.ok(datacenterService.get(datacenterId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDatacenter(
            @RequestBody @Valid final DatacenterDTO datacenterDTO) {
        final Integer createdDatacenterId = datacenterService.create(datacenterDTO);
        return new ResponseEntity<>(createdDatacenterId, HttpStatus.CREATED);
    }

    @PutMapping("/{datacenterId}")
    public ResponseEntity<Integer> updateDatacenter(
            @PathVariable(name = "datacenterId") final Integer datacenterId,
            @RequestBody @Valid final DatacenterDTO datacenterDTO) {
        datacenterService.update(datacenterId, datacenterDTO);
        return ResponseEntity.ok(datacenterId);
    }

    @DeleteMapping("/{datacenterId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDatacenter(
            @PathVariable(name = "datacenterId") final Integer datacenterId) {
        final ReferencedWarning referencedWarning = datacenterService.getReferencedWarning(datacenterId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        datacenterService.delete(datacenterId);
        return ResponseEntity.noContent().build();
    }

}
