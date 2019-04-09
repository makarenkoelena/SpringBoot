package com.sales.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sales.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
}

