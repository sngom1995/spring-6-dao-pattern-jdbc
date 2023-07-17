package sn.guru.springframework.springdaopatternjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.guru.springframework.springdaopatternjdbc.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
