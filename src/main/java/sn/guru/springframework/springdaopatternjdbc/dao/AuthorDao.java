package sn.guru.springframework.springdaopatternjdbc.dao;

import sn.guru.springframework.springdaopatternjdbc.domain.Author;

public interface AuthorDao {
    Author getAuthorById(Long id);

    Author getAuthorByFirstName(String firstName, String lastName);

    Author saveAuthor(Author author);


    Author updateAuthor(Author author, Long id);


    Author deleteAuthor(Long id);
}
