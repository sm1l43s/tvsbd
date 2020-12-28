package by.brausov.dao;

import by.brausov.entities.AcademicGroup;

import java.util.List;

public interface AcademicGroupsDAO {
    List<AcademicGroup> list();
    AcademicGroup getById(int id);
    AcademicGroup getByName(String name);
    void add(AcademicGroup academicGroup);
    void edit(AcademicGroup academicGroup);
    void delete(AcademicGroup academicGroup);
}
