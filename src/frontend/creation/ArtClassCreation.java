package frontend.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.Teacher;
import backend.model.ArtClass;
import backend.model.ClassPackage;
import frontend.ManagerFrame;
import frontend.home.Home;
import frontend.util.PageNames;
import frontend.viewer.IndividualDateViewer;

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

        JCheckBox autoGenerateCheckbox = new JCheckBox("Auto-generate from package (creates weekly classes)");
        autoGenerateCheckbox.setFont(new Font("Arial", Font.PLAIN, 20));
        autoGenerateCheckbox.setBackground(new Color(0xe6e6fa));
        autoGenerateCheckbox.setPreferredSize(new Dimension(200, 50));
        autoGenerateCheckbox.setMinimumSize(new Dimension(200, 50));
        autoGenerateCheckbox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(autoGenerateCheckbox);

        JLabel packageLabel = new JLabel("Select Package:");
        packageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        packageLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        packageLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        packageLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        packageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(packageLabel);

        JComboBox<String> packageSelector = new JComboBox<>();
        packageSelector.setFont(new Font("Arial", Font.PLAIN, 24));
        packageSelector.setPreferredSize(new Dimension(200, 50));
        packageSelector.setMinimumSize(new Dimension(200, 50));
        packageSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        packageSelector.setEnabled(false);
        contentPanel.add(packageSelector);

        autoGenerateCheckbox.addActionListener(e -> {
            packageSelector.setEnabled(autoGenerateCheckbox.isSelected());
            if (autoGenerateCheckbox.isSelected()) {
                packageSelector.removeAllItems();
                packageSelector.addItem("-- Select a Package --");
                for (ClassPackage pkg : Teacher.getInstance().getPackages()) {
                    packageSelector.addItem(pkg.getName() + " (" + pkg.getQuantity() + " classes)");
                }
            }
        });

        createClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int hour = ampmInput.getSelectedItem().equals("AM") ? 
                    (hourInput.getSelectedItem().equals(12) ? 0 : (Integer)hourInput.getSelectedItem()) : 
                    (hourInput.getSelectedItem().equals(12) ? 12 : (Integer)hourInput.getSelectedItem() + 12);
                int minute = Integer.parseInt((String)minuteInput.getSelectedItem());
                LocalTime classTime = LocalTime.of(hour, minute);
                double classLength = (double) lengthInput.getSelectedItem();
                String className = nameInput.getText();
                int classCapacity = Integer.parseInt(capacityInput.getText());

                if (autoGenerateCheckbox.isSelected() && packageSelector.getSelectedIndex() > 0) {
                    ArrayList<ClassPackage> packages = Teacher.getInstance().getPackages();
                    ClassPackage selectedPkg = packages.get(packageSelector.getSelectedIndex() - 1);
                    int numClasses = selectedPkg.getQuantity();
                    
                    int classesCreated = 0;
                    int weeksOffset = 0;
                    while (classesCreated < numClasses) {
                        LocalDate classDate = selectedDate.plusWeeks(weeksOffset);
                        weeksOffset++;
                        
                        if (Teacher.getInstance().isBlockedDay(classDate)) {
                            continue;
                        }
                        
                        LocalDateTime dateTime = LocalDateTime.of(classDate, classTime);
                        ArtClass newClass = new ArtClass(className, classCapacity, dateTime, classLength);
                        newClass.setClassPackage(selectedPkg);
                        Teacher.getInstance().addArtClass(newClass);
                        classesCreated++;
                    }
                } else {
                    Teacher.getInstance().addArtClass(new ArtClass(
                        className,
                        classCapacity,
                        LocalDateTime.of(selectedDate, classTime),
                        classLength
                    ));
                }

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
                autoGenerateCheckbox.setSelected(false);
                packageSelector.setEnabled(false);
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