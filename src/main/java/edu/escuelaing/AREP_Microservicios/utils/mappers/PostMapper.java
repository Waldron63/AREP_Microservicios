package edu.escuelaing.AREP_Microservicios.utils.mappers;

import edu.escuelaing.AREP_Microservicios.repository.UserRepository;
import edu.escuelaing.AREP_Microservicios.utils.DTO.PostDTO;
import edu.escuelaing.AREP_Microservicios.model.Post;
import edu.escuelaing.AREP_Microservicios.model.User;
import edu.escuelaing.AREP_Microservicios.utils.DTO.StreamDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostMapper {

    public PostDTO toDTO(Post post) {
        if (post == null) return null;
        return PostDTO.builder()
                .id(post.getId())
                .message(post.getMessage())
                .likes(post.getLikes())
                .userId(post.getUser() != null ? post.getUser().getId() : null)
                .build();
    }

    public Post toEntity(PostDTO dto) {
        if (dto == null) return null;
        Post.PostBuilder builder = Post.builder()
                .id(dto.getId())
                .message(dto.getMessage())
                .likes(dto.getLikes())
                .user(null);
        return builder.build();
    }
}
