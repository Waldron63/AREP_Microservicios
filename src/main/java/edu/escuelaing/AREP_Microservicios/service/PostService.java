package edu.escuelaing.AREP_Microservicios.service;

import edu.escuelaing.AREP_Microservicios.utils.DTO.PostDTO;
import edu.escuelaing.AREP_Microservicios.model.Post;
import edu.escuelaing.AREP_Microservicios.model.User;
import edu.escuelaing.AREP_Microservicios.repository.PostRepository;
import edu.escuelaing.AREP_Microservicios.utils.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    // Crear
    public PostDTO createPost(PostDTO dto) {
        Post post = postMapper.toEntity(dto);
        Post saved = postRepository.save(post);
        return postMapper.toDTO(saved);
    }

    // Leer todos
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::toDTO).collect(Collectors.toList());
    }

    // Leer por id
    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id).map(postMapper::toDTO);
    }

    // Actualizar
    public Optional<PostDTO> updatePost(Long id, PostDTO dto) {
        return postRepository.findById(id).map(existing -> {
            existing.setMessage(dto.getMessage());
            existing.setLikes(dto.getLikes());
            // usar el mapper para construir el User desde el DTO (si aplica)
            User userFromDto = postMapper.toEntity(dto).getUser();
            existing.setUser(userFromDto);
            Post updated = postRepository.save(existing);
            return postMapper.toDTO(updated);
        });
    }

    // Eliminar
    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

