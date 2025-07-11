package com.bookshop.productservice.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book,Long> {
/*
	Iterable<Book> findAll();
	Optional<Book> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);
	Book save(Book book);
	void deleteByIsbn(String isbn);
*/
    Optional<Book> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);

	@Modifying
	@Transactional
	@Query("delete from Book where isbn = :isbn")
	void deleteByIsbn(String isbn);

}

