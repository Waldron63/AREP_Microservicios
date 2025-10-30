package edu.escuelaing.AREP_Microservicios.utils.DTO;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostToStreamDTO {
    private Long postId;
    private Long streamId;
}
