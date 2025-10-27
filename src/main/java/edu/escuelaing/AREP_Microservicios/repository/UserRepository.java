/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.escuelaing.AREP_Microservicios.repository;

import edu.escuelaing.AREP_Microservicios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author juan
 */
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByName(String username);
}
