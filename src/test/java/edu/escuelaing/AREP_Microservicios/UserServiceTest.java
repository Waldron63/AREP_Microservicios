/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.AREP_Microservicios;


import edu.escuelaing.AREP_Microservicios.model.User;
import edu.escuelaing.AREP_Microservicios.repository.UserRepository;
import edu.escuelaing.AREP_Microservicios.service.UserService;
import edu.escuelaing.AREP_Microservicios.utils.DTO.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "Juan", "1234");
    }

    @Test
    void getAllUser_ShouldReturnUserDTOList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> result = userService.getAllUser();

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getUsername());
    }

    @Test
    void getUser_ShouldReturnUserDTO_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO dto = userService.getUser(1L);

        assertEquals("Juan", dto.getUsername());
    }

    @Test
    void createUser_ShouldSaveAndReturnDTO() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO dto = new UserDTO(1L, "Juan", "1234");
        UserDTO result = userService.createUser(dto);

        assertEquals("Juan", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }
}

