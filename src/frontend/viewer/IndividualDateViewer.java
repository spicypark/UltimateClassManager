package frontend.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import backend.Teacher;
import backend.model.ArtClass;
import frontend.ManagerFrame;
import frontend.creation.ArtClassCreation;
import frontend.home.Home;
import frontend.util.PageNames;

import javax.swing.JOptionPane;

public class IndividualDateViewer extends JPanel {
    private static IndividualDateViewer instance = null;

    private LocalDate selectedDate = LocalDate.now();
    private JButton dateViewerToCalendar;
    private JButton createClass;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private JLabel currentDateLabel;
    
    private IndividualDateViewer() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        dateViewerToCalendar = new JButton("<< Back to Calendar");
        dateViewerToCalendar.setFont(new Font("Arial", Font.PLAIN, 24));
        dateViewerToCalendar.setPreferredSize(new Dimension(200, 50));
        dateViewerToCalendar.setMinimumSize(new Dimension(200, 50));
        dateViewerToCalendar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        dateViewerToCalendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(Home.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.HOME);
            }
        });

        createClass = new JButton("Add New Class");
        createClass.setFont(new Font("Arial", Font.PLAIN, 24));
        createClass.setPreferredSize(new Dimension(200, 50));
        createClass.setMinimumSize(new Dimension(200, 50));
        createClass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        createClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if the date is blocked (break or holiday)
                if (Teacher.getInstance().isBlockedDay(selectedDate)) {
                    JOptionPane.showMessageDialog(createClass, 
                        "Cannot add classes on this day - it is a break day or holiday.",
                        "Blocked Day", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ArtClassCreation.getInstance().setSelectedDate(selectedDate);
                ManagerFrame.getInstance().setContentPane(ArtClassCreation.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.CLASS_CREATION + selectedDate.toString());
            }
        });

        currentDateLabel = new JLabel(selectedDate.toString());
        currentDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentDateLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        currentDateLabel.setPreferredSize(new Dimension(200, 50));
        currentDateLabel.setMinimumSize(new Dimension(200, 50));
        currentDateLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        optionsPanel.add(dateViewerToCalendar);
        optionsPanel.add(createClass);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(currentDateLabel);
        
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, java.awt.BorderLayout.NORTH);
        this.add(scrollPane, java.awt.BorderLayout.CENTER);
    }

    public LocalDate getDate() {
        return selectedDate;
    }

    public void setDate(LocalDate d) {
        this.selectedDate = d;
        this.currentDateLabel.setText(d.toString());
        contentPanel.add(currentDateLabel);
    }

    public void displayClasses() {
        for (int i = 0; i < Teacher.getInstance().getAllClasses().size(); i++) {
            ArtClass c = Teacher.getInstance().getAllClasses().get(i);
            if (c.getClassDateTime().toLocalDate().equals(selectedDate)) {
                contentPanel.add(c.getButton());
                contentPanel.revalidate();
                contentPanel.repaint();
                
                c.getButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        IndividualClassViewer.getInstance().setSelectedClass(c);
                        ManagerFrame.getInstance().setContentPane(IndividualClassViewer.getInstance());
                        ManagerFrame.getInstance().revalidate();
                        ManagerFrame.getInstance().repaint();
                        ManagerFrame.getInstance().setTitle(PageNames.CLASS_VIEWER + c.getClassName());
                    }
                });
            }
        }
    }

    public void clearList() {
        contentPanel.removeAll();
        contentPanel.add(currentDateLabel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static IndividualDateViewer getInstance() {
        if (instance == null) instance = new IndividualDateViewer();
        return instance;
    }
}
