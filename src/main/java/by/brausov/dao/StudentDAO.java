package by.brausov.dao;

import by.brausov.entities.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> list();
    Student getById(int id);
    void add(Student student);
    void edit(Student student);
    void delete(Student student);

}
