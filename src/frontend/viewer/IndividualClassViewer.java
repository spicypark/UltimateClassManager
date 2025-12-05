package frontend.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import backend.Database;
import backend.Teacher;
import backend.model.ArtClass;
import backend.model.ClassPackage;
import frontend.ManagerFrame;
import frontend.util.PageNames;

public class IndividualClassViewer extends JPanel {
    private static IndividualClassViewer instance = null;

    private ArtClass selectedClass;
    private JButton dateViewerToCalendar;
    private JButton editPackageButton;
    private JPanel optionsPanel;
    private JPanel informationPanel;
    private JPanel studentPanel;
    private JScrollPane scrollPane;
    private JLabel nameValue;
    private JLabel dateTimeValue;
    private JLabel capacityValue;
    private JLabel packageValue;
    
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

        editPackageButton = new JButton("Edit Package");
        editPackageButton.setFont(new Font("Arial", Font.PLAIN, 24));
        editPackageButton.setPreferredSize(new Dimension(200, 50));
        editPackageButton.setMinimumSize(new Dimension(200, 50));
        editPackageButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        editPackageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEditPackageDialog();
            }
        });

        optionsPanel.add(dateViewerToCalendar);
        optionsPanel.add(editPackageButton);

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

        JLabel packageLabel = new JLabel("Package:");
        packageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        packageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        packageLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        packageLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        packageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(packageLabel);

        packageValue = new JLabel("");
        packageValue.setHorizontalAlignment(SwingConstants.CENTER);
        packageValue.setFont(new Font("Arial", Font.BOLD, 48));
        packageValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        packageValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        packageValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        informationPanel.add(packageValue);
        
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
        capacityValue.setText(Integer.toString(c.getEnrolledCount()) + " / " + Integer.toString(c.getCapacity()));
        
        ClassPackage pkg = c.getClassPackage();
        if (pkg != null) {
            packageValue.setText(pkg.getName());
        } else {
            packageValue.setText("None");
        }
    }

    private void showEditPackageDialog() {
        java.util.ArrayList<ClassPackage> packages = Teacher.getInstance().getPackages();
        
        if (packages.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No packages available. Please create a package first.",
                "No Packages", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] packageNames = new String[packages.size() + 1];
        packageNames[0] = "None";
        int selectedIndex = 0;
        for (int i = 0; i < packages.size(); i++) {
            ClassPackage pkg = packages.get(i);
            packageNames[i + 1] = pkg.getName() + " (" + pkg.getQuantity() + " classes, $" + String.format("%.2f", pkg.getPrice()) + ")";
            if (selectedClass.getClassPackage() != null && 
                selectedClass.getClassPackage().getName().equals(pkg.getName())) {
                selectedIndex = i + 1;
            }
        }

        JComboBox<String> packageSelector = new JComboBox<>(packageNames);
        packageSelector.setSelectedIndex(selectedIndex);

        int result = JOptionPane.showConfirmDialog(this, packageSelector, 
            "Select Package for " + selectedClass.getClassName(), 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selected = packageSelector.getSelectedIndex();
            if (selected == 0) {
                selectedClass.setClassPackage(null);
            } else {
                selectedClass.setClassPackage(packages.get(selected - 1));
            }
            
            Database.saveTeacherClasses(Teacher.getInstance().getAllClasses());
            
            setSelectedClass(selectedClass);
            
            JOptionPane.showMessageDialog(this, 
                "Package updated successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static IndividualClassViewer getInstance() {
        if (instance == null) instance = new IndividualClassViewer();
        return instance;
    }
}
