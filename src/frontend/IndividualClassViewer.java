package frontend;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import backend.ArtClass;

public class IndividualClassViewer extends JPanel {
    private static IndividualClassViewer instance = null;

    private LocalDate classDate = LocalDate.now();
    private ArrayList<ArtClass> classesOnDate = new ArrayList<ArtClass>();
    private JButton dateViewerToCalendar;
    private JButton createClass;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private JLabel currentDateLabel;
    
    private IndividualClassViewer() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        dateViewerToCalendar = new JButton("<< Back to Calendar");
        dateViewerToCalendar.setFont(new Font("Arial", Font.PLAIN, 24));
        dateViewerToCalendar.setPreferredSize(new Dimension(200, 50));
        dateViewerToCalendar.setMinimumSize(new Dimension(200, 50));
        dateViewerToCalendar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        dateViewerToCalendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame.getInstance().setContentPane(Home.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.HOME);
            }
        });

        // createClass = new JButton("Add New Class");
        // createClass.setFont(new Font("Arial", Font.PLAIN, 24));
        // createClass.setPreferredSize(new Dimension(200, 50));
        // createClass.setMinimumSize(new Dimension(200, 50));
        // createClass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        // createClass.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         StudentProfileCreation.getInstance().setCreating();
        //         ManagerFrame.getInstance().setContentPane(ArtClassCreation.getInstance());
        //         ManagerFrame.getInstance().revalidate();
        //         ManagerFrame.getInstance().repaint();
        //         ManagerFrame.getInstance().setTitle(PageNames.CLASS_CREATION + classDate.toString());
        //     }
        // });

        currentDateLabel = new JLabel(classDate.toString());
        currentDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentDateLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        currentDateLabel.setPreferredSize(new Dimension(200, 50));
        currentDateLabel.setMinimumSize(new Dimension(200, 50));
        currentDateLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        optionsPanel.add(dateViewerToCalendar);
        optionsPanel.add(createClass);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(currentDateLabel);
        
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, java.awt.BorderLayout.NORTH);
        this.add(scrollPane, java.awt.BorderLayout.CENTER);
    }

    public LocalDate getDate() {
        return classDate;
    }

    public void setDate(LocalDate d) {
        this.classDate = d;
        this.currentDateLabel.setText(d.toString());
        contentPanel.add(currentDateLabel);
    }

    public void addArtClass(ArtClass ac) {
        this.classesOnDate.add(ac);
        contentPanel.add(new JLabel(ac.getClassName()));
    }

    public static IndividualClassViewer getInstance() {
        if (instance == null) instance = new IndividualClassViewer();
        return instance;
    }
}
