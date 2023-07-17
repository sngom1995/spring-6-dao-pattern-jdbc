package sn.guru.springframework.springdaopatternjdbc.dao;

import sn.guru.springframework.springdaopatternjdbc.domain.Book;

public interface BookDao {
    Book getBookById(Long id);
    Book  saveBook(Book book);
    Book updateBook(Book book, Long id);
    Book deleteBook(Long id);
}
