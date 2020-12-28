package by.brausov.gui;

import by.brausov.entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {

    private User user;

    public MainForm(User user) {
        this.user = user;
        final JFrame frame = new JFrame("Вы вошли как: " + user.getLogin());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(480, 360));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        JLabel info = new JLabel("Система управления академическими группами и студентами");
        info.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridy = 0;
        constraints.gridx = 0;
        panel.add(info, constraints);

        JLabel label3 = new JLabel(" ");
        constraints.gridy = 1;
        constraints.gridx = 0;
        panel.add(label3, constraints);

        JButton academicGroupInfo = new JButton("Информация о академических группах");
        academicGroupInfo.setBackground(new Color(54, 129, 88));
        academicGroupInfo.setPreferredSize(new Dimension(300, 30));
        academicGroupInfo.setForeground(Color.white);
        academicGroupInfo.setFocusable(false);
        academicGroupInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoAboutAcademicGroup();
            }
        });
        constraints.gridy = 2;
        constraints.gridx = 0;
        panel.add(academicGroupInfo, constraints);

        JLabel label = new JLabel(" ");
        constraints.gridy = 3;
        constraints.gridx = 0;
        panel.add(label, constraints);

        JButton studentsGroupInfo = new JButton("Информация о студентах");
        studentsGroupInfo.setBackground(new Color(54, 129, 88));
        studentsGroupInfo.setPreferredSize(new Dimension(300, 30));
        studentsGroupInfo.setForeground(Color.white);
        studentsGroupInfo.setFocusable(false);
        studentsGroupInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoAboutStudents();
            }
        });
        constraints.gridy = 4;
        constraints.gridx = 0;
        panel.add(studentsGroupInfo, constraints);

        JLabel label1 = new JLabel(" ");
        constraints.gridy = 5;
        constraints.gridx = 0;
        panel.add(label1, constraints);

        if (user.getRole().equals("ADMIN")) {
            JButton addUser = new JButton("Добавить пользователя");
            addUser.setBackground(new Color(42, 129, 48));
            addUser.setPreferredSize(new Dimension(300, 30));
            addUser.setForeground(Color.white);
            addUser.setFocusable(false);
            addUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddUser();
                }
            });
            constraints.gridy = 6;
            constraints.gridx = 0;
            panel.add(addUser, constraints);
        }

        JLabel label2 = new JLabel(" ");
        constraints.gridy = 7;
        constraints.gridx = 0;
        panel.add(label2, constraints);

        JButton close = new JButton("Выход");
        close.setBackground(new Color(129, 49, 37));
        close.setPreferredSize(new Dimension(300, 30));
        close.setForeground(Color.white);
        close.setFocusable(false);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        constraints.gridy = 8;
        constraints.gridx = 0;
        panel.add(close, constraints);


        frame.add(panel);
        frame.setVisible(true);
    }
}
