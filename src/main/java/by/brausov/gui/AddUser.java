package by.brausov.gui;

import by.brausov.dao.UserDAO;
import by.brausov.dao.UserDAOImpl;
import by.brausov.encoder.Encoder;
import by.brausov.entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUser {

    public AddUser() {

        final JFrame frame = new JFrame("Добавление пользователя");
        frame.setSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;

        JLabel surname = new JLabel("Фамилия пользователя");
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

        JLabel name = new JLabel("Имя пользователя");
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

        final JLabel password = new JLabel("Пароль пользователя");
        password.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 6;
        panel.add(password, constraints);

        final JTextField passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        constraints.gridy = 7;
        panel.add(passwordField, constraints);

        JLabel empty2 = new JLabel(" ");
        constraints.gridy = 8;
        panel.add(empty2, constraints);

        JLabel category = new JLabel("Выберите категорию пользователя:");
        category.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 9;
        panel.add(category, constraints);

        ButtonGroup group = new ButtonGroup();

        final JRadioButton admin = new JRadioButton("Администратор");
        admin.setPreferredSize(new Dimension(200, 30));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 10;
        group.add(admin);
        panel.add(admin, constraints);

        final JRadioButton user = new JRadioButton("Пользователь");
        constraints.gridy = 11;
        group.add(user);
        panel.add(user, constraints);

        JLabel empty3 = new JLabel(" ");
        constraints.gridx = 0;
        constraints.gridy = 12;
        panel.add(empty3, constraints);

        JButton add = new JButton("Добавить");
        add.setFocusable(false);
        add.setBackground(new Color(12, 123, 12));
        add.setForeground(Color.white);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDAO userDAO = new UserDAOImpl();
                if (!surnameField.getText().isEmpty() && !nameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    if (admin.isSelected()) {
                        userDAO.add(new User(surnameField.getText().trim() + " " + nameField.getText().trim(),
                                passwordField.getText().trim(), "ADMIN"));
                        JOptionPane.showMessageDialog(null, "Пользователь добавлен!");
                        frame.dispose();
                    } else if (user.isSelected()) {
                        userDAO.add(new User(surnameField.getText().trim() + " " + nameField.getText().trim(),
                                passwordField.getText().trim(), "USER"));
                        JOptionPane.showMessageDialog(null, "Пользователь добавлен!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Не все поля введены!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Не все поля введены!");
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 13;
        panel.add(add, constraints);

        JButton cancel = new JButton("Отмена");
        cancel.setFocusable(false);
        cancel.setBackground(new Color(123, 52, 8));
        cancel.setForeground(Color.white);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 14;
        panel.add(cancel, constraints);

        frame.add(panel);
        frame.setVisible(true);

    }
}
