package frontend.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

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
import backend.model.Payment;
import backend.model.StudentProfile;
import frontend.ManagerFrame;
import frontend.home.Home;
import frontend.overview.StudentProfileOverview;
import frontend.util.PageNames;

/**
 * StudentDateViewer allows a student to view and enroll in classes on a specific date.
 * It shows a dropdown of available classes and lets the student enroll or unenroll.
 */
public class StudentDateViewer extends JPanel {
    private static StudentDateViewer instance = null;

    private LocalDate selectedDate = LocalDate.now();
    private StudentProfile currentStudent;
    private JButton backButton;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private JLabel currentDateLabel;
    private JLabel enrolledClassesLabel;
    private JPanel enrolledPanel;
    private JComboBox<String> classSelector;
    private JButton enrollButton;
    private JButton unenrollButton;
    private ArrayList<ArtClass> classesOnDate = new ArrayList<>();

    private StudentDateViewer() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        backButton = new JButton("<< Back to Profile");
        backButton.setFont(new Font("Arial", Font.PLAIN, 24));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setMinimumSize(new Dimension(200, 50));
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IndividualProfileViewer.getInstance().displayProfile(currentStudent);
                ManagerFrame.getInstance().setContentPane(IndividualProfileViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.PROFILE_VIEWER + currentStudent.getFirstName() + " " + currentStudent.getLastName());
            }
        });

        optionsPanel.add(backButton);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        currentDateLabel = new JLabel(selectedDate.toString());
        currentDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentDateLabel.setAlignmentX(CENTER_ALIGNMENT);
        currentDateLabel.setFont(new Font("Arial", Font.BOLD, 28));
        currentDateLabel.setPreferredSize(new Dimension(200, 60));
        currentDateLabel.setMinimumSize(new Dimension(200, 60));
        currentDateLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        contentPanel.add(currentDateLabel);

        // Section for enrolling in a class
        JLabel enrollLabel = new JLabel("Enroll in a Class:");
        enrollLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enrollLabel.setAlignmentX(CENTER_ALIGNMENT);
        enrollLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        enrollLabel.setPreferredSize(new Dimension(200, 50));
        enrollLabel.setMinimumSize(new Dimension(200, 50));
        enrollLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(enrollLabel);

        classSelector = new JComboBox<>();
        classSelector.setFont(new Font("Arial", Font.PLAIN, 20));
        classSelector.setPreferredSize(new Dimension(200, 50));
        classSelector.setMinimumSize(new Dimension(200, 50));
        classSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(classSelector);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(0xe6e6fa));

        enrollButton = new JButton("Enroll");
        enrollButton.setFont(new Font("Arial", Font.PLAIN, 24));
        enrollButton.setPreferredSize(new Dimension(150, 50));
        enrollButton.setMinimumSize(new Dimension(150, 50));
        enrollButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        enrollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enrollInSelectedClass();
            }
        });
        buttonPanel.add(enrollButton);

        unenrollButton = new JButton("Unenroll");
        unenrollButton.setFont(new Font("Arial", Font.PLAIN, 24));
        unenrollButton.setPreferredSize(new Dimension(150, 50));
        unenrollButton.setMinimumSize(new Dimension(150, 50));
        unenrollButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        unenrollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unenrollFromSelectedClass();
            }
        });
        buttonPanel.add(unenrollButton);

        contentPanel.add(buttonPanel);

        // Section showing enrolled classes
        enrolledClassesLabel = new JLabel("Currently Enrolled Classes on This Day:");
        enrolledClassesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enrolledClassesLabel.setAlignmentX(CENTER_ALIGNMENT);
        enrolledClassesLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        enrolledClassesLabel.setPreferredSize(new Dimension(200, 60));
        enrolledClassesLabel.setMinimumSize(new Dimension(200, 60));
        enrolledClassesLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        contentPanel.add(enrolledClassesLabel);

        enrolledPanel = new JPanel();
        enrolledPanel.setLayout(new BoxLayout(enrolledPanel, BoxLayout.Y_AXIS));
        enrolledPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.add(enrolledPanel);

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setStudent(StudentProfile student) {
        this.currentStudent = student;
    }

    public void setDate(LocalDate date) {
        this.selectedDate = date;
        this.currentDateLabel.setText(date.toString());
    }

    public void refreshDisplay() {
        // Populate class selector with all classes on this date
        classesOnDate.clear();
        classSelector.removeAllItems();
        
        for (ArtClass c : Teacher.getInstance().getAllClasses()) {
            if (c.getClassDateTime().toLocalDate().equals(selectedDate)) {
                classesOnDate.add(c);
                String enrolledStatus = c.isStudentEnrolled(currentStudent) ? " [ENROLLED]" : "";
                String availableSlots = " (" + c.getEnrolledCount() + "/" + c.getCapacity() + ")";
                classSelector.addItem(c.getClassName() + " - " + c.getClassDateTime().toLocalTime() + availableSlots + enrolledStatus);
            }
        }

        if (classesOnDate.isEmpty()) {
            classSelector.addItem("No classes available on this date");
            enrollButton.setEnabled(false);
            unenrollButton.setEnabled(false);
        } else {
            enrollButton.setEnabled(true);
            unenrollButton.setEnabled(true);
        }

        // Refresh enrolled classes panel
        enrolledPanel.removeAll();
        for (ArtClass c : classesOnDate) {
            if (c.isStudentEnrolled(currentStudent)) {
                JPanel classPanel = new JPanel();
                classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.X_AXIS));
                classPanel.setBackground(new Color(0xe6e6fa));
                classPanel.setPreferredSize(new Dimension(600, 45));
                classPanel.setMinimumSize(new Dimension(600, 45));
                classPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

                // Determine attendance status
                boolean isAbsent = c.isStudentAbsent(currentStudent);
                String statusText = isAbsent ? " [ABSENT]" : " [ATTENDED]";
                // Color statusColor = isAbsent ? new Color(255, 100, 100) : new Color(100, 200, 100);
                
                JLabel classLabel = new JLabel("• " + c.getClassName() + " at " + c.getClassDateTime().toLocalTime() + statusText);
                classLabel.setFont(new Font("Arial", Font.PLAIN, 24));
                // classLabel.setForeground(statusColor);
                classPanel.add(classLabel);

                // Add attendance buttons
                final ArtClass artClass = c;
                
                JButton attendedButton = new JButton("Mark Attended");
                attendedButton.setFont(new Font("Arial", Font.PLAIN, 14));
                attendedButton.addActionListener(e -> {
                    artClass.markStudentPresent(currentStudent);
                    Database.saveTeacherClasses(Teacher.getInstance().getAllClasses());
                    refreshDisplay();
                });
                
                JButton absentButton = new JButton("Mark Absent");
                absentButton.setFont(new Font("Arial", Font.PLAIN, 14));
                absentButton.addActionListener(e -> {
                    artClass.markStudentAbsent(currentStudent);
                    Database.saveTeacherClasses(Teacher.getInstance().getAllClasses());
                    refreshDisplay();
                });
                
                classPanel.add(javax.swing.Box.createHorizontalGlue());
                classPanel.add(attendedButton);
                classPanel.add(absentButton);
                
                enrolledPanel.add(classPanel);
            }
        }

        if (enrolledPanel.getComponentCount() == 0) {
            JLabel noClasses = new JLabel("Not enrolled in any classes on this day.");
            noClasses.setFont(new Font("Arial", Font.ITALIC, 18));
            noClasses.setForeground(Color.GRAY);
            enrolledPanel.add(noClasses);
        }

        enrolledPanel.revalidate();
        enrolledPanel.repaint();
    }

    private void enrollInSelectedClass() {
        if (classesOnDate.isEmpty() || classSelector.getSelectedIndex() < 0) {
            return;
        }

        ArtClass selectedClass = classesOnDate.get(classSelector.getSelectedIndex());
        
        if (selectedClass.isStudentEnrolled(currentStudent)) {
            JOptionPane.showMessageDialog(this, 
                currentStudent.getFirstName() + " is already enrolled in " + selectedClass.getClassName() + ".",
                "Already Enrolled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (selectedClass.getEnrolledCount() >= selectedClass.getCapacity()) {
            JOptionPane.showMessageDialog(this, 
                "This class is full. Cannot enroll.",
                "Class Full", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calculate payment amount based on class package
        double paymentAmount = 0.0;
        ClassPackage pkg = selectedClass.getClassPackage();
        if (pkg != null && pkg.getQuantity() > 0) {
            paymentAmount = pkg.getPrice() / pkg.getQuantity();
        }

        // Deduct from student balance
        currentStudent.subtractBalance(paymentAmount);
        currentStudent.updateButton();
        Database.saveStudentProfiles(StudentProfileOverview.getInstance().profiles);

        // Create and record payment
        String studentName = currentStudent.getFirstName() + " " + currentStudent.getLastName();
        Payment payment = new Payment(paymentAmount, selectedClass.getClassName(), 
            selectedClass.getClassDateTime(), studentName);
        Teacher.getInstance().addPayment(payment);

        // Update home screen earnings display
        Home.getInstance().updateBalance();

        selectedClass.enrollStudent(currentStudent);
        Database.saveTeacherClasses(Teacher.getInstance().getAllClasses());
        
        String paymentMsg = paymentAmount > 0 ? 
            String.format(" $%.2f has been deducted from their balance.", paymentAmount) : "";
        JOptionPane.showMessageDialog(this, 
            currentStudent.getFirstName() + " has been enrolled in " + selectedClass.getClassName() + "!" + paymentMsg,
            "Enrolled Successfully", JOptionPane.INFORMATION_MESSAGE);
        
        refreshDisplay();
    }

    private void unenrollFromSelectedClass() {
        if (classesOnDate.isEmpty() || classSelector.getSelectedIndex() < 0) {
            return;
        }

        ArtClass selectedClass = classesOnDate.get(classSelector.getSelectedIndex());
        
        if (!selectedClass.isStudentEnrolled(currentStudent)) {
            JOptionPane.showMessageDialog(this, 
                currentStudent.getFirstName() + " is not enrolled in " + selectedClass.getClassName() + ".",
                "Not Enrolled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to unenroll " + currentStudent.getFirstName() + " from " + selectedClass.getClassName() + "?",
            "Confirm Unenroll", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            selectedClass.unenrollStudent(currentStudent);
            Database.saveTeacherClasses(Teacher.getInstance().getAllClasses());
            
            JOptionPane.showMessageDialog(this, 
                currentStudent.getFirstName() + " has been unenrolled from " + selectedClass.getClassName() + ".",
                "Unenrolled Successfully", JOptionPane.INFORMATION_MESSAGE);
            
            refreshDisplay();
        }
    }

    public static StudentDateViewer getInstance() {
        if (instance == null) instance = new StudentDateViewer();
        return instance;
    }
}
