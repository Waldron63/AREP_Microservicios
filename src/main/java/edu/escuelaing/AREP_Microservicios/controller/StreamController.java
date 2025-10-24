package edu.escuelaing.AREP_Microservicios.controller;

import edu.escuelaing.AREP_Microservicios.service.StreamService;
import edu.escuelaing.AREP_Microservicios.utils.DTO.StreamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/streams")
public class StreamController {

    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @PostMapping
    public ResponseEntity<StreamDTO> createStream(@RequestBody StreamDTO dto) {
        StreamDTO created = streamService.createStream(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StreamDTO>> getAll() {
        return ResponseEntity.ok(streamService.getAllStreams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamDTO> getById(@PathVariable Long id) {
        return streamService.getStreamById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamDTO> update(@PathVariable Long id, @RequestBody StreamDTO dto) {
        return streamService.updateStream(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = streamService.deleteStream(id);
        if (deleted) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}

