/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.escuelaing.AREP_Microservicios.repository;

import edu.escuelaing.AREP_Microservicios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author juan
 */
public interface UserRepository extends JpaRepository<User, Long>{
    
}
