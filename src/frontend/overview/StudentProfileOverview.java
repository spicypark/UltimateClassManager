package frontend.overview;

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

import frontend.ManagerFrame;
import frontend.creation.StudentProfileCreation;
import frontend.home.Home;
import frontend.util.PageNames;
import frontend.viewer.IndividualProfileViewer;
import backend.Database;
import backend.model.StudentProfile;

public class StudentProfileOverview extends JPanel {
    private static StudentProfileOverview instance = null;
    
    private JButton overviewToHome;
    private JButton createProfile;
    private JButton sortProfiles;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    public ArrayList<StudentProfile> profiles = new ArrayList<StudentProfile>();

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

        sortProfiles = new JButton("Sort A - Z");
        sortProfiles.setFont(new Font("Arial", Font.PLAIN, 24));
        sortProfiles.setPreferredSize(new Dimension(200, 50));
        sortProfiles.setMinimumSize(new Dimension(200, 50));
        sortProfiles.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        sortProfiles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortProfilesAlphabetically();
            }
        });
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, java.awt.BorderLayout.NORTH);
        this.add(scrollPane, java.awt.BorderLayout.CENTER);

        ArrayList<StudentProfile> loaded = Database.loadStudentProfiles();
        if (loaded != null) {
            for (StudentProfile s : loaded) {
                this.profiles.add(s);
                s.rebuildButton();
                JButton overviewButton = s.getButton();
                overviewButton.setPreferredSize(new Dimension(200, 50));
                overviewButton.setMinimumSize(new Dimension(200, 50));
                overviewButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                overviewButton.setFont(new Font("Arial", Font.PLAIN, 24));
                overviewButton.setBackground(new Color(237, 237, 237));
                contentPanel.add(overviewButton);
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
            contentPanel.revalidate();
            contentPanel.repaint();
        }
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
        Database.saveStudentProfiles(profiles);

        overviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IndividualProfileViewer.getInstance().displayProfile(s);
                ManagerFrame.getInstance().setContentPane(IndividualProfileViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.PROFILE_VIEWER + s.getFirstName() + " " + s.getLastName());
            }
        });

        this.sortProfilesAlphabetically();
    }

    public void removeStudentProfile(StudentProfile s) {
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).equals(s)) {
                profiles.remove(i);
                contentPanel.remove(i);
                Database.saveStudentProfiles(profiles);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        }
    }

    public void sortProfilesAlphabetically() {
        if (profiles == null || profiles.size() <= 1) return;
        for (int i = 0; i < profiles.size() - 1; i++) {
            for (int j = 0; j < profiles.size() - i - 1; j++) {
                StudentProfile current = profiles.get(j);
                StudentProfile next = profiles.get(j + 1);
                int lastNameCompare = current.getLastName().compareToIgnoreCase(next.getLastName());
                boolean shouldSwap = false;
                if (lastNameCompare > 0) {
                    shouldSwap = true;
                } else if (lastNameCompare == 0) {
                    if (current.getFirstName().compareToIgnoreCase(next.getFirstName()) > 0) {
                        shouldSwap = true;
                    }
                }
                
                if (shouldSwap) {
                    profiles.set(j, next);
                    profiles.set(j + 1, current);
                }
            }
        }
        contentPanel.removeAll();
        for (StudentProfile s : profiles) {
            s.rebuildButton();
            JButton overviewButton = s.getButton();
            overviewButton.setPreferredSize(new Dimension(200, 50));
            overviewButton.setMinimumSize(new Dimension(200, 50));
            overviewButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            overviewButton.setFont(new Font("Arial", Font.PLAIN, 24));
            overviewButton.setBackground(new Color(237, 237, 237));
            contentPanel.add(overviewButton);
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
        contentPanel.revalidate();
        contentPanel.repaint();
        Database.saveStudentProfiles(profiles);
    }

    public static StudentProfileOverview getInstance() {
        if (instance == null) instance = new StudentProfileOverview();
        return instance;
    }
}