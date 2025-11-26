package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import backend.StudentProfile;

public class StudentProfileOverview extends JPanel {
    private static StudentProfileOverview instance = null;
    
    private JButton overviewToHome;
    private JButton createProfile;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    ArrayList<StudentProfile> profiles = new ArrayList<StudentProfile>();

    private StudentProfileOverview() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        overviewToHome = new JButton("<< Back to Home");
        overviewToHome.setFont(new Font("Arial", Font.PLAIN, 24));
        overviewToHome.setPreferredSize(new Dimension(200, 50));
        overviewToHome.setMinimumSize(new Dimension(200, 50));
        overviewToHome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        overviewToHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(Home.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.HOME);
            }
        });

        createProfile = new JButton("Create New Profile");
        createProfile.setFont(new Font("Arial", Font.PLAIN, 24));
        createProfile.setPreferredSize(new Dimension(200, 50));
        createProfile.setMinimumSize(new Dimension(200, 50));
        createProfile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        createProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StudentProfileCreation.getInstance().setCreating();
                ManagerFrame.getInstance().setContentPane(StudentProfileCreation.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.PROFILE_CREATION);
            }
        });

        optionsPanel.add(overviewToHome);
        optionsPanel.add(createProfile);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, java.awt.BorderLayout.NORTH);
        this.add(scrollPane, java.awt.BorderLayout.CENTER);
    }

    public void addStudentProfile(StudentProfile s) {
        this.profiles.add(s);
        JButton overviewButton = s.getButton();
        overviewButton.setPreferredSize(new Dimension(200, 50));
        overviewButton.setMinimumSize(new Dimension(200, 50));
        overviewButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        overviewButton.setFont(new Font("Arial", Font.PLAIN, 24));
        overviewButton.setBackground(new Color(237, 237, 237));
        contentPanel.add(overviewButton);
        contentPanel.revalidate();
        contentPanel.repaint();

        overviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IndividualProfileViewer.getInstance().displayProfile(s);
                ManagerFrame.getInstance().setContentPane(IndividualProfileViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.PROFILE_VIEWER + s.getFirstName() + " " + s.getLastName());
            }
        });
    }

    public void removeStudentProfile(StudentProfile s) {
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).equals(s)) {
                profiles.remove(i);
                contentPanel.remove(i);
            }
        }
    }
    
    public static StudentProfileOverview getInstance() {
        if (instance == null) instance = new StudentProfileOverview();
        return instance;
    }
}