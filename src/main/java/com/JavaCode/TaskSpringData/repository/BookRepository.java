package com.JavaCode.TaskSpringData.repository;

import com.JavaCode.TaskSpringData.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}