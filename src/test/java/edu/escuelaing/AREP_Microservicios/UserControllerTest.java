/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.AREP_Microservicios;

import edu.escuelaing.AREP_Microservicios.controller.UserController;
import edu.escuelaing.AREP_Microservicios.service.UserService;
import edu.escuelaing.AREP_Microservicios.utils.DTO.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnOk_WhenCreated() {
        UserDTO dto = new UserDTO(1L, "Juan", "1234");
        when(userService.createUser(dto)).thenReturn(dto);

        ResponseEntity<?> response = userController.createUser(dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAll_ShouldReturnUsers() {
        List<UserDTO> users = List.of(new UserDTO(1L, "a", "x"));
        when(userService.getAllUser()).thenReturn(users);

        ResponseEntity<?> response = userController.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(users, response.getBody());
    }

    @Test
    void getById_ShouldReturnUser() {
        UserDTO dto = new UserDTO(1L, "Juan", "pass");
        when(userService.getUser(1L)).thenReturn(dto);

        ResponseEntity<?> response = userController.getById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }
}

