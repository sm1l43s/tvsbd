package by.brausov.gui;

import by.brausov.dao.*;
import by.brausov.encoder.Encoder;
import by.brausov.entities.AcademicGroup;
import by.brausov.entities.Student;
import by.brausov.entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddStudent {

    StudentDAO studentDAO = new StudentDAOImpl();
    AcademicGroupsDAO academicGroupsDAO = new AcademicGroupDAOImpl();
    List<JRadioButton> buttons = new ArrayList<>();

    public AddStudent() {

        final JFrame frame = new JFrame("Добавление студента");
        frame.setSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;

        final JLabel surname = new JLabel("Фамилия студента");
        surname.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(surname, constraints);

        final JTextField surnameField = new JTextField();
        surnameField.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(surnameField, constraints);

        JLabel empty = new JLabel(" ");
        constraints.gridy = 2;
        panel.add(empty, constraints);

        final JLabel name = new JLabel("Имя студента");
        name.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 3;
        panel.add(name, constraints);

        final JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        constraints.gridy = 4;
        panel.add(nameField, constraints);

        JLabel empty1 = new JLabel(" ");
        constraints.gridy = 5;
        panel.add(empty1, constraints);

        final JLabel patronym = new JLabel("Отчество студента");
        patronym.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 6;
        panel.add(patronym, constraints);

        final JTextField patronymField = new JTextField();
        patronymField.setPreferredSize(new Dimension(200, 30));
        constraints.gridy = 7;
        panel.add(patronymField, constraints);

        JLabel empty2 = new JLabel(" ");
        constraints.gridy = 8;
        panel.add(empty2, constraints);

        final JLabel gender = new JLabel("Пол студента");
        gender.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 9;
        panel.add(gender, constraints);

        final ButtonGroup groupGender = new ButtonGroup();

        final JRadioButton male = new JRadioButton("Мужской");
        constraints.gridy = 10;
        groupGender.add(male);
        panel.add(male, constraints);

        final JRadioButton female = new JRadioButton("Женский");
        constraints.gridx = 0;
        constraints.gridy = 11;
        groupGender.add(female);
        panel.add(female, constraints);

        JLabel empty3 = new JLabel(" ");
        constraints.gridy = 12;
        panel.add(empty3, constraints);

        final JLabel birthday = new JLabel("Дата рождения (гггг-мм-дд)");
        birthday.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 13;
        panel.add(birthday, constraints);

        final JTextField birthdayField = new JTextField();
        birthdayField.setPreferredSize(new Dimension(200, 30));
        constraints.gridy = 14;
        panel.add(birthdayField, constraints);

        JLabel empty4 = new JLabel(" ");
        constraints.gridy = 15;
        panel.add(empty4, constraints);

        final JLabel numberOfExamsFailed = new JLabel("Количество не сданных экзаменов");
        numberOfExamsFailed.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 16;
        panel.add(numberOfExamsFailed, constraints);

        final JTextField numberOfExamsFailedField = new JTextField("0");
        numberOfExamsFailedField.setPreferredSize(new Dimension(200, 30));
        constraints.gridy = 17;
        panel.add(numberOfExamsFailedField, constraints);

        JLabel empty5 = new JLabel(" ");
        constraints.gridy = 18;
        panel.add(empty5, constraints);

        ButtonGroup groupAcademicGroup = new ButtonGroup();

        JLabel groupName = new JLabel("Выберите группу");
        groupName.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 19;
        panel.add(groupName, constraints);

        int i = 1;
        for (AcademicGroup group: academicGroupsDAO.list()) {
            if (!group.getName().equals("unknown")) {
                JRadioButton button = new JRadioButton(group.getName());
                groupAcademicGroup.add(button);
                constraints.gridy = 19 + i;
                panel.add(button, constraints);
                buttons.add(button);
                i++;
            }
        }

        JButton add = new JButton("Добавить");
        add.setFocusable(false);
        add.setBackground(new Color(12, 123, 12));
        add.setForeground(Color.white);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = null;
                UserDAO userDAO = new UserDAOImpl();
                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                try {
                    dateFormat.parse(birthdayField.getText());

                    if (!surnameField.getText().isEmpty() && !nameField.getText().isEmpty() && !patronymField.getText().isEmpty()
                            && !birthdayField.getText().isEmpty() && !numberOfExamsFailedField.getText().isEmpty()) {
                        if (male.isSelected() && checkIsSelected(buttons)) {
                            student = new Student(surnameField.getText(), nameField.getText(), patronymField.getText(),
                                    "Мужской", java.sql.Date.valueOf(birthdayField.getText()), academicGroupsDAO.getByName(getNameFromListSelectionButton(buttons)),
                                    Integer.parseInt(numberOfExamsFailedField.getText()));
                            studentDAO.add(student);
                            userDAO.add(new User(surnameField.getText() + " " + nameField.getText(),
                                                birthdayField.getText(),
                                                "USER"));
                            frame.dispose();
                            new InfoAboutStudents();
                        } else if (female.isSelected() && checkIsSelected(buttons)) {
                            student = new Student(surnameField.getText(), nameField.getText(), patronymField.getText(),
                                    "Женский", java.sql.Date.valueOf(birthdayField.getText()), academicGroupsDAO.getByName(getNameFromListSelectionButton(buttons)),
                                    Integer.parseInt(numberOfExamsFailedField.getText()));
                            studentDAO.add(student);
                            userDAO.add(new User(surnameField.getText() + " " + nameField.getText(),
                                    birthdayField.getText(),
                                    "USER"));
                            frame.dispose();
                            new InfoAboutStudents();
                        } else {
                            JOptionPane.showMessageDialog(null, "Не все поля введены!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Не все поля введены!");
                    }

                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Неверный формат ввода даты. Верный гггг-мм-дд!");
                }



            }
        });

        constraints.gridx = 0;
        constraints.gridy = 19 + i + 2;
        panel.add(add, constraints);

        JButton cancel = new JButton("Отмена");
        cancel.setFocusable(false);
        cancel.setBackground(new Color(123, 52, 8));
        cancel.setForeground(Color.white);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new InfoAboutStudents();
            }
        });
        constraints.gridy = 19 + i + 3;
        panel.add(cancel, constraints);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(640, 480));
        frame.add(scrollPane);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new InfoAboutStudents();
            }
        });

        frame.setVisible(true);

    }

    private boolean checkIsSelected(List<JRadioButton> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isSelected()) {
                return true;
            }
        }
        return false;
    }

    private String getNameFromListSelectionButton(List<JRadioButton> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isSelected()) {
                return buttons.get(i).getText();
            }
        }
        return "none";
    }

}
