package by.brausov.gui;

import by.brausov.dao.AcademicGroupDAOImpl;
import by.brausov.dao.AcademicGroupsDAO;
import by.brausov.entities.AcademicGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddGroup {

    AcademicGroupsDAO academicGroupsDAO = new AcademicGroupDAOImpl();

    public AddGroup() {
        final JFrame frame = new JFrame("Добавление академической группы");
        frame.setSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;

        JLabel nameGroup = new JLabel("Введите название группы:");
        nameGroup.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameGroup, constraints);

        final JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(name, constraints);

        JLabel empty = new JLabel(" ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(empty, constraints);

        JLabel specialtyGroup = new JLabel("Введите специальность группы:");
        specialtyGroup.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(specialtyGroup, constraints);

        final JTextField specialty = new JTextField();
        specialty.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(specialty, constraints);

        JLabel empty1 = new JLabel(" ");
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(empty1, constraints);

        JLabel formOfStudyGroup = new JLabel("Выберите форму обучения группы:");
        formOfStudyGroup.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(formOfStudyGroup, constraints);

        final ButtonGroup group = new ButtonGroup();

        final JRadioButton fullTime = new JRadioButton("Дневная");
        fullTime.setPreferredSize(new Dimension(200, 30));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 7;
        constraints.gridx = 0;
        group.add(fullTime);
        panel.add(fullTime, constraints);

        final JRadioButton extramular = new JRadioButton("Заочная");
        constraints.gridy = 8;
        constraints.gridx = 0;
        group.add(extramular);
        panel.add(extramular, constraints);

        JLabel empty2 = new JLabel(" ");
        constraints.gridx = 0;
        constraints.gridy = 9;
        panel.add(empty2, constraints);

        JLabel numberOfPeopleGroup = new JLabel("Введите количество человек в группе:");
        numberOfPeopleGroup.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 0;
        constraints.gridy = 10;
        panel.add(numberOfPeopleGroup, constraints);

        final JTextField numberOfPeople = new JTextField();
        numberOfPeople.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 11;
        panel.add(numberOfPeople, constraints);

        JLabel empty3 = new JLabel(" ");
        constraints.gridx = 0;
        constraints.gridy = 12;
        panel.add(empty3, constraints);

        JLabel courseGroup = new JLabel("Введите курс группы:");
        courseGroup.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 13;
        panel.add(courseGroup, constraints);

        final JTextField course = new JTextField();
        course.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 14;
        panel.add(course, constraints);

        JLabel empty4 = new JLabel(" ");
        constraints.gridx = 0;
        constraints.gridy = 15;
        panel.add(empty4, constraints);


        JButton add = new JButton("Добавить");
        add.setFocusable(false);
        add.setBackground(new Color(12, 123, 12));
        add.setForeground(Color.white);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fullTime.isSelected() && !numberOfPeople.getText().isEmpty() && !course.getText().isEmpty()) {
                    boolean flag = false;
                    flag = checkData(name.getText(), specialty.getText(), 1, Integer.parseInt(numberOfPeople.getText()),
                          Integer.parseInt(course.getText()));

                    if (flag) {
                        academicGroupsDAO.add(toAcademicGroup(name.getText(), specialty.getText(),
                                1, Integer.parseInt(numberOfPeople.getText()), Integer.parseInt(course.getText())));
                        JOptionPane.showMessageDialog(null, "Новая запись добавлена");
                        frame.dispose();
                        new InfoAboutAcademicGroup();
                    } else {
                        JOptionPane.showMessageDialog(null, "Не все поля введены верно!" +
                                " Проверьте правильность ввода! (Для заочной не более 30 человек. Для дневной - 20)");
                    }
                } else if (extramular.isSelected() && !numberOfPeople.getText().isEmpty() && !course.getText().isEmpty()) {
                    boolean flag = false;
                    flag = checkData(name.getText(), specialty.getText(), 2, Integer.parseInt(numberOfPeople.getText()),
                            Integer.parseInt(course.getText()));
                    if (flag) {
                        academicGroupsDAO.add(toAcademicGroup(name.getText(), specialty.getText(),
                                2, Integer.parseInt(numberOfPeople.getText()), Integer.parseInt(course.getText())));
                        JOptionPane.showMessageDialog(null, "Новая запись добавлена");
                        frame.dispose();
                        new InfoAboutAcademicGroup();
                    } else {
                        JOptionPane.showMessageDialog(null, "Не все поля введены верно!" +
                                " Проверьте правильность ввода! (Для заочной не более 30 человек. Для дневной - 20)");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Не все поля введены верно!" +
                            " Проверьте правильность ввода! (Для заочной не более 30 человек. Для дневной - 20)");
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 16;
        panel.add(add, constraints);

        JButton cancel = new JButton("Отмена");
        cancel.setFocusable(false);
        cancel.setBackground(new Color(123, 52, 8));
        cancel.setForeground(Color.white);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new InfoAboutAcademicGroup();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 17;
        panel.add(cancel, constraints);

        frame.add(panel);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new InfoAboutAcademicGroup();
            }
        });

        frame.setVisible(true);
    }

    private boolean checkData(String name, String specialty, int formOfStudy, int numberOfPeople, int course) {
        if (!name.isEmpty() && !specialty.isEmpty() && formOfStudy == 1 && numberOfPeople <= 20 || formOfStudy == 2 && numberOfPeople <= 30) {
            return true;
        }
        return false;
    }

    private AcademicGroup toAcademicGroup(String name, String specialty, int formOfStudy, int numberOfPeople, int course) {
        AcademicGroup group = new AcademicGroup();
        group.setName(name);
        group.setSpecialty(specialty);
        if (formOfStudy == 1) {
            group.setFormOfStudy("Дневная");
        } else {
            group.setFormOfStudy("Заочная");
        }
        group.setNumberOfPeople(numberOfPeople);
        group.setCourse(course);
        return group;
    }

}
