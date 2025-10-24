package edu.escuelaing.AREP_Microservicios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private int likes;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

}