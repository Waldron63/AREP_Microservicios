package edu.escuelaing.AREP_Microservicios.service;

import edu.escuelaing.AREP_Microservicios.model.Stream;
import edu.escuelaing.AREP_Microservicios.repository.StreamRepository;
import edu.escuelaing.AREP_Microservicios.utils.DTO.StreamDTO;
import edu.escuelaing.AREP_Microservicios.utils.mappers.StreamMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StreamService {

    private final StreamRepository streamRepository;
    private final StreamMapper streamMapper;

    public StreamService(StreamRepository streamRepository, StreamMapper streamMapper) {
        this.streamRepository = streamRepository;
        this.streamMapper = streamMapper;
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

    public boolean deleteStream(Long id) {
        if (streamRepository.existsById(id)) {
            streamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

