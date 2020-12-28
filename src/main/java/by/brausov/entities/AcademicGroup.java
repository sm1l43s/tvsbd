package by.brausov.entities;

public class AcademicGroup {

    private int id;
    private String name;
    private String specialty;
    private String formOfStudy;
    private int numberOfPeople;
    private int course;

    public AcademicGroup(String name, String specialty, String formOfStudy, int numberOfPeople, int course) {
        this.name = name;
        this.specialty = specialty;
        this.formOfStudy = formOfStudy;
        this.numberOfPeople = numberOfPeople;
        this.course = course;
    }

    public AcademicGroup(int id, String name, String specialty, String formOfStudy, int numberOfPeople, int course) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.formOfStudy = formOfStudy;
        this.numberOfPeople = numberOfPeople;
        this.course = course;
    }

    public AcademicGroup() {}

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getFormOfStudy() {
        return formOfStudy;
    }

    public void setFormOfStudy(String formOfStudy) {
        this.formOfStudy = formOfStudy;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "academicGroup{" +
                "name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", formOfStudy='" + formOfStudy + '\'' +
                ", numberOfPeople=" + numberOfPeople +
                ", course=" + course +
                '}';
    }
}
