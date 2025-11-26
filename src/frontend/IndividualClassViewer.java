package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import backend.ArtClass;

public class IndividualClassViewer extends JPanel {
    private static IndividualClassViewer instance = null;

    private ArtClass selectedClass;
    private JButton dateViewerToCalendar;
    // private JButton createClass;
    private JPanel optionsPanel;
    private JPanel informationPanel;
    private JPanel studentPanel;
    private JScrollPane scrollPane;
    private JLabel nameValue;
    private JLabel dateTimeValue;
    private JLabel capacityValue;
    
    private IndividualClassViewer() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        dateViewerToCalendar = new JButton("<< Back to Date Overview");
        dateViewerToCalendar.setFont(new Font("Arial", Font.PLAIN, 24));
        dateViewerToCalendar.setPreferredSize(new Dimension(200, 50));
        dateViewerToCalendar.setMinimumSize(new Dimension(200, 50));
        dateViewerToCalendar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        dateViewerToCalendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(IndividualDateViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.DATE_VIEWER + selectedClass.getClassDateTime().toLocalDate().toString());
            }
        });

        // createClass = new JButton("Reschedule Class");
        // createClass.setFont(new Font("Arial", Font.PLAIN, 24));
        // createClass.setPreferredSize(new Dimension(200, 50));
        // createClass.setMinimumSize(new Dimension(200, 50));
        // createClass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        // createClass.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {

        //     }
        // });

        optionsPanel.add(dateViewerToCalendar);
        // optionsPanel.add(createClass);

        informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));

        JLabel name = new JLabel("Name:");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        name.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(name);

        nameValue = new JLabel("");
        nameValue.setHorizontalAlignment(SwingConstants.CENTER);
        nameValue.setFont(new Font("Arial", Font.BOLD, 48));
        nameValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        nameValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        nameValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(nameValue);

        JLabel dateTime = new JLabel("Date/Time:");
        dateTime.setHorizontalAlignment(SwingConstants.CENTER);
        dateTime.setFont(new Font("Arial", Font.PLAIN, 20));
        dateTime.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        dateTime.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        dateTime.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(dateTime);

        dateTimeValue = new JLabel("");
        dateTimeValue.setHorizontalAlignment(SwingConstants.CENTER);
        dateTimeValue.setFont(new Font("Arial", Font.BOLD, 48));
        dateTimeValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        dateTimeValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        dateTimeValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(dateTimeValue);

        JLabel capacity = new JLabel("Enrolled:");
        capacity.setHorizontalAlignment(SwingConstants.CENTER);
        capacity.setFont(new Font("Arial", Font.PLAIN, 20));
        capacity.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        capacity.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        capacity.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(capacity);

        capacityValue = new JLabel("");
        capacityValue.setHorizontalAlignment(SwingConstants.CENTER);
        capacityValue.setFont(new Font("Arial", Font.BOLD, 48));
        capacityValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        capacityValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        capacityValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(capacityValue);
        
        studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(studentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        studentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel);
        this.add(informationPanel);
        this.add(scrollPane);
    }

    public void setSelectedClass(ArtClass c) {
        this.selectedClass = c;
        nameValue.setText(c.getClassName());
        dateTimeValue.setText(c.getClassDateTime().toLocalDate().toString() + "  |  " + c.getClassDateTime().toLocalTime().toString() + " - " + c.getClassDateTime().toLocalTime().plusMinutes((long) (c.getLengthInHours() * 60)) );
        capacityValue.setText(Integer.toString(c.getEnrolledStudents().size()) + " / " + Integer.toString(c.getCapacity()));
    }

    public static IndividualClassViewer getInstance() {
        if (instance == null) instance = new IndividualClassViewer();
        return instance;
    }
}
