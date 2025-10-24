package edu.escuelaing.AREP_Microservicios.utils.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StreamDTO {
    private Long id;
    private String name;
    // Lista de ids de posts asociados (simplificamos en el DTO)
    private List<Long> postIds;
}

