package by.brausov.entities;

import java.util.Date;

public class Student {

    private int id;
    private String surname;
    private String name;
    private String patronym;
    private String gender;
    private Date birthDay;
    private AcademicGroup group;
    private int numberOfExamsFailed;

    public Student() {}

    public Student(String surname, String name, String patronym, String gender, Date birthDay, AcademicGroup group, int numberOfExamsFailed) {
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.gender = gender;
        this.birthDay = birthDay;
        this.group = group;
        this.numberOfExamsFailed = numberOfExamsFailed;
    }

    public Student(int id, String surname, String name, String patronym, String gender, Date birthDay, AcademicGroup group, int numberOfExamsFailed) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.gender = gender;
        this.birthDay = birthDay;
        this.group = group;
        this.numberOfExamsFailed = numberOfExamsFailed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public java.sql.Date getBirthDay() {
        return (java.sql.Date) birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public AcademicGroup getGroup() {
        return group;
    }

    public void setGroup(AcademicGroup group) {
        this.group = group;
    }

    public int getNumberOfExamsFailed() {
        return numberOfExamsFailed;
    }

    public void setNumberOfExamsFailed(int numberOfExamsFailed) {
        this.numberOfExamsFailed = numberOfExamsFailed;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronym='" + patronym + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDay=" + birthDay +
                ", group=" + group +
                ", numberOfExamsFailed=" + numberOfExamsFailed +
                '}';
    }
}
