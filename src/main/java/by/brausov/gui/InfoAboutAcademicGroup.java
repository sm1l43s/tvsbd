package by.brausov.gui;

import by.brausov.dao.AcademicGroupDAOImpl;
import by.brausov.dao.AcademicGroupsDAO;
import by.brausov.entities.AcademicGroup;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class InfoAboutAcademicGroup {

    AcademicGroupsDAO academicGroupsDAO = new AcademicGroupDAOImpl();
    JTable table = null;

    public InfoAboutAcademicGroup() {
        final JFrame frame = new JFrame("Информация о академических группах");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(640, 100));
        panelTop.setLayout(new GridBagLayout());
        panelTop.setBackground(Color.white);

        GridBagConstraints constraints = new GridBagConstraints();

        JLabel info = new JLabel("Академические группы");
        info.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelTop.add(info, constraints);

        JButton addGroup = new JButton("Добавить группу");
        addGroup.setPreferredSize(new Dimension(205, 35));
        addGroup.setBackground(new Color(12, 123, 12));
        addGroup.setForeground(Color.white);
        addGroup.setFocusable(false);
        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddGroup();
                frame.dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        panelTop.add(addGroup, constraints);

        JButton deleteGroup = new JButton("Удалить выделенную группу");
        deleteGroup.setPreferredSize(new Dimension(205, 35));
        deleteGroup.setBackground(new Color(123, 54, 15));
        deleteGroup.setForeground(Color.white);
        deleteGroup.setFocusable(false);
        deleteGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow(table);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        panelTop.add(deleteGroup, constraints);


        JPanel panelBottom = new JPanel();
        panelBottom.setPreferredSize(new Dimension(640, 340));
        panelBottom.setBackground(Color.white);

        table = new JTable();
        table.setPreferredSize(new Dimension(640, 380));
        final DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.addColumn("№");
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Specialty");
        model.addColumn("Form of study");
        model.addColumn("Number of people");
        model.addColumn("Course");
        table = createTable(table);

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                AcademicGroupsDAO academicGroupsDAO = new AcademicGroupDAOImpl();
                if (e.getType() == TableModelEvent.UPDATE) {
                    int selectedRow = table.getSelectedRow();

                    int id = ((Integer)model.getValueAt(selectedRow, 1)).intValue();
                    String name = ((String)model.getValueAt(selectedRow, 2));
                    String specialty = ((String)model.getValueAt(selectedRow, 3));
                    String formOfStudy = ((String)model.getValueAt(selectedRow, 4));
                    int numberOfPeople = ((Integer)model.getValueAt(selectedRow, 5)).intValue();
                    int course = ((Integer)model.getValueAt(selectedRow, 6)).intValue();

                    AcademicGroup group = new AcademicGroup(id, name, specialty, formOfStudy, numberOfPeople, course);

                    academicGroupsDAO.edit(group);
                    createTable(table);
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
        List<AcademicGroup> list = academicGroupsDAO.list();

        int i = 0;
        for (AcademicGroup group: list) {
            if(!group.getName().equals("unknown")) {
                model.addRow(new Object[]{++i, group.getId(), group.getName(), group.getSpecialty(), group.getFormOfStudy(),
                            group.getNumberOfPeople(), group.getCourse()});
            }
        }
        return table;
    }

    private JTable deleteRow(JTable table) {
        academicGroupsDAO.delete(academicGroupsDAO.getById((Integer) table.getValueAt(table.getSelectedRow(), 1)));
        return createTable(table);
    }
}
