package by.brausov.gui;

import by.brausov.auth.Auth;
import by.brausov.dao.UserDAOImpl;
import by.brausov.entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {

    public Login() {

        final JFrame frame = new JFrame("Авторизация");
        frame.setSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;

        JLabel info = new JLabel("Авторизация");
        info.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(info, constraints);

        JLabel empty = new JLabel(" ");
        constraints.gridy = 1;
        panel.add(empty, constraints);

        final JLabel login = new JLabel("Введите логин");
        login.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 2;
        panel.add(login, constraints);

        final JTextField loginField = new JTextField();
        loginField.setPreferredSize(new Dimension(150, 35));
        constraints.gridy = 3;
        panel.add(loginField, constraints);

        JLabel empty1 = new JLabel(" ");
        constraints.gridy = 4;
        panel.add(empty1, constraints);

        JLabel password = new JLabel("Введите пароль");
        password.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridy = 5;
        panel.add(password, constraints);

        final JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 35));
        constraints.gridy = 6;
        panel.add(passwordField, constraints);

        JLabel empty2 = new JLabel(" ");
        constraints.gridy = 7;
        panel.add(empty2, constraints);

        JButton btnLogin = new JButton("Войти");
        btnLogin.setBackground(new Color(30, 113, 50));
        btnLogin.setForeground(Color.white);
        btnLogin.setPreferredSize(new Dimension(100, 40));

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loginField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    if (new Auth().Verify(new User(loginField.getText(), passwordField.getText()))) {
                        new MainForm(new UserDAOImpl().getByName(loginField.getText()));
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ошибка авторизации. Логин или пароль не совпадает. Проверьте правильноть ввода!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Не все поля введены! Проверьте ввод");
                }
            }
        });

        constraints.gridy = 8;
        panel.add(btnLogin, constraints);

        JButton cancel = new JButton("Отмена");
        cancel.setBackground(new Color(113, 35, 20));
        cancel.setForeground(Color.white);
        cancel.setPreferredSize(new Dimension(100, 40));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        constraints.gridy = 9;
        panel.add(cancel, constraints);

        frame.add(panel);
        frame.setVisible(true);

    }

}
