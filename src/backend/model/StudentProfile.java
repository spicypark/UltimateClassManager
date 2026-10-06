package backend.model;

import java.time.LocalDate;
import javax.swing.JButton;

import backend.Teacher;

public class StudentProfile {
    private final String studentFirstName;
    private final String studentLastName;
    private int studentAge;
    private transient JButton overviewButton;
    private double studentBalance;

    public StudentProfile(String firstName, String lastName, int age, double balance) {
        studentFirstName = firstName;
        studentLastName = lastName;
        studentAge = age;
        studentBalance = balance;
        overviewButton = new JButton(studentLastName + ", " + studentFirstName + "  |  " + age + "  |  $" + balance);
    }

    public void rebuildButton() {
        this.overviewButton = new JButton(studentLastName + ", " + studentFirstName + "  |  " + studentAge + "  |  $" + studentBalance);
        this.overviewButton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 24));
        this.overviewButton.setPreferredSize(new java.awt.Dimension(200, 50));
        this.overviewButton.setMinimumSize(new java.awt.Dimension(200, 50));
        this.overviewButton.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 50));
    }

    public String getFirstName() {return studentFirstName;}
    public String getLastName() {return studentLastName;}
    public int getAge() {return studentAge;}
    public int updateAge() {return studentAge++;}
    public int updateAge(int i) {return studentAge = i;}
    public double getBalance() {return studentBalance;}
    public double setBalance(double i) {return studentBalance = i;};
    public double addBalance(double i) {return studentBalance += i;}
    public double subtractBalance(double i) {return studentBalance -= i;}

    public JButton getButton() {return overviewButton;}
    public void updateButton() {
        if (overviewButton == null) {
            rebuildButton();
        }
        overviewButton.setText(studentLastName + ", " + studentFirstName + "  |  " + studentAge + "  |  $" + studentBalance);
    }

    public boolean hasClassOnDay(LocalDate date) {
        for (ArtClass c : Teacher.getInstance().getAllClasses()) {
            if (c.getClassDateTime().toLocalDate().equals(date)) {
                if (c.isStudentEnrolled(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEnrolledIn(ArtClass artClass) {
        return artClass.isStudentEnrolled(this);
    }

    public boolean hasAbsenceOnDay(LocalDate date) {
        for (ArtClass c : Teacher.getInstance().getAllClasses()) {
            if (c.getClassDateTime().toLocalDate().equals(date)) {
                if (c.isStudentEnrolled(this) && c.isStudentAbsent(this)) {
                    return true;
                }
            }
        }
        return false;
    }
}
