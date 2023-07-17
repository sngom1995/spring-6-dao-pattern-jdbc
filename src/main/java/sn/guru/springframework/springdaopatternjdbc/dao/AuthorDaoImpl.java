package sn.guru.springframework.springdaopatternjdbc.dao;

import org.springframework.stereotype.Component;
import sn.guru.springframework.springdaopatternjdbc.domain.Author;

import javax.sql.DataSource;
import java.sql.*;


@Component
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author getAuthorById(Long id) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE id = ?");
            preparedStatement.setLong(1, id);
            statement = connection.createStatement();
            //resultSet = statement.executeQuery("SELECT * FROM author WHERE id = " + id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getAuthorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
            closeConnection(connection, statement, resultSet); // doesn't really close it ... just puts back in connection pool
                }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author getAuthorByFirstName(String firstName, String lastName) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetAuthor = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? AND last_name = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSetAuthor = preparedStatement.executeQuery();
            if (resultSetAuthor.next()) {
                return getAuthorFromResultSet(resultSetAuthor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
              closeConnection(connection, statement, resultSetAuthor);// doesn't really close it ... just puts back in connection pool
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author saveAuthor(Author author) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetAuthor = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO author (first_name, last_name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.execute();
            Statement statement1 = connection.createStatement();

            resultSetAuthor = statement1.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSetAuthor.next()) {
                Long savedId = resultSetAuthor.getLong(1);
               return this.getAuthorById(savedId);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Author updateAuthor(Author author, Long id) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetAuthor = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE author SET first_name = ?, last_name = ? WHERE author.id = ?", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, id);
            preparedStatement.execute();

        }
        catch (SQLException e) {}
        return this.getAuthorById(id);
    }

    @Override
    public Author deleteAuthor(Long id) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetAuthor = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM author WHERE author.id = ?", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        }
        catch (SQLException e) {}
        return this.getAuthorById(id);
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
              if (resultSet != null)

                resultSet.close();

            if (statement != null)

                statement.close();

            if (connection != null)

                connection.close(); // doesn't really close it ... just puts back in connection pool

    }
    private Author getAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));
        return author;
    }
}
