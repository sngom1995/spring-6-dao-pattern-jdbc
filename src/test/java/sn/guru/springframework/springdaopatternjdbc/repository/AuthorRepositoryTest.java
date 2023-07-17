package sn.guru.springframework.springdaopatternjdbc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sn.guru.springframework.springdaopatternjdbc.domain.Author;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void testSaveData() {
        Author author = Author.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        authorRepository.save(author);
    }
}
