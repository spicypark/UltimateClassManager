package frontend.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import backend.Database;
import backend.Teacher;
import frontend.ManagerFrame;
import frontend.calendar.TeacherCalendarPanel;
import frontend.creation.TeacherBreakCreation;
import frontend.overview.ClassPackageOverview;
import frontend.overview.StudentProfileOverview;
import frontend.util.PageNames;
import frontend.viewer.IndividualDateViewer;

public class Home extends JPanel {
    private static Home instance = null;
    
    private JButton homeToOverview;
    private JButton homeToPackages;
    private JButton exportPaymentsButton;
    private JButton addBreakButton;
    private JPanel infoPanel;
    private JPanel featurePanel;
    private JLabel balanceValue;
    private TeacherCalendarPanel calendarPanel;

    private Home() {
        super();
        this.setBackground(new Color(0xe6e6fa));
        this.setLayout(new GridLayout(1, 2));

        infoPanel = new JPanel();
        featurePanel = new JPanel();

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));

        // === RIGHT SIDE: Buttons and Total Earnings ===
        homeToOverview = new JButton("Go to Profile Overview  >>");
        infoPanel.add(homeToOverview);
        homeToOverview.setFont(new Font("Arial", Font.PLAIN, 24));
        homeToOverview.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToOverview.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToOverview.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToOverview.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ManagerFrame.getInstance().setContentPane(StudentProfileOverview.getInstance());
            ManagerFrame.getInstance().revalidate();
            ManagerFrame.getInstance().repaint();
            ManagerFrame.getInstance().setTitle(PageNames.PROFILE_OVERVIEW);
        }});

        homeToPackages = new JButton("Go to Class Packages  >>");
        infoPanel.add(homeToPackages);
        homeToPackages.setFont(new Font("Arial", Font.PLAIN, 24));
        homeToPackages.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToPackages.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToPackages.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToPackages.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ClassPackageOverview.getInstance().refreshPackages();
            ManagerFrame.getInstance().setContentPane(ClassPackageOverview.getInstance());
            ManagerFrame.getInstance().revalidate();
            ManagerFrame.getInstance().repaint();
            ManagerFrame.getInstance().setTitle(PageNames.CLASS_PACKAGE_MANAGEMENT);
        }});

        exportPaymentsButton = new JButton("Export Payments to CSV");
        infoPanel.add(exportPaymentsButton);
        exportPaymentsButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exportPaymentsButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        exportPaymentsButton.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        exportPaymentsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        exportPaymentsButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            exportPaymentsToCSV();
        }});

        // Vertical glue to push earnings to center of remaining space
        infoPanel.add(Box.createVerticalGlue());

        // Total earnings section - centered in remaining space
        JLabel earnings = new JLabel("Total Earnings:");
        earnings.setHorizontalAlignment(SwingConstants.CENTER);
        earnings.setFont(new Font("Arial", Font.PLAIN, 48));
        earnings.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        earnings.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        earnings.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        infoPanel.add(earnings);

        balanceValue = new JLabel("$0.00");
        balanceValue.setHorizontalAlignment(SwingConstants.CENTER);
        balanceValue.setFont(new Font("Arial", Font.PLAIN, 48));
        balanceValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        balanceValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        balanceValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        infoPanel.add(balanceValue);

        // Vertical glue after earnings to complete centering
        infoPanel.add(Box.createVerticalGlue());

        // === LEFT SIDE: Calendar and Add Break button ===
        JLabel calendarLabel = new JLabel("Teacher Calendar");
        calendarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        calendarLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        calendarLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        calendarLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 80));
        calendarLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        featurePanel.add(calendarLabel);

        calendarPanel = new TeacherCalendarPanel(){
            @Override
            public void onDateClicked(int year, int month, int day) {
                super.onDateClicked(year, month, day);
                IndividualDateViewer.getInstance().setDate(LocalDate.of(year, month, day));
                IndividualDateViewer.getInstance().clearList();
                IndividualDateViewer.getInstance().displayClasses();
                ManagerFrame.getInstance().setContentPane(IndividualDateViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.DATE_VIEWER + LocalDate.of(year, month, day).toString());
            }
        };
        featurePanel.add(calendarPanel);

        // Add Break button below the calendar
        addBreakButton = new JButton("Add Teacher Break");
        featurePanel.add(addBreakButton);
        addBreakButton.setFont(new Font("Arial", Font.PLAIN, 24));
        addBreakButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        addBreakButton.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        addBreakButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addBreakButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ManagerFrame.getInstance().setContentPane(TeacherBreakCreation.getInstance());
            ManagerFrame.getInstance().revalidate();
            ManagerFrame.getInstance().repaint();
            ManagerFrame.getInstance().setTitle("Add Teacher Break");
        }});

        // Add panels: featurePanel (calendar) on LEFT, infoPanel (buttons) on RIGHT
        this.add(featurePanel);
        this.add(infoPanel);
    }

    public TeacherCalendarPanel getCalendarPanel() {
        return calendarPanel;
    }

    public void updateBalance() {
        // Calculate total earnings from all payments
        double total = Teacher.getInstance().getTotalEarnings();
        balanceValue.setText(String.format("$%.2f", total));
    }

    private void exportPaymentsToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Payments to CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        fileChooser.setSelectedFile(new java.io.File("payments.csv"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.toLowerCase().endsWith(".csv")) {
                filename += ".csv";
            }
            
            boolean success = Database.exportPaymentsToCSV(Teacher.getInstance().getPayments(), filename);
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Payments exported successfully to:\n" + filename,
                    "Export Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to export payments.",
                    "Export Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static Home getInstance() {
        if (instance == null) instance = new Home();
        return instance;
    }
}
