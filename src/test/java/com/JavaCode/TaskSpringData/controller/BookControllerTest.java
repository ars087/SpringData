package com.JavaCode.TaskSpringData.controller;

import com.JavaCode.TaskSpringData.model.Book;
import com.JavaCode.TaskSpringData.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    public BookService bookService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllBooks() throws Exception {

        Book book1 = new Book(1L, "Book One", "Author One", 2020);
        Book book2 = new Book(2L, "Book Two", "Author Two", 2021);

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));


        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Book One"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].title").value("Book Two"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById_Success() throws Exception {
        // Arrange
        Book book = new Book(1L, "Book One", "Author One", 2020);

        when(bookService.getBookById(any(Long.class))).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Book One"))
            .andExpect(jsonPath("$.author").value("Author One"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        // Arrange
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/{id}", 1L))
            .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testCreateBook() throws Exception {

        Book book = new Book(1L, "New Book", "New Author", 2022);

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("New Book"))
            .andExpect(jsonPath("$.author").value("New Author"));

        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    void testUpdateBook_Success() throws Exception {
        // Arrange
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", 2023);

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);


        // Act & Assert
        mockMvc.perform(put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Updated Title"))
            .andExpect(jsonPath("$.author").value("Updated Author"));

        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    void testUpdateBook_NotFound() throws Exception {
        // Arrange
        Book bookDetails = new Book(1L, "New Title", "New Author", 2023);

        when(bookService.updateBook(eq(1L), any(Book.class))).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDetails)))
            .andExpect(status().isNotFound());

        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    void testDeleteBook() throws Exception {

        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/{id}", 1L))
            .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }

}