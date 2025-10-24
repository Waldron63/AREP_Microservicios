package edu.escuelaing.AREP_Microservicios.utils.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostDTO {
    private Long id;
    private String message;
    private int likes;
    // Id del usuario asociado (User se implementará después)
    private Long userId;
}

