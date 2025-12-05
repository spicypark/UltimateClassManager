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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.model.StudentProfile;
import frontend.ManagerFrame;
import frontend.calendar.StudentCalendarPanel;
import frontend.creation.StudentProfileCreation;
import frontend.home.Home;
import frontend.overview.StudentProfileOverview;
import frontend.util.PageNames;

public class IndividualProfileViewer extends JPanel {
    private static IndividualProfileViewer instance = null;
    
    private JButton viewerToOverview;
    private JButton editProfile;
    private JButton deleteProfile;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private StudentCalendarPanel studentCalendarPanel;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JPanel studentOptionsPanel;

    private StudentProfile currentProfile;

    JLabel nameValue;
    JLabel ageValue;
    JLabel balanceValue;
    
    private IndividualProfileViewer() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        viewerToOverview = new JButton("<<  Back to Overview");
        viewerToOverview.setFont(new Font("Arial", Font.PLAIN, 24));
        viewerToOverview.setPreferredSize(new Dimension(200, 50));
        viewerToOverview.setMinimumSize(new Dimension(200, 50));
        viewerToOverview.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        viewerToOverview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(StudentProfileOverview.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.PROFILE_OVERVIEW);
            }
        });

        editProfile = new JButton("Edit Profile");
        editProfile.setFont(new Font("Arial", Font.PLAIN, 24));
        editProfile.setPreferredSize(new Dimension(200, 50));
        editProfile.setMinimumSize(new Dimension(200, 50));
        editProfile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        editProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StudentProfileCreation.getInstance().setEditing(currentProfile);
                ManagerFrame.getInstance().setContentPane(StudentProfileCreation.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.PROFILE_EDITING + currentProfile.getFirstName() + " " + currentProfile.getLastName());
            }
        });

        deleteProfile = new JButton("Delete Profile");
        deleteProfile.setFont(new Font("Arial", Font.PLAIN, 24));
        deleteProfile.setPreferredSize(new Dimension(200, 50));
        deleteProfile.setMinimumSize(new Dimension(200, 50));
        deleteProfile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        deleteProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(deleteProfile, "Are you sure you want to delete this profile?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION) == 0) {
                    StudentProfileOverview.getInstance().removeStudentProfile(currentProfile);
                    ManagerFrame.getInstance().setContentPane(StudentProfileOverview.getInstance());
                    ManagerFrame.getInstance().revalidate();
                    ManagerFrame.getInstance().repaint();
                    ManagerFrame.getInstance().setTitle(PageNames.PROFILE_OVERVIEW);
                    Home.getInstance().updateBalance();
                }
            }
        });

        optionsPanel.add(viewerToOverview);
        optionsPanel.add(editProfile);
        optionsPanel.add(deleteProfile);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);

        JLabel name = new JLabel("Name:");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setPreferredSize(new Dimension(300, 50));
        name.setMinimumSize(new Dimension(300, 50));
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        name.setOpaque(true);
        name.setBackground(new Color(0xe6e6fa));
        leftPanel.add(name);

        nameValue = new JLabel("");
        nameValue.setHorizontalAlignment(SwingConstants.CENTER);
        nameValue.setFont(new Font("Arial", Font.BOLD, 48));
        nameValue.setPreferredSize(new Dimension(300, 50));
        nameValue.setMinimumSize(new Dimension(300, 50));
        nameValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        nameValue.setOpaque(true);
        nameValue.setBackground(new Color(0xe6e6fa));
        leftPanel.add(nameValue);

        JLabel age = new JLabel("Age:");
        age.setHorizontalAlignment(SwingConstants.CENTER);
        age.setFont(new Font("Arial", Font.PLAIN, 20));
        age.setPreferredSize(new Dimension(300, 50));
        age.setMinimumSize(new Dimension(300, 50));
        age.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        age.setOpaque(true);
        age.setBackground(new Color(0xe6e6fa));
        leftPanel.add(age);

        ageValue = new JLabel("");
        ageValue.setHorizontalAlignment(SwingConstants.CENTER);
        ageValue.setFont(new Font("Arial", Font.BOLD, 48));
        ageValue.setPreferredSize(new Dimension(300, 50));
        ageValue.setMinimumSize(new Dimension(300, 50));
        ageValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        ageValue.setOpaque(true);
        ageValue.setBackground(new Color(0xe6e6fa));
        leftPanel.add(ageValue);

        JLabel balance = new JLabel("Balance:");
        balance.setHorizontalAlignment(SwingConstants.CENTER);
        balance.setFont(new Font("Arial", Font.PLAIN, 20));
        balance.setPreferredSize(new Dimension(300, 50));
        balance.setMinimumSize(new Dimension(300, 50));
        balance.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        balance.setOpaque(true);
        balance.setBackground(new Color(0xe6e6fa));
        leftPanel.add(balance);

        balanceValue = new JLabel("");
        balanceValue.setHorizontalAlignment(SwingConstants.CENTER);
        balanceValue.setFont(new Font("Arial", Font.BOLD, 48));
        balanceValue.setPreferredSize(new Dimension(300, 50));
        balanceValue.setMinimumSize(new Dimension(300, 50));
        balanceValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        balanceValue.setOpaque(true);
        balanceValue.setBackground(new Color(0xe6e6fa));
        leftPanel.add(balanceValue);

        studentOptionsPanel = new JPanel();
        studentOptionsPanel.setLayout(new BoxLayout(studentOptionsPanel, BoxLayout.X_AXIS));

        rightPanel.add(studentOptionsPanel, java.awt.BorderLayout.NORTH);

        studentCalendarPanel = new StudentCalendarPanel(){
            @Override
            public void onDateClicked(int year, int month, int day) {
                super.onDateClicked(year, month, day);
                StudentDateViewer.getInstance().setStudent(currentProfile);
                StudentDateViewer.getInstance().setDate(LocalDate.of(year, month, day));
                StudentDateViewer.getInstance().refreshDisplay();
                ManagerFrame.getInstance().setContentPane(StudentDateViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.STUDENT_CALENDAR + currentProfile.getFirstName() + " " + currentProfile.getLastName() + " - " + LocalDate.of(year, month, day).toString());
            }
        };
        studentCalendarPanel.setPreferredSize(new Dimension(300, 200));
        studentCalendarPanel.setMaximumSize(new Dimension(300, 200));   
        rightPanel.add(studentCalendarPanel, java.awt.BorderLayout.CENTER);
        
        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, java.awt.BorderLayout.NORTH);
        this.add(contentPanel, java.awt.BorderLayout.CENTER);
    }

    public void displayProfile(StudentProfile sp) {
        nameValue.setText(sp.getFirstName() + " " + sp.getLastName());
        ageValue.setText(Integer.toString(sp.getAge()));
        balanceValue.setText(Double.toString(sp.getBalance()));
        currentProfile = sp;
        studentCalendarPanel.setCurrentStudent(sp);
    }

    public StudentProfile getCurrentProfile() {
        return currentProfile;
    }
    
    public static IndividualProfileViewer getInstance() {
        if (instance == null) instance = new IndividualProfileViewer();
        return instance;
    }
}