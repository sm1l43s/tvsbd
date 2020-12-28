package by.brausov.gui;

import by.brausov.dao.AcademicGroupDAOImpl;
import by.brausov.dao.AcademicGroupsDAO;
import by.brausov.dao.StudentDAO;
import by.brausov.dao.StudentDAOImpl;
import by.brausov.entities.AcademicGroup;
import by.brausov.entities.Student;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class InfoAboutStudents {

    StudentDAO studentDAO = new StudentDAOImpl();
    JTable table = null;

    public  InfoAboutStudents() {

        final JFrame frame = new JFrame("Информация о студентах");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(640, 100));
        panelTop.setLayout(new GridBagLayout());
        panelTop.setBackground(Color.white);

        GridBagConstraints constraints = new GridBagConstraints();

        JLabel info = new JLabel("Студенты");
        info.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelTop.add(info, constraints);

        JButton addStudent = new JButton("Добавить студента");
        addStudent.setPreferredSize(new Dimension(205, 35));
        addStudent.setBackground(new Color(12, 123, 12));
        addStudent.setForeground(Color.white);
        addStudent.setFocusable(false);
        addStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStudent();
                frame.dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        panelTop.add(addStudent, constraints);

        JButton deleteStudent = new JButton("Удалить выбранного студента");
        deleteStudent.setPreferredSize(new Dimension(205, 35));
        deleteStudent.setBackground(new Color(123, 54, 15));
        deleteStudent.setForeground(Color.white);
        deleteStudent.setFocusable(false);
        deleteStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow(table);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        panelTop.add(deleteStudent, constraints);


        JPanel panelBottom = new JPanel();
        panelBottom.setPreferredSize(new Dimension(640, 340));
        panelBottom.setBackground(Color.white);

        table = new JTable();
        table.setPreferredSize(new Dimension(640, 380));
        final DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.addColumn("№");
        model.addColumn("ID");
        model.addColumn("Surname");
        model.addColumn("Name");
        model.addColumn("Patronym");
        model.addColumn("Gender");
        model.addColumn("Birthday");
        model.addColumn("Name group");
        model.addColumn("Number of exams failed");
        table = createTable(table);

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                StudentDAO studentDAO = new StudentDAOImpl();
                AcademicGroupsDAO academicGroupsDAO = new AcademicGroupDAOImpl();
                if (e.getType() == TableModelEvent.UPDATE) {
                    int selectedRow = table.getSelectedRow();

                    int id = ((Integer)model.getValueAt(selectedRow, 1)).intValue();
                    String surname = ((String)model.getValueAt(selectedRow, 2));
                    String name = ((String)model.getValueAt(selectedRow, 3));
                    String patronym = ((String)model.getValueAt(selectedRow, 4));
                    String gender = ((String)model.getValueAt(selectedRow, 5));
                    String birthday = String.valueOf(model.getValueAt(selectedRow, 6));
                    String namegroup = ((String)model.getValueAt(selectedRow, 7));
                    int numberOfExamsFailed = ((Integer)model.getValueAt(selectedRow, 8)).intValue();

                    AcademicGroup group = academicGroupsDAO.getByName(namegroup);
                    if (group.getCourse() != 0) {
                        Student student = new Student(id, surname, name, patronym, gender, java.sql.Date.valueOf(birthday), academicGroupsDAO.getByName(namegroup), numberOfExamsFailed);
                        studentDAO.edit(student);
                        createTable(table);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ошибка. Нет такой группы!");
                        createTable(table);
                    }
                }
            }
        });

        table.setDragEnabled(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(630, 330));
        panelBottom.add(scrollPane);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(panelBottom, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    private JTable createTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<Student> list = studentDAO.list();

        int i = 0;
        for (Student student: list) {
            model.addRow(new Object[]{++i, student.getId(), student.getSurname(), student.getName(), student.getPatronym(), student.getGender(),
                        student.getBirthDay(), student.getGroup().getName(), student.getNumberOfExamsFailed()});
        }
        return table;
    }

    private JTable deleteRow(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        studentDAO.delete(studentDAO.getById((Integer) table.getValueAt(table.getSelectedRow(), 1)));
        return createTable(table);
    }

}
