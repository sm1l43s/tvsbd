package by.brausov.dao;

import by.brausov.connect.Connect;
import by.brausov.entities.AcademicGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicGroupDAOImpl implements AcademicGroupsDAO {

    private final String GET_ALL = "SELECT * FROM `groups`;";
    private final String GET_BY_ID = "SELECT * FROM `groups` WHERE id = ?";
    private final String GET_BY_NAME = "SELECT * FROM `groups` WHERE name = ?";
    private final String ADD = "INSERT INTO `groups` (`id`, `name`, `specialty`, `form_of_study`, `number_of_people`, `course`) VALUES (NULL, ?, ?, ?, ?, ?);";
    private final String UPDATE = "UPDATE `groups` SET name = ?, specialty = ?, form_of_study = ?, number_of_people = ?, course = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM groups WHERE id = ?";
    private final String UPDATE_INFO_GROUPS_IN_STUDENTS = "{call update_info_students(?)}";

    @Override
    public List<AcademicGroup> list() {
        List<AcademicGroup> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet res = null;

        try {
            connection = new Connect().getConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            res = statement.executeQuery(GET_ALL);

            while (res.next()) {
                list.add(fromResultSetToAcademicGroups(res));
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
    public AcademicGroup getById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        AcademicGroup academicGroup = new AcademicGroup();
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(GET_BY_ID);
            connection.setAutoCommit(false);
            statement.setInt(1, id);
            res = statement.executeQuery();
            while (res.next()) {
                academicGroup = fromResultSetToAcademicGroups(res);
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
        return academicGroup;
    }

    @Override
    public AcademicGroup getByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        AcademicGroup academicGroup = new AcademicGroup();
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(GET_BY_NAME);
            connection.setAutoCommit(false);
            statement.setString(1, name);
            res = statement.executeQuery();
            while (res.next()) {
                academicGroup = fromResultSetToAcademicGroups(res);
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
        return academicGroup;
    }

    @Override
    public void add(AcademicGroup academicGroup) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(ADD);

            connection.setAutoCommit(false);

            statement.setString(1, academicGroup.getName());
            statement.setString(2, academicGroup.getSpecialty());
            statement.setString(3, academicGroup.getFormOfStudy());
            statement.setInt(4, academicGroup.getNumberOfPeople());
            statement.setInt(5, academicGroup.getCourse());

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
    public void edit(AcademicGroup academicGroup) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = new Connect().getConnection();
            statement = connection.prepareStatement(UPDATE);
            connection.setAutoCommit(false);

            statement.setString(1, academicGroup.getName());
            statement.setString(2, academicGroup.getSpecialty());
            statement.setString(3, academicGroup.getFormOfStudy());
            statement.setInt(4, academicGroup.getNumberOfPeople());
            statement.setInt(5, academicGroup.getCourse());
            statement.setInt(6, academicGroup.getId());

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
    public void delete(AcademicGroup academicGroup) {
        Connection connection = null;
        PreparedStatement statement = null;
        CallableStatement callableStatement = null;
        try {
            connection = new Connect().getConnection();
            callableStatement = connection.prepareCall(UPDATE_INFO_GROUPS_IN_STUDENTS);
            statement = connection.prepareStatement(DELETE);
            connection.setAutoCommit(false);

            callableStatement.setInt(1, academicGroup.getId());
            callableStatement.execute();

            statement.setInt(1, academicGroup.getId());
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

    public AcademicGroup fromResultSetToAcademicGroups(ResultSet res) {
        AcademicGroup group = new AcademicGroup();
        try {
            group.setId(res.getInt("id"));
            group.setName(res.getString("name"));
            group.setSpecialty(res.getString("specialty"));
            group.setFormOfStudy(res.getString("form_of_study"));
            group.setNumberOfPeople(res.getInt("number_of_people"));
            group.setCourse(res.getInt("course"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }
}
