package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Home extends JPanel {
    private static Home instance = null;
    
    private JButton homeToOverview;
    private JPanel infoPanel;
    private JPanel featurePanel;
    private JLabel balanceValue;
    private TeacherCalendarPanel calendarPanel;

    private Home() {
        super();
        this.setBackground(new Color(0xe6e6fa));
        this.setLayout(new GridLayout(1, 2));

        infoPanel = new JPanel();
        featurePanel = new JPanel();

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));

        JLabel earnings = new JLabel("Total Earnings:");
        earnings.setHorizontalAlignment(SwingConstants.CENTER);
        earnings.setFont(new Font("Arial", Font.PLAIN, 48));
        earnings.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        earnings.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        earnings.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        infoPanel.add(earnings);

        balanceValue = new JLabel("$0");
        balanceValue.setHorizontalAlignment(SwingConstants.CENTER);
        balanceValue.setFont(new Font("Arial", Font.PLAIN, 48));
        balanceValue.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        balanceValue.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        balanceValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        infoPanel.add(balanceValue);

        homeToOverview = new JButton("Go to Profile Overview  >>");
        featurePanel.add(homeToOverview);
        homeToOverview.setFont(new Font("Arial", Font.PLAIN, 24));
        homeToOverview.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToOverview.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToOverview.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        homeToOverview.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ManagerFrame.getInstance().setContentPane(StudentProfileOverview.getInstance());
            ManagerFrame.getInstance().revalidate();
            ManagerFrame.getInstance().repaint();
            ManagerFrame.getInstance().setTitle(PageNames.PROFILE_OVERVIEW);
        }});

        JLabel calendarLabel = new JLabel("Teacher Calendar");
        calendarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        calendarLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        calendarLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        calendarLabel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 80));
        calendarLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        featurePanel.add(calendarLabel);

        calendarPanel = new TeacherCalendarPanel(){
            @Override
            public void onDateClicked(int year, int month, int day) {
                super.onDateClicked(year, month, day);
                IndividualDateViewer.getInstance().setDate(LocalDate.of(year, month, day));
                IndividualDateViewer.getInstance().clearList();
                IndividualDateViewer.getInstance().displayClasses();
                ManagerFrame.getInstance().setContentPane(IndividualDateViewer.getInstance());
                ManagerFrame.getInstance().revalidate();
                ManagerFrame.getInstance().repaint();
                ManagerFrame.getInstance().setTitle(PageNames.DATE_VIEWER + LocalDate.of(year, month, day).toString());
            }
        };
        featurePanel.add(calendarPanel);

        this.add(infoPanel);
        this.add(featurePanel);
    }

    public TeacherCalendarPanel getCalendarPanel() {
        return calendarPanel;
    }

    public void updateBalance() {
        double sum = 0.0;
        for (int i = 0; i < StudentProfileOverview.getInstance().profiles.size(); i++) {
            sum += StudentProfileOverview.getInstance().profiles.get(i).getBalance();
        }
        balanceValue.setText("$" + String.valueOf(sum));
    }

    public static Home getInstance() {
        if (instance == null) instance = new Home();
        return instance;
    }
}
