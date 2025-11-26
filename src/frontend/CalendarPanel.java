package frontend;

import javax.swing.*;

import backend.Teacher;

import java.awt.*;
import java.time.LocalDate;
// import java.awt.event.*;
import java.util.Calendar;

public class CalendarPanel extends JPanel {
    private Calendar calendar;
    private JLabel monthYearLabel;
    private JPanel daysPanel;
    private JButton[][] dayButtons;
    private final int CURRENT_MONTH;
    private final int CURRENT_YEAR;
    
    public CalendarPanel() {
        calendar = Calendar.getInstance();
        this.setLayout(new BorderLayout(5, 5));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create top panel with navigation controls
        JPanel topPanel = createTopPanel();
        this.add(topPanel, BorderLayout.NORTH);
        
        // Create days of week header
        JPanel weekHeaderPanel = createWeekHeaderPanel();
        this.add(weekHeaderPanel, BorderLayout.CENTER);
        
        // Create days grid panel
        daysPanel = new JPanel(new GridLayout(0, 7, 2, 2));
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(weekHeaderPanel, BorderLayout.NORTH);
        centerPanel.add(daysPanel, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);
        
        dayButtons = new JButton[6][7];
        this.initializeDayButtons();
        
        CURRENT_MONTH = calendar.get(Calendar.MONTH);
        CURRENT_YEAR = calendar.get(Calendar.YEAR);

        this.updateCalendar();
    }
    
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        
        // Month/Year label in center
        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Left controls (previous year, previous month)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton prevYearButton = new JButton("<<");
        JButton prevMonthButton = new JButton("<");
        prevYearButton.addActionListener(e -> changeYear(-1));
        prevMonthButton.addActionListener(e -> changeMonth(-1));
        leftPanel.add(prevYearButton);
        leftPanel.add(prevMonthButton);
        
        // Right controls (next month, next year)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        JButton nextMonthButton = new JButton(">");
        JButton nextYearButton = new JButton(">>");
        nextMonthButton.addActionListener(e -> changeMonth(1));
        nextYearButton.addActionListener(e -> changeYear(1));
        rightPanel.add(nextMonthButton);
        rightPanel.add(nextYearButton);
        
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(monthYearLabel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);
        
        return topPanel;
    }
    
    private JPanel createWeekHeaderPanel() {
        JPanel weekHeaderPanel = new JPanel(new GridLayout(1, 7, 2, 2));
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        
        for (String day : dayNames) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            label.setOpaque(true);
            label.setBackground(new Color(230, 230, 230));
            weekHeaderPanel.add(label);
        }
        
        return weekHeaderPanel;
    }
    
    private void initializeDayButtons() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                dayButtons[i][j] = new JButton();
                dayButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                dayButtons[i][j].setFocusPainted(false);
                dayButtons[i][j].setMargin(new Insets(2, 2, 2, 2));
                
                daysPanel.add(dayButtons[i][j]);
            }
        }
    }
    
    public void updateCalendar() {
        // Update month/year label
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        monthYearLabel.setText(months[month] + " " + year);
        
        // Get calendar information
        Calendar temp = (Calendar) calendar.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        // Update day buttons
        int dayCounter = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i == 0 && j < firstDayOfWeek) || dayCounter > daysInMonth) {
                    dayButtons[i][j].setText("");
                    dayButtons[i][j].setEnabled(false);
                    dayButtons[i][j].setBackground(null);
                } 
                else {
                    dayButtons[i][j].setText(String.valueOf(dayCounter));
                    dayButtons[i][j].setEnabled(true);
                    int dayHolder = dayCounter;
                    dayButtons[i][j].removeActionListener(dayButtons[i][j].getActionListeners().length > 0 ? dayButtons[i][j].getActionListeners()[0] : null);
                    dayButtons[i][j].addActionListener(e -> onDateClicked(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, dayHolder));
                    
                    // Highlight current day
                    if (dayCounter == currentDay && month == CURRENT_MONTH && year == CURRENT_YEAR) {
                        dayButtons[i][j].setBackground(new Color(184, 210, 255));
                    }
                    else if (Teacher.getInstance().hasClassOnDay(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, dayHolder))) {
                        dayButtons[i][j].setBackground(new Color(191, 255, 203));
                    }
                    else {
                        dayButtons[i][j].setBackground(new Color(0xe6e6fa));
                    }
                    
                    dayCounter++;
                }
            }
        }
    }
    
    private void changeMonth(int delta) {
        calendar.add(Calendar.MONTH, delta);
        updateCalendar();
    }
    
    private void changeYear(int delta) {
        calendar.add(Calendar.YEAR, delta);
        updateCalendar();
    }
    
    public void onDateClicked(int year, int month, int day) {
        System.out.println("Date clicked: " + year + "-" + month + "-" + day);
    }
}