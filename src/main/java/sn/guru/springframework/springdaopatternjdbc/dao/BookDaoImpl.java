package sn.guru.springframework.springdaopatternjdbc.dao;

import org.springframework.stereotype.Component;
import sn.guru.springframework.springdaopatternjdbc.domain.Book;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {
    private final DataSource dataSource;

    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Book getBookById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getBookFromResultSet(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection(connection, statement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Book getBookFromResultSet(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setPublisher(resultSet.getString("publisher"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    private void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
           resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close(); // doesn't really close it ... just puts back in connection pool
        }

    }

    @Override
    public Book saveBook(Book book) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO book (title, isbn, publisher) VALUES (?, ?, ?)");
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getPublisher());
            statement.execute();
            Statement statement1 = connection.createStatement();
            resultSet = statement1.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                return  this.getBookById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection(connection, statement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book updateBook(Book book, Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE book SET title = ?, isbn = ?, publisher = ? WHERE id = ?");
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getPublisher());
            statement.setLong(4, id);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection(connection, statement, resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  this.getBookById(id);
    }

    @Override
    public Book deleteBook(Long id) {
        return null;
    }
}
