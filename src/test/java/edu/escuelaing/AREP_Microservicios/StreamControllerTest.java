/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.AREP_Microservicios;

import edu.escuelaing.AREP_Microservicios.controller.StreamController;
import edu.escuelaing.AREP_Microservicios.service.StreamService;
import edu.escuelaing.AREP_Microservicios.utils.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StreamControllerTest {

    @Mock
    private StreamService streamService;

    @InjectMocks
    private StreamController streamController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStream_ShouldReturnCreatedStream() {
        StreamDTO dto = new StreamDTO();
        when(streamService.createStream(dto)).thenReturn(dto);

        ResponseEntity<StreamDTO> response = streamController.createStream(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAll_ShouldReturnStreams() {
        List<StreamDTO> streams = List.of(new StreamDTO(), new StreamDTO());
        when(streamService.getAllStreams()).thenReturn(streams);

        ResponseEntity<List<StreamDTO>> response = streamController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(streams, response.getBody());
    }

    @Test
    void getById_ShouldReturnStream_WhenFound() {
        StreamDTO dto = new StreamDTO();
        when(streamService.getStreamById(1L)).thenReturn(Optional.of(dto));

        ResponseEntity<StreamDTO> response = streamController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }



    @Test
    void delete_ShouldReturnNoContent_WhenDeleted() {
        when(streamService.deleteStream(1L)).thenReturn(true);

        ResponseEntity<Void> response = streamController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

    
