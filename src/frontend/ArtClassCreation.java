package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import backend.ArtClass;
import backend.Teacher;

public class ArtClassCreation extends JPanel {
    private static ArtClassCreation instance = null;
    
    private JButton creationToOverview;
    private JButton createClass;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JPanel timeSelectorPanel;
    private LocalDate selectedDate;

    private ArtClassCreation() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));

        selectedDate = IndividualDateViewer.getInstance().getDate();
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        creationToOverview = new JButton("<< Back to Date Overview");
        creationToOverview.setFont(new Font("Arial", Font.PLAIN, 24));
        creationToOverview.setPreferredSize(new Dimension(200, 50));
        creationToOverview.setMinimumSize(new Dimension(200, 50));
        creationToOverview.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        creationToOverview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(IndividualDateViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.DATE_VIEWER + selectedDate.toString());
            }
        });

        createClass = new JButton("Save Class");
        createClass.setFont(new Font("Arial", Font.PLAIN, 24));
        createClass.setPreferredSize(new Dimension(200, 50));
        createClass.setMinimumSize(new Dimension(200, 50));
        createClass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        optionsPanel.add(creationToOverview);
        optionsPanel.add(createClass);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel name = new JLabel("Name:");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setFont(new Font("Arial", Font.PLAIN, 24));
        name.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        name.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(name);
        
        JTextField nameInput = new JTextField(10);
        nameInput.setFont(new Font("Arial", Font.PLAIN, 24));
        nameInput.setHorizontalAlignment(JTextField.CENTER);
        nameInput.setPreferredSize(new Dimension(200, 50));
        nameInput.setMinimumSize(new Dimension(200, 50));
        nameInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(nameInput);

        JLabel capacity = new JLabel("Capacity:");
        capacity.setHorizontalAlignment(SwingConstants.CENTER);
        capacity.setFont(new Font("Arial", Font.PLAIN, 24));
        capacity.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        capacity.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        capacity.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(capacity);
        
        JTextField capacityInput = new JTextField(10);
        capacityInput.setFont(new Font("Arial", Font.PLAIN, 24));
        capacityInput.setHorizontalAlignment(JTextField.CENTER);
        capacityInput.setPreferredSize(new Dimension(200, 50));
        capacityInput.setMinimumSize(new Dimension(200, 50));
        capacityInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(capacityInput);

        JLabel time = new JLabel("Time:");
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setFont(new Font("Arial", Font.PLAIN, 24));
        time.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        time.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        time.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(time);

        timeSelectorPanel = new JPanel();
        timeSelectorPanel.setLayout(new BoxLayout(timeSelectorPanel, BoxLayout.X_AXIS));
        timeSelectorPanel.setPreferredSize(new Dimension(200, 50));
        timeSelectorPanel.setMinimumSize(new Dimension(200, 50));
        timeSelectorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JComboBox<Integer> hourInput = new JComboBox<Integer>(new Integer[] {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        hourInput.setFont(new Font("Arial", Font.PLAIN, 24));
        ((JLabel)hourInput.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        timeSelectorPanel.add(hourInput);
        JLabel colon = new JLabel(":");
        colon.setFont(new Font("Arial", Font.PLAIN, 24));
        timeSelectorPanel.add(colon);
        JComboBox<String> minuteInput = new JComboBox<String>(new String[] {"00", "15", "30", "45"});
        minuteInput.setFont(new Font("Arial", Font.PLAIN, 24));
        ((JLabel)minuteInput.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        timeSelectorPanel.add(minuteInput);
        JComboBox<String> ampmInput = new JComboBox<String>(new String[] {"PM", "AM"});
        ampmInput.setFont(new Font("Arial", Font.PLAIN, 24));
        ((JLabel)ampmInput.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        timeSelectorPanel.add(ampmInput);

        contentPanel.add(timeSelectorPanel);

        JLabel length = new JLabel("Length (hours):");
        length.setHorizontalAlignment(SwingConstants.CENTER);
        length.setFont(new Font("Arial", Font.PLAIN, 24));
        length.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        length.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        length.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(length);
        
        JComboBox<Double> lengthInput = new JComboBox<Double>(new Double[] {1.0, 1.5, 2.0, 2.5, 3.0});
        lengthInput.setFont(new Font("Arial", Font.PLAIN, 24));
        ((JLabel)lengthInput.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        lengthInput.setPreferredSize(new Dimension(200, 50));
        lengthInput.setMinimumSize(new Dimension(200, 50));
        lengthInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(lengthInput);

        createClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Teacher.getInstance().addArtClass(new ArtClass(
                    nameInput.getText(),
                    Integer.parseInt(capacityInput.getText()),
                    LocalDateTime.of(selectedDate,
                        LocalTime.of(
                            ampmInput.getSelectedItem().equals("AM") ? (hourInput.getSelectedItem().equals(12) ? 0 : (Integer)hourInput.getSelectedItem()) : (hourInput.getSelectedItem().equals(12) ? 12 : (Integer)hourInput.getSelectedItem() + 12),
                            Integer.parseInt((String)minuteInput.getSelectedItem())
                        )
                    ),
                    (double) lengthInput.getSelectedItem()
                ));
                IndividualDateViewer.getInstance().displayClasses();
                ManagerFrame.getInstance().setContentPane(IndividualDateViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.DATE_VIEWER + selectedDate.toString());    
                nameInput.setText("");
                capacityInput.setText("");
                hourInput.setSelectedIndex(0);
                minuteInput.setSelectedIndex(0);
                ampmInput.setSelectedIndex(0);
                lengthInput.setSelectedIndex(0);
                Home.getInstance().updateBalance();
                Home.getInstance().getCalendarPanel().updateCalendar();
            }
        });
        
        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, java.awt.BorderLayout.NORTH);
        this.add(contentPanel, java.awt.BorderLayout.CENTER);
    }

    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
        instance = new ArtClassCreation();
    }
    
    public static ArtClassCreation getInstance() {
        if (instance == null) instance = new ArtClassCreation();
        return instance;
    }
}