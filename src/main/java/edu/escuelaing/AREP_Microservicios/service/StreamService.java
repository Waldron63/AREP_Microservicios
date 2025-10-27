package edu.escuelaing.AREP_Microservicios.service;

import edu.escuelaing.AREP_Microservicios.model.Post;
import edu.escuelaing.AREP_Microservicios.model.Stream;
import edu.escuelaing.AREP_Microservicios.repository.PostRepository;
import edu.escuelaing.AREP_Microservicios.repository.StreamRepository;
import edu.escuelaing.AREP_Microservicios.utils.DTO.StreamDTO;
import edu.escuelaing.AREP_Microservicios.utils.mappers.StreamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StreamService {

    @Autowired
    private final StreamRepository streamRepository;
    private final StreamMapper streamMapper;

    @Autowired
    private final PostRepository postRepository;

    public StreamService(StreamRepository streamRepository, StreamMapper streamMapper, PostRepository postRepository) {
        this.streamRepository = streamRepository;
        this.streamMapper = streamMapper;
        this.postRepository = postRepository;
    }

    public StreamDTO createStream(StreamDTO dto) {
        Stream stream = streamMapper.toEntity(dto);
        Stream saved = streamRepository.save(stream);
        return streamMapper.toDTO(saved);
    }

    public List<StreamDTO> getAllStreams() {
        return streamRepository.findAll().stream().map(streamMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<StreamDTO> getStreamById(Long id) {
        return streamRepository.findById(id).map(streamMapper::toDTO);
    }

    public Optional<StreamDTO> updateStream(Long id, StreamDTO dto) {
        return streamRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            // reemplazar la lista de posts si viene en el DTO
            Stream temp = streamMapper.toEntity(dto);
            existing.setPosts(temp.getPosts());
            Stream updated = streamRepository.save(existing);
            return streamMapper.toDTO(updated);
        });
    }

    /**
     * Añade un post existente a un stream existente si ambos existen y el post no está ya en la lista.
     * @param streamId id del stream
     * @param postId id del post
     * @return Optional con el StreamDTO actualizado si ambos existen, Optional.empty() si alguno no existe.
     */
    public Optional<StreamDTO> addPostToStream(Long streamId, Long postId) {
        Optional<Stream> maybeStream = streamRepository.findById(streamId);
        Optional<Post> maybePost = postRepository.findById(postId);
        if (maybeStream.isEmpty() || maybePost.isEmpty()) {
            return Optional.empty();
        }
        Stream stream = maybeStream.get();
        Post post = maybePost.get();
        boolean alreadyPresent = stream.getPosts().stream().anyMatch(p -> p.getId() != null && p.getId().equals(post.getId()));
        if (!alreadyPresent) {
            stream.getPosts().add(post);
            Stream saved = streamRepository.save(stream);
            return Optional.of(streamMapper.toDTO(saved));
        }
        // si ya estaba presente, devolvemos el stream sin cambios
        return Optional.of(streamMapper.toDTO(stream));
    }

    public boolean deleteStream(Long id) {
        if (streamRepository.existsById(id)) {
            streamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
