package sn.guru.springframework.springdaopatternjdbc.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import sn.guru.springframework.springdaopatternjdbc.domain.Book;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookDaoImpl.class)
class BookDaoImplTest {
    @Autowired
    BookDao bookDao;

    @Test
    void getBookById() {
        Book book = bookDao.getBookById(1L);
        System.out.println(book.getTitle());
        assertThat(book).isNotNull();
    }

    @Test
    void saveBook() {
        Book book = Book.builder()
                .title("Spring Data JPA")
                .isbn("123456789")
                .publisher("Guru")
                .build();
        Book book1 = bookDao.saveBook(book);
        System.out.println(book1.getTitle());
        assertThat(book1).isNotNull();
    }

    @Test
    void updateBook() {
        Book book = Book.builder()
                .title("Spring Data JPA2")
                .isbn("123456789")
                .publisher("Guru2")
                .build();
        Book book1 = bookDao.updateBook(book, 1L);
        System.out.println(book1.getTitle());
        assertThat(book1).isNotNull();
    }

    @Test
    void deleteBook() {
    }
}
