package frontend.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import backend.Database;
import backend.model.StudentProfile;
import frontend.ManagerFrame;
import frontend.home.Home;
import frontend.overview.StudentProfileOverview;
import frontend.util.PageNames;
import frontend.viewer.IndividualProfileViewer;

public class StudentProfileCreation extends JPanel {
    private static StudentProfileCreation instance = null;
    
    private JButton creationToOverview;
    private JButton createProfile;
    private JPanel optionsPanel;
    private JPanel contentPanel;

    private StudentProfileCreation(int mode) {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        creationToOverview = new JButton("<< Back to Profile Overview");
        if (mode == 1) creationToOverview.setText("<< Back to Individual Profile");
        creationToOverview.setFont(new Font("Arial", Font.PLAIN, 24));
        creationToOverview.setPreferredSize(new Dimension(200, 50));
        creationToOverview.setMinimumSize(new Dimension(200, 50));
        creationToOverview.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        creationToOverview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mode == 0) {
                    ManagerFrame.getInstance().setContentPane(StudentProfileOverview.getInstance());
                    ManagerFrame.getInstance().revalidate();
                    ManagerFrame.getInstance().repaint();
                    ManagerFrame.getInstance().setTitle(PageNames.PROFILE_OVERVIEW);
                }
                else if (mode == 1) {
                    IndividualProfileViewer.getInstance().displayProfile(IndividualProfileViewer.getInstance().getCurrentProfile());
                    ManagerFrame.getInstance().setContentPane(IndividualProfileViewer.getInstance());
                    ManagerFrame.getInstance().revalidate();
                    ManagerFrame.getInstance().repaint();
                    ManagerFrame.getInstance().setTitle(PageNames.PROFILE_VIEWER + IndividualProfileViewer.getInstance().getCurrentProfile().getFirstName() + " " + IndividualProfileViewer.getInstance().getCurrentProfile().getLastName());
                }
            }
        });

        createProfile = new JButton("Save Profile");
        createProfile.setFont(new Font("Arial", Font.PLAIN, 24));
        createProfile.setPreferredSize(new Dimension(200, 50));
        createProfile.setMinimumSize(new Dimension(200, 50));
        createProfile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        optionsPanel.add(creationToOverview);
        optionsPanel.add(createProfile);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel firstName = new JLabel("First Name:");
        firstName.setHorizontalAlignment(SwingConstants.CENTER);
        firstName.setFont(new Font("Arial", Font.PLAIN, 24));
        firstName.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        firstName.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        firstName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(firstName);
        
        JTextField firstNameInput = new JTextField(10);
        firstNameInput.setFont(new Font("Arial", Font.PLAIN, 24));
        firstNameInput.setHorizontalAlignment(JTextField.CENTER);
        firstNameInput.setPreferredSize(new Dimension(200, 50));
        firstNameInput.setMinimumSize(new Dimension(200, 50));
        firstNameInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        if (mode == 1) {
            firstNameInput.setText(IndividualProfileViewer.getInstance().getCurrentProfile().getFirstName());
            firstNameInput.setEditable(false);
        }
        contentPanel.add(firstNameInput);

        JLabel lastName = new JLabel("Last Name:");
        lastName.setHorizontalAlignment(SwingConstants.CENTER);
        lastName.setFont(new Font("Arial", Font.PLAIN, 24));
        lastName.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        lastName.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        lastName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(lastName);
        
        JTextField lastNameInput = new JTextField(10);
        lastNameInput.setFont(new Font("Arial", Font.PLAIN, 24));
        lastNameInput.setHorizontalAlignment(JTextField.CENTER);
        lastNameInput.setPreferredSize(new Dimension(200, 50));
        lastNameInput.setMinimumSize(new Dimension(200, 50));
        lastNameInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        if (mode == 1) {
            lastNameInput.setText(IndividualProfileViewer.getInstance().getCurrentProfile().getLastName());
            lastNameInput.setEditable(false);
        }
        contentPanel.add(lastNameInput);

        JLabel age = new JLabel("Age:");
        age.setHorizontalAlignment(SwingConstants.CENTER);
        age.setFont(new Font("Arial", Font.PLAIN, 24));
        age.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        age.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        age.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(age);
        
        JTextField ageInput = new JTextField(10);
        ageInput.setFont(new Font("Arial", Font.PLAIN, 24));
        ageInput.setHorizontalAlignment(JTextField.CENTER);
        ageInput.setPreferredSize(new Dimension(200, 50));
        ageInput.setMinimumSize(new Dimension(200, 50));
        ageInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        if (mode == 1) ageInput.setText(String.valueOf(IndividualProfileViewer.getInstance().getCurrentProfile().getAge()));
        contentPanel.add(ageInput);

        JLabel balance = new JLabel("Balance:");
        balance.setHorizontalAlignment(SwingConstants.CENTER);
        balance.setFont(new Font("Arial", Font.PLAIN, 24));
        balance.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        balance.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        balance.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(balance);
        
        JTextField balanceInput = new JTextField(10);
        balanceInput.setFont(new Font("Arial", Font.PLAIN, 24));
        balanceInput.setHorizontalAlignment(JTextField.CENTER);
        balanceInput.setPreferredSize(new Dimension(200, 50));
        balanceInput.setMinimumSize(new Dimension(200, 50));
        balanceInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        if (mode == 1) balanceInput.setText(String.valueOf(IndividualProfileViewer.getInstance().getCurrentProfile().getBalance()));
        contentPanel.add(balanceInput);

        createProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String first = firstNameInput.getText().trim();
                String last = lastNameInput.getText().trim();
                String ageText = ageInput.getText().trim();
                String balanceText = balanceInput.getText().trim();

                String namePattern = "^[A-Za-z\\s\'-]+$";
                if (mode == 0) {
                    if (first.isEmpty() || !first.matches(namePattern)) {
                        JOptionPane.showMessageDialog(createProfile,
                            "Invalid first name. Please enter only letters (hyphens and apostrophes allowed).",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (last.isEmpty() || !last.matches(namePattern)) {
                        JOptionPane.showMessageDialog(createProfile,
                            "Invalid last name. Please enter only letters (hyphens and apostrophes allowed).",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }

                int ageVal;
                try {
                    ageVal = Integer.parseInt(ageText);
                    if (ageVal < 0) {
                        JOptionPane.showMessageDialog(createProfile,
                            "Age cannot be negative. Please re-enter a valid age.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createProfile,
                        "Invalid age. Please enter a whole number (e.g., 10).",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                double balanceVal;
                try {
                    balanceVal = Double.parseDouble(balanceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createProfile, "Invalid balance. Please enter a number (e.g., 45.50).", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (mode == 0) {
                    StudentProfileOverview.getInstance().addStudentProfile(new StudentProfile(first, last, ageVal, balanceVal));
                    ManagerFrame.getInstance().setContentPane(StudentProfileOverview.getInstance());
                    ManagerFrame.getInstance().revalidate();
                    ManagerFrame.getInstance().repaint();
                    ManagerFrame.getInstance().setTitle(PageNames.PROFILE_OVERVIEW);
                }
                else if (mode == 1) {
                    IndividualProfileViewer.getInstance().getCurrentProfile().updateAge(ageVal);
                    IndividualProfileViewer.getInstance().getCurrentProfile().setBalance(balanceVal);
                    IndividualProfileViewer.getInstance().getCurrentProfile().updateButton();
                    IndividualProfileViewer.getInstance().displayProfile(IndividualProfileViewer.getInstance().getCurrentProfile());
                    ManagerFrame.getInstance().setContentPane(IndividualProfileViewer.getInstance());
                    ManagerFrame.getInstance().revalidate();
                    ManagerFrame.getInstance().repaint();
                    ManagerFrame.getInstance().setTitle(PageNames.PROFILE_VIEWER + IndividualProfileViewer.getInstance().getCurrentProfile().getFirstName()
                        + " " + IndividualProfileViewer.getInstance().getCurrentProfile().getLastName());
                    Database.saveStudentProfiles(frontend.overview.StudentProfileOverview.getInstance().profiles);
                }

                firstNameInput.setText("");
                lastNameInput.setText("");
                ageInput.setText("");
                balanceInput.setText("");
                Home.getInstance().updateBalance();
            }
        });
        
        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, java.awt.BorderLayout.NORTH);
        this.add(contentPanel, java.awt.BorderLayout.CENTER);
    }

    public void setEditing(StudentProfile profile) {
        instance = new StudentProfileCreation(1);
    }

    public void setCreating() {
        instance = new StudentProfileCreation(0);
    }
    
    public static StudentProfileCreation getInstance() {
        if (instance == null) instance = new StudentProfileCreation(0);
        return instance;
    }
}