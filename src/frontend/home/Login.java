package frontend.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.mindrot.jbcrypt.BCrypt;

import frontend.ManagerFrame;
import frontend.util.PageNames;

/**
 * Login page for teacher authentication.
 * Uses BCrypt to securely verify the password.
 */
public class Login extends JPanel {
    private static Login instance = null;

    // Pre-hashed password for "Placeholder311" using BCrypt
    // Generated with BCrypt.hashpw("Placeholder311", BCrypt.gensalt())
    private static final String USERNAME = "FayArt";
    private static final String PASSWORD_HASH = "$2a$10$jXVSZ.z8bzjSy9DCtMro9uxMV5Ei3p8cDZxfz2xQE2UkLFg7fPc92";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel contentPanel;

    private Login() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(0xe6e6fa));

        // Title
        JLabel title = new JLabel("Ultimate Class Manager");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        title.setMinimumSize(new Dimension(Integer.MAX_VALUE, 100));
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        contentPanel.add(title);

        // Subtitle
        JLabel subtitle = new JLabel("Please log in to continue");
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 24));
        subtitle.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        subtitle.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        subtitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(subtitle);

        // Spacer
        JPanel spacer1 = new JPanel();
        spacer1.setBackground(new Color(0xe6e6fa));
        spacer1.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        spacer1.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        spacer1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(spacer1);

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setAlignmentX(CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        usernameLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        contentPanel.add(usernameLabel);

        // Username field
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 24));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setPreferredSize(new Dimension(300, 50));
        usernameField.setMinimumSize(new Dimension(300, 50));
        usernameField.setMaximumSize(new Dimension(400, 50));
        usernameField.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(usernameField);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setAlignmentX(CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        contentPanel.add(passwordLabel);

        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setPreferredSize(new Dimension(300, 50));
        passwordField.setMinimumSize(new Dimension(300, 50));
        passwordField.setMaximumSize(new Dimension(400, 50));
        passwordField.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(passwordField);

        // Spacer
        JPanel spacer2 = new JPanel();
        spacer2.setBackground(new Color(0xe6e6fa));
        spacer2.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        spacer2.setMinimumSize(new Dimension(Integer.MAX_VALUE, 30));
        spacer2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        contentPanel.add(spacer2);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 24));
        loginButton.setPreferredSize(new Dimension(200, 50));
        loginButton.setMinimumSize(new Dimension(200, 50));
        loginButton.setMaximumSize(new Dimension(200, 50));
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });
        contentPanel.add(loginButton);

        // Allow Enter key to submit
        passwordField.addActionListener(e -> attemptLogin());
        usernameField.addActionListener(e -> passwordField.requestFocus());

        this.add(contentPanel, BorderLayout.CENTER);
    }

    private void attemptLogin() {
        String enteredUsername = usernameField.getText().trim();
        String enteredPassword = new String(passwordField.getPassword());

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password.",
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verify credentials using BCrypt
        if (enteredUsername.equals(USERNAME) && BCrypt.checkpw(enteredPassword, PASSWORD_HASH)) {
            // Login successful - go to home
            ManagerFrame.getInstance().setContentPane(Home.getInstance());
            ManagerFrame.getInstance().revalidate();
            ManagerFrame.getInstance().repaint();
            ManagerFrame.getInstance().setTitle(PageNames.HOME);
            
            // Clear fields for security
            usernameField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password.",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public static Login getInstance() {
        if (instance == null) instance = new Login();
        return instance;
    }
}
