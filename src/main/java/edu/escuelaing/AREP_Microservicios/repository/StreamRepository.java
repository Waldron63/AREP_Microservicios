package edu.escuelaing.AREP_Microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.escuelaing.AREP_Microservicios.model.Stream;

public interface StreamRepository extends JpaRepository<Stream, Long> {
}

