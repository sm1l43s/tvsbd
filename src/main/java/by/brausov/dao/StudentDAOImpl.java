package by.brausov.dao;

import by.brausov.connect.Connect;
import by.brausov.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private AcademicGroupsDAO academicGroupsDAO = new AcademicGroupDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();

    private final String GET_ALL = "SELECT * FROM `students`;";
    private final String GET_BY_ID = "SELECT * FROM `students` WHERE id = ?";
    private final String ADD = "INSERT INTO `students` (`id`, `surname`, `name`, `patronym`, `gender`, `birthday`, `group`, `number_of_exams_failed`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);";
    private final String UPDATE = "UPDATE `students` SET `surname` = ?, `name` = ?, `patronym` = ?, `gender` = ?, `birthday` = ?, `group`= ?, `number_of_exams_failed` = ? WHERE `students`.`id` = ?;";
    private final String DELETE = "DELETE FROM students WHERE id = ?";

    @Override
    public List<Student> list() {
        List<Student> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet res = null;

        try {
            connection = new Connect().getConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            res = statement.executeQuery(GET_ALL);

            while (res.next()) {
                list.add(fromResultSetToStudent(res));
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
    public Student getById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        Student student = new Student();
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(GET_BY_ID);
            connection.setAutoCommit(false);
            statement.setInt(1, id);
            res = statement.executeQuery();
            while (res.next()) {
                student = fromResultSetToStudent(res);
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
        return student;
    }

    @Override
    public void add(Student student) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(ADD);

            connection.setAutoCommit(false);

            statement.setString(1, student.getSurname());
            statement.setString(2, student.getName());
            statement.setString(3, student.getPatronym());
            statement.setString(4, student.getGender());
            statement.setDate(5, (Date) student.getBirthDay());
            statement.setInt(6, student.getGroup().getId());
            statement.setInt(7, student.getNumberOfExamsFailed());

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
    public void edit(Student student) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(UPDATE);
            connection.setAutoCommit(false);

            statement.setString(1, student.getSurname());
            statement.setString(2, student.getName());
            statement.setString(3, student.getPatronym());
            statement.setString(4, student.getGender());
            statement.setDate(5, student.getBirthDay());
            statement.setInt(6, student.getGroup().getId());
            statement.setInt(7, student.getNumberOfExamsFailed());
            statement.setInt(8, student.getId());

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
    public void delete(Student student) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(DELETE);
            connection.setAutoCommit(false);

            statement.setInt(1, student.getId());
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

    public Student fromResultSetToStudent(ResultSet res) {
        Student student = new Student();
        try {
            student.setId(res.getInt("id"));
            student.setSurname(res.getString("surname"));
            student.setName(res.getString("name"));
            student.setPatronym(res.getString("patronym"));
            student.setGender(res.getString("gender"));
            student.setBirthDay(res.getDate("birthday"));
            student.setGroup(academicGroupsDAO.getById(res.getInt("group")));
            student.setNumberOfExamsFailed(res.getInt("number_of_exams_failed"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

}
