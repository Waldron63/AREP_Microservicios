package edu.escuelaing.AREP_Microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.escuelaing.AREP_Microservicios.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}

