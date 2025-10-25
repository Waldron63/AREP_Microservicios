/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.AREP_Microservicios.service;

import edu.escuelaing.AREP_Microservicios.model.User;
import edu.escuelaing.AREP_Microservicios.repository.UserRepository;
import edu.escuelaing.AREP_Microservicios.utils.DTO.UserDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author juan
 */
@Service
public class UserService {

    
    private UserRepository userRepository;

    
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userReturn = new ArrayList<>();
        for (User u: users){
            userReturn.add(toDTO(u));
        }
        return userReturn;
        
    }

    
    public UserDTO getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return toDTO(user.get());
    }

    
    public UserDTO createUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        userRepository.save(user);
        return toDTO(user);
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getPassword());
    }

    public User toEntity(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword());
    }
    

    
}
