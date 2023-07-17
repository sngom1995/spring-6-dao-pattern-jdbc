package sn.guru.springframework.springdaopatternjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.guru.springframework.springdaopatternjdbc.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
