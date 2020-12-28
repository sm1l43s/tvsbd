package by.brausov.dao;

import by.brausov.connect.Connect;
import by.brausov.encoder.Encoder;
import by.brausov.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final String GET_ALL = "SELECT * FROM `users`;";
    private final String GET_BY_ID = "SELECT * FROM `users` WHERE id = ?";
    private final String GET_BY_NAME = "SELECT * FROM `users` WHERE login = ?";
    private final String ADD = "INSERT INTO `users` (`id`, `login`, `password`, `role`) VALUES (NULL, ?, ?, ?);";
    private final String UPDATE = "UPDATE `users` SET `login` = ?, `password` = ?, `role` = ? WHERE `users`.`id` = ?;";
    private final String DELETE = "DELETE FROM `users` WHERE id = ?";

    @Override
    public List<User> list() {
        List<User> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet res = null;

        try {
            connection = new Connect().getConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            res = statement.executeQuery(GET_ALL);

            while (res.next()) {
                list.add(fromResultSetToUser(res));
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public User getById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        User user = new User();
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(GET_BY_ID);
            connection.setAutoCommit(false);
            statement.setInt(1, id);
            res = statement.executeQuery();
            while (res.next()) {
                user = fromResultSetToUser(res);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User getByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        User user = new User();
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(GET_BY_NAME);
            connection.setAutoCommit(false);
            statement.setString(1, name);
            res = statement.executeQuery();
            while (res.next()) {
                user = fromResultSetToUser(res);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void add(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(ADD);

            connection.setAutoCommit(false);

            statement.setString(1, user.getLogin());
            statement.setString(2, new Encoder().getEncodedString(user.getPassword()));
            statement.setString(3, user.getRole());

            statement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void edit(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(UPDATE);
            connection.setAutoCommit(false);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getId());

            statement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(DELETE);
            connection.setAutoCommit(false);

            statement.setInt(1, user.getId());

            statement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public User fromResultSetToUser(ResultSet res) {
        User user = new User();
        try {
            user.setId(res.getInt("id"));
            user.setLogin(res.getString("login"));
            user.setPassword(res.getString("password"));
            user.setRole(res.getString("role"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
