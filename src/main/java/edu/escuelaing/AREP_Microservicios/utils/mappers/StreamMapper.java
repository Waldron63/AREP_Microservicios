package edu.escuelaing.AREP_Microservicios.utils.mappers;

import edu.escuelaing.AREP_Microservicios.model.Stream;
import edu.escuelaing.AREP_Microservicios.model.Post;
import edu.escuelaing.AREP_Microservicios.utils.DTO.StreamDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StreamMapper {

    public StreamDTO toDTO(Stream stream) {
        if (stream == null) return null;
        return StreamDTO.builder()
                .id(stream.getId())
                .name(stream.getName())
                .postIds(stream.getPosts() != null ? stream.getPosts().stream().map(Post::getId).collect(Collectors.toList()) : null)
                .build();
    }

    public Stream toEntity(StreamDTO dto) {
        if (dto == null) return null;
        Stream.StreamBuilder builder = Stream.builder()
                .id(dto.getId())
                .name(dto.getName());
        if (dto.getPostIds() != null) {
            List<Post> posts = dto.getPostIds().stream()
                    .map(id -> Post.builder().id(id).build())
                    .collect(Collectors.toList());
            builder.posts(posts);
        }
        return builder.build();
    }
}

