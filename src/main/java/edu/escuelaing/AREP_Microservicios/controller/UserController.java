/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.AREP_Microservicios.controller;

import edu.escuelaing.AREP_Microservicios.service.UserService;
import edu.escuelaing.AREP_Microservicios.utils.DTO.UserDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(){
        try {
            List<UserDTO> users =  userService.getAllUser();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not users");
        } 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@RequestParam Long id){
        try {
            UserDTO user = userService.getUser(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not found user");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?>  getUser(@RequestBody UserDTO user){
        try {
            UserDTO userR = userService.createUser(user);
            return ResponseEntity.ok().body(userR);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not created user");
        }
    }
    
}
