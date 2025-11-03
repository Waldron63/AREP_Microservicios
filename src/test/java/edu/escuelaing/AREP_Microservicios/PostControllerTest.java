package edu.escuelaing.AREP_Microservicios;

import edu.escuelaing.AREP_Microservicios.controller.PostController;
import edu.escuelaing.AREP_Microservicios.service.PostService;
import edu.escuelaing.AREP_Microservicios.utils.DTO.PostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost_ShouldReturnCreatedPost() {
        PostDTO dto = new PostDTO();
        when(postService.createPost(dto)).thenReturn(dto);

        ResponseEntity<PostDTO> response = postController.createPost(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAll_ShouldReturnListOfPosts() {
        List<PostDTO> posts = List.of(new PostDTO(), new PostDTO());
        when(postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<List<PostDTO>> response = postController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
    }

    @Test
    void getById_ShouldReturnPost_WhenFound() {
        PostDTO dto = new PostDTO();
        when(postService.getPostById(1L)).thenReturn(Optional.of(dto));

        ResponseEntity<PostDTO> response = postController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getById_ShouldReturnNotFound_WhenMissing() {
        when(postService.getPostById(1L)).thenReturn(Optional.empty());

        ResponseEntity<PostDTO> response = postController.getById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void update_ShouldReturnUpdatedPost_WhenExists() {
        PostDTO dto = new PostDTO();
        when(postService.updatePost(1L, dto)).thenReturn(Optional.of(dto));

        ResponseEntity<PostDTO> response = postController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void delete_ShouldReturnNoContent_WhenDeleted() {
        when(postService.deletePost(1L)).thenReturn(true);

        ResponseEntity<Void> response = postController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturnNotFound_WhenNotDeleted() {
        when(postService.deletePost(1L)).thenReturn(false);

        ResponseEntity<Void> response = postController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

