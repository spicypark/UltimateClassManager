package frontend.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.Teacher;
import backend.model.TeacherBreak;
import frontend.ManagerFrame;
import frontend.home.Home;
import frontend.util.PageNames;

/**
 * UI for creating teacher breaks with start and end date selection.
 */
public class TeacherBreakCreation extends JPanel {
    private static TeacherBreakCreation instance = null;

    private JButton backButton;
    private JButton createBreakButton;
    private JPanel optionsPanel;
    private JPanel contentPanel;

    private JComboBox<Integer> startYearInput;
    private JComboBox<String> startMonthInput;
    private JComboBox<Integer> startDayInput;
    private JComboBox<Integer> endYearInput;
    private JComboBox<String> endMonthInput;
    private JComboBox<Integer> endDayInput;

    private final String[] MONTHS = {"January", "February", "March", "April", "May", "June", 
                                      "July", "August", "September", "October", "November", "December"};

    private TeacherBreakCreation() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xe6e6fa));

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        backButton = new JButton("<< Back to Home");
        backButton.setFont(new Font("Arial", Font.PLAIN, 24));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setMinimumSize(new Dimension(200, 50));
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Home.getInstance().getCalendarPanel().updateCalendar();
                ManagerFrame.getInstance().setContentPane(Home.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.HOME);
            }
        });

        createBreakButton = new JButton("Create Break");
        createBreakButton.setFont(new Font("Arial", Font.PLAIN, 24));
        createBreakButton.setPreferredSize(new Dimension(200, 50));
        createBreakButton.setMinimumSize(new Dimension(200, 50));
        createBreakButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        createBreakButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createBreak();
            }
        });

        optionsPanel.add(backButton);
        optionsPanel.add(createBreakButton);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("Add Teacher Break");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        title.setMinimumSize(new Dimension(Integer.MAX_VALUE, 80));
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        contentPanel.add(title);

        // Start Date Section
        JLabel startLabel = new JLabel("Start Date:");
        startLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startLabel.setAlignmentX(CENTER_ALIGNMENT);
        startLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        startLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        startLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        startLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(startLabel);

        JPanel startDatePanel = new JPanel();
        startDatePanel.setLayout(new BoxLayout(startDatePanel, BoxLayout.X_AXIS));
        startDatePanel.setBackground(new Color(0xe6e6fa));
        startDatePanel.setPreferredSize(new Dimension(400, 50));
        startDatePanel.setMinimumSize(new Dimension(400, 50));
        startDatePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        int currentYear = LocalDate.now().getYear();
        Integer[] years = new Integer[10];
        for (int i = 0; i < 10; i++) {
            years[i] = currentYear + i;
        }

        startMonthInput = new JComboBox<>(MONTHS);
        startMonthInput.setFont(new Font("Arial", Font.PLAIN, 20));
        startMonthInput.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        startMonthInput.addActionListener(e -> updateDayComboBox(startDayInput, startYearInput, startMonthInput));
        startDatePanel.add(startMonthInput);

        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) days[i] = i + 1;
        startDayInput = new JComboBox<>(days);
        startDayInput.setFont(new Font("Arial", Font.PLAIN, 20));
        startDayInput.setSelectedIndex(LocalDate.now().getDayOfMonth() - 1);
        startDatePanel.add(startDayInput);

        startYearInput = new JComboBox<>(years);
        startYearInput.setFont(new Font("Arial", Font.PLAIN, 20));
        startYearInput.addActionListener(e -> updateDayComboBox(startDayInput, startYearInput, startMonthInput));
        startDatePanel.add(startYearInput);

        contentPanel.add(startDatePanel);

        // End Date Section
        JLabel endLabel = new JLabel("End Date:");
        endLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endLabel.setAlignmentX(CENTER_ALIGNMENT);
        endLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        endLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        endLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        endLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contentPanel.add(endLabel);

        JPanel endDatePanel = new JPanel();
        endDatePanel.setLayout(new BoxLayout(endDatePanel, BoxLayout.X_AXIS));
        endDatePanel.setBackground(new Color(0xe6e6fa));
        endDatePanel.setPreferredSize(new Dimension(400, 50));
        endDatePanel.setMinimumSize(new Dimension(400, 50));
        endDatePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        endMonthInput = new JComboBox<>(MONTHS);
        endMonthInput.setFont(new Font("Arial", Font.PLAIN, 20));
        endMonthInput.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        endMonthInput.addActionListener(e -> updateDayComboBox(endDayInput, endYearInput, endMonthInput));
        endDatePanel.add(endMonthInput);

        endDayInput = new JComboBox<>(days.clone());
        endDayInput.setFont(new Font("Arial", Font.PLAIN, 20));
        endDayInput.setSelectedIndex(LocalDate.now().getDayOfMonth() - 1);
        endDatePanel.add(endDayInput);

        endYearInput = new JComboBox<>(years.clone());
        endYearInput.setFont(new Font("Arial", Font.PLAIN, 20));
        endYearInput.addActionListener(e -> updateDayComboBox(endDayInput, endYearInput, endMonthInput));
        endDatePanel.add(endYearInput);

        contentPanel.add(endDatePanel);

        optionsPanel.setBackground(new Color(0xe6e6fa));
        contentPanel.setBackground(new Color(0xe6e6fa));
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private void updateDayComboBox(JComboBox<Integer> dayCombo, JComboBox<Integer> yearCombo, JComboBox<String> monthCombo) {
        int year = (Integer) yearCombo.getSelectedItem();
        int month = monthCombo.getSelectedIndex() + 1;
        int maxDays = LocalDate.of(year, month, 1).lengthOfMonth();
        
        int currentSelection = dayCombo.getSelectedIndex();
        dayCombo.removeAllItems();
        for (int i = 1; i <= maxDays; i++) {
            dayCombo.addItem(i);
        }
        if (currentSelection < maxDays) {
            dayCombo.setSelectedIndex(currentSelection);
        } else {
            dayCombo.setSelectedIndex(maxDays - 1);
        }
    }

    private void createBreak() {
        int startYear = (Integer) startYearInput.getSelectedItem();
        int startMonth = startMonthInput.getSelectedIndex() + 1;
        int startDay = (Integer) startDayInput.getSelectedItem();
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

        int endYear = (Integer) endYearInput.getSelectedItem();
        int endMonth = endMonthInput.getSelectedIndex() + 1;
        int endDay = (Integer) endDayInput.getSelectedItem();
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

        if (endDate.isBefore(startDate)) {
            JOptionPane.showMessageDialog(this, 
                "End date must be on or after start date.",
                "Invalid Date Range", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TeacherBreak newBreak = new TeacherBreak(startDate, endDate);
        Teacher.getInstance().addBreak(newBreak);

        JOptionPane.showMessageDialog(this, 
            "Break created from " + startDate + " to " + endDate + ".\nAny classes during this period have been removed.",
            "Break Created", JOptionPane.INFORMATION_MESSAGE);

        // Return to home
        Home.getInstance().getCalendarPanel().updateCalendar();
        ManagerFrame.getInstance().setContentPane(Home.getInstance());
        ManagerFrame.getInstance().revalidate();
        ManagerFrame.getInstance().repaint();
        ManagerFrame.getInstance().setTitle(PageNames.HOME);
    }

    public static TeacherBreakCreation getInstance() {
        if (instance == null) instance = new TeacherBreakCreation();
        return instance;
    }
}
