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
                Author author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));
                return author;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
            if (resultSet != null)

                    resultSet.close();

            if (statement != null)

                    statement.close();

            if (connection != null)

                    connection.close(); // doesn't really close it ... just puts back in connection pool
                }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author getAuthorByFirstName(String firstName) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetAuthor = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE first_name = ?");
            preparedStatement.setString(1, firstName);
            resultSetAuthor = preparedStatement.executeQuery();
            if (resultSetAuthor.next()) {
                Author author = new Author();
                author.setId(resultSetAuthor.getLong("id"));
                author.setFirstName(resultSetAuthor.getString("first_name"));
                author.setLastName(resultSetAuthor.getString("last_name"));
                return author;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSetAuthor != null)

                    resultSetAuthor.close();


                if (connection != null)

                    connection.close(); // doesn't really close it ... just puts back in connection pool
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
