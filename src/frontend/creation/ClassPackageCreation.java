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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.Teacher;
import backend.model.ClassPackage;
import frontend.ManagerFrame;
import frontend.overview.ClassPackageOverview;
import frontend.util.PageNames;

/**
 * ClassPackageCreation allows the teacher to create a new class package.
 */
public class ClassPackageCreation extends JPanel {
    private static ClassPackageCreation instance = null;
    
    private JButton backButton;
    private JButton saveButton;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JTextField nameInput;
    private JTextField quantityInput;
    private JTextField priceInput;

    private ClassPackageCreation() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        backButton = new JButton("<< Back to Package Overview");
        backButton.setFont(new Font("Arial", Font.PLAIN, 24));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setMinimumSize(new Dimension(200, 50));
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClassPackageOverview.getInstance().refreshPackages();
                ManagerFrame.getInstance().setContentPane(ClassPackageOverview.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.CLASS_PACKAGE_MANAGEMENT);
            }
        });

        saveButton = new JButton("Save Package");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 24));
        saveButton.setPreferredSize(new Dimension(200, 50));
        saveButton.setMinimumSize(new Dimension(200, 50));
        saveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        optionsPanel.add(backButton);
        optionsPanel.add(saveButton);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Package Name
        JLabel nameLabel = new JLabel("Package Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        nameLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        nameLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        nameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(nameLabel);
        
        nameInput = new JTextField(20);
        nameInput.setFont(new Font("Arial", Font.PLAIN, 24));
        nameInput.setHorizontalAlignment(JTextField.CENTER);
        nameInput.setPreferredSize(new Dimension(200, 50));
        nameInput.setMinimumSize(new Dimension(200, 50));
        nameInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(nameInput);

        // Number of Classes
        JLabel quantityLabel = new JLabel("Number of Classes:");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        quantityLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        quantityLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        quantityLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(quantityLabel);
        
        quantityInput = new JTextField(10);
        quantityInput.setFont(new Font("Arial", Font.PLAIN, 24));
        quantityInput.setHorizontalAlignment(JTextField.CENTER);
        quantityInput.setPreferredSize(new Dimension(200, 50));
        quantityInput.setMinimumSize(new Dimension(200, 50));
        quantityInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(quantityInput);

        // Price
        JLabel priceLabel = new JLabel("Price ($):");
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        priceLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        priceLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        priceLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(priceLabel);
        
        priceInput = new JTextField(10);
        priceInput.setFont(new Font("Arial", Font.PLAIN, 24));
        priceInput.setHorizontalAlignment(JTextField.CENTER);
        priceInput.setPreferredSize(new Dimension(200, 50));
        priceInput.setMinimumSize(new Dimension(200, 50));
        priceInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(priceInput);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameInput.getText().trim();
                String quantityText = quantityInput.getText().trim();
                String priceText = priceInput.getText().trim();

                // Validation
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(saveButton, "Please enter a package name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(saveButton, "Number of classes must be a positive integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(saveButton, "Invalid number of classes. Please enter a whole number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(priceText);
                    if (price < 0) {
                        JOptionPane.showMessageDialog(saveButton, "Price cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(saveButton, "Invalid price. Please enter a number (e.g., 199.99).", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create and save package
                ClassPackage newPackage = new ClassPackage(name, quantity, price);
                Teacher.getInstance().addPackage(newPackage);

                // Clear inputs
                nameInput.setText("");
                quantityInput.setText("");
                priceInput.setText("");

                // Navigate back
                ClassPackageOverview.getInstance().refreshPackages();
                ManagerFrame.getInstance().setContentPane(ClassPackageOverview.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.CLASS_PACKAGE_MANAGEMENT);
            }
        });
        
        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }
    
    public static ClassPackageCreation getInstance() {
        if (instance == null) instance = new ClassPackageCreation();
        return instance;
    }
}
