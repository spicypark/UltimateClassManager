package frontend.viewer;

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

import backend.Database;
import backend.Teacher;
import backend.model.ClassPackage;
import frontend.ManagerFrame;
import frontend.overview.ClassPackageOverview;
import frontend.util.PageNames;

/**
 * IndividualPackageViewer displays detailed information about a specific class package.
 */
public class IndividualPackageViewer extends JPanel {
    private static IndividualPackageViewer instance = null;
    
    private JButton backButton;
    private JButton deleteButton;
    private JButton editButton;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private ClassPackage currentPackage;

    private JLabel nameValue;
    private JLabel quantityValue;
    private JLabel priceValue;

    private IndividualPackageViewer() {
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

        deleteButton = new JButton("Delete Package");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 24));
        deleteButton.setPreferredSize(new Dimension(200, 50));
        deleteButton.setMinimumSize(new Dimension(200, 50));
        deleteButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(deleteButton, 
                    "Are you sure you want to delete this package?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    Teacher.getInstance().removePackage(currentPackage);
                    ClassPackageOverview.getInstance().refreshPackages();
                    ManagerFrame.getInstance().setContentPane(ClassPackageOverview.getInstance());
                    ManagerFrame.getInstance().revalidate();
                    ManagerFrame.getInstance().repaint();
                    ManagerFrame.getInstance().setTitle(PageNames.CLASS_PACKAGE_MANAGEMENT);
                }
            }
        });

        editButton = new JButton("Edit Package");
        editButton.setFont(new Font("Arial", Font.PLAIN, 24));
        editButton.setPreferredSize(new Dimension(200, 50));
        editButton.setMinimumSize(new Dimension(200, 50));
        editButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEditDialog();
            }
        });

        optionsPanel.add(backButton);
        optionsPanel.add(editButton);
        optionsPanel.add(deleteButton);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Package Name
        JLabel nameLabel = new JLabel("Package Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        nameLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        nameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(nameLabel);

        nameValue = new JLabel("");
        nameValue.setHorizontalAlignment(SwingConstants.CENTER);
        nameValue.setFont(new Font("Arial", Font.BOLD, 48));
        nameValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        nameValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 60));
        nameValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        contentPanel.add(nameValue);

        // Number of Classes
        JLabel quantityLabel = new JLabel("Number of Classes:");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        quantityLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        quantityLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        quantityLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(quantityLabel);

        quantityValue = new JLabel("");
        quantityValue.setHorizontalAlignment(SwingConstants.CENTER);
        quantityValue.setFont(new Font("Arial", Font.BOLD, 48));
        quantityValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        quantityValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 60));
        quantityValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        contentPanel.add(quantityValue);

        // Price
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        priceLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        priceLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        priceLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(priceLabel);

        priceValue = new JLabel("");
        priceValue.setHorizontalAlignment(SwingConstants.CENTER);
        priceValue.setFont(new Font("Arial", Font.BOLD, 48));
        priceValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        priceValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 60));
        priceValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        contentPanel.add(priceValue);
        
        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private void showEditDialog() {
        JTextField nameField = new JTextField(currentPackage.getName());
        JTextField quantityField = new JTextField(String.valueOf(currentPackage.getQuantity()));
        JTextField priceField = new JTextField(String.valueOf(currentPackage.getPrice()));

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.add(new JLabel("Package Name:"));
        editPanel.add(nameField);
        editPanel.add(new JLabel("Number of Classes:"));
        editPanel.add(quantityField);
        editPanel.add(new JLabel("Price:"));
        editPanel.add(priceField);

        int result = JOptionPane.showConfirmDialog(this, editPanel, "Edit Package", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String newName = nameField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String priceText = priceField.getText().trim();

            // Validate inputs
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Package name cannot be empty.", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int newQuantity;
            try {
                newQuantity = Integer.parseInt(quantityText);
                if (newQuantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be a positive number.", 
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a valid number.", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double newPrice;
            try {
                newPrice = Double.parseDouble(priceText);
                if (newPrice < 0) {
                    JOptionPane.showMessageDialog(this, "Price cannot be negative.", 
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price must be a valid number.", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Apply changes
            currentPackage.setName(newName);
            currentPackage.setQuantity(newQuantity);
            currentPackage.setPrice(newPrice);
            
            // Save to database
            Database.saveClassPackages(Teacher.getInstance().getPackages());
            
            // Refresh display
            displayPackage(currentPackage);
            
            JOptionPane.showMessageDialog(this, "Package updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void displayPackage(ClassPackage pkg) {
        this.currentPackage = pkg;
        nameValue.setText(pkg.getName());
        quantityValue.setText(String.valueOf(pkg.getQuantity()));
        priceValue.setText("$" + String.format("%.2f", pkg.getPrice()));
    }
    
    public static IndividualPackageViewer getInstance() {
        if (instance == null) instance = new IndividualPackageViewer();
        return instance;
    }
}
