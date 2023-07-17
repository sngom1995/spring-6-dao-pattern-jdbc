package sn.guru.springframework.springdaopatternjdbc.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import sn.guru.springframework.springdaopatternjdbc.domain.Author;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = {"sn.guru.springframework.springdaopatternjdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthorById() {
        Author author = authorDao.getAuthorById(1L);
        System.out.println(author.getFirstName() + " " + author.getLastName());
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByFirstName() {
        Author author = authorDao.getAuthorByFirstName("Samba");
        System.out.println(author.getFirstName() + " " + author.getLastName());
        assertThat(author).isNotNull();
    }
}
