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

import backend.Teacher;
import backend.model.ClassPackage;
import frontend.ManagerFrame;
import frontend.creation.ClassPackageCreation;
import frontend.home.Home;
import frontend.util.PageNames;
import frontend.viewer.IndividualPackageViewer;

public class ClassPackageOverview extends JPanel {
    private static ClassPackageOverview instance = null;
    
    private JButton backToHome;
    private JButton createPackage;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;

    private ClassPackageOverview() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        backToHome = new JButton("<< Back to Home");
        backToHome.setFont(new Font("Arial", Font.PLAIN, 24));
        backToHome.setPreferredSize(new Dimension(200, 50));
        backToHome.setMinimumSize(new Dimension(200, 50));
        backToHome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        backToHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(Home.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.HOME);
            }
        });

        createPackage = new JButton("Create New Package");
        createPackage.setFont(new Font("Arial", Font.PLAIN, 24));
        createPackage.setPreferredSize(new Dimension(200, 50));
        createPackage.setMinimumSize(new Dimension(200, 50));
        createPackage.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        createPackage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(ClassPackageCreation.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.CLASS_PACKAGE_CREATION);
            }
        });

        optionsPanel.add(backToHome);
        optionsPanel.add(createPackage);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshPackages();
    }

    public void refreshPackages() {
        contentPanel.removeAll();
        
        for (ClassPackage pkg : Teacher.getInstance().getPackages()) {
            JButton packageButton = new JButton(pkg.getName() + "  |  " + pkg.getQuantity() + " classes  |  $" + pkg.getPrice());
            packageButton.setPreferredSize(new Dimension(200, 50));
            packageButton.setMinimumSize(new Dimension(200, 50));
            packageButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            packageButton.setFont(new Font("Arial", Font.PLAIN, 24));
            packageButton.setBackground(new Color(237, 237, 237));
            
            packageButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    IndividualPackageViewer.getInstance().displayPackage(pkg);
                    ManagerFrame.getInstance().setContentPane(IndividualPackageViewer.getInstance());
                    ManagerFrame.getInstance().revalidate();
                    ManagerFrame.getInstance().repaint();
                    ManagerFrame.getInstance().setTitle(PageNames.INDIVIDUAL_PACKAGE_VIEWER + pkg.getName());
                }
            });
            
            contentPanel.add(packageButton);
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public static ClassPackageOverview getInstance() {
        if (instance == null) instance = new ClassPackageOverview();
        return instance;
    }
}
