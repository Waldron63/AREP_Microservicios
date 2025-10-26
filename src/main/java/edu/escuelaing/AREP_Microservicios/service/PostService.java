package edu.escuelaing.AREP_Microservicios.service;

import edu.escuelaing.AREP_Microservicios.repository.UserRepository;
import edu.escuelaing.AREP_Microservicios.utils.DTO.PostDTO;
import edu.escuelaing.AREP_Microservicios.model.Post;
import edu.escuelaing.AREP_Microservicios.model.User;
import edu.escuelaing.AREP_Microservicios.repository.PostRepository;
import edu.escuelaing.AREP_Microservicios.utils.mappers.PostMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }

    // Crear
    public PostDTO createPost(PostDTO dto) {
        Post post = postMapper.toEntity(dto);
        if (dto.getUserId() != null){
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            post.setUser(user);
        }
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
            User userFromDto = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
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

