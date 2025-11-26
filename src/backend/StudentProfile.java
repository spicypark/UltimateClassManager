package backend;

import java.util.ArrayList;
import javax.swing.JButton;

public class StudentProfile {
    private final String studentFirstName;
    private final String studentLastName;
    private int studentAge;
    private double studentBalance;
    private ClassPackage currentPackage;
    private ArrayList<ClassPackage> futurePackages = new ArrayList<ClassPackage>();
    private transient JButton overviewButton;

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
    public ClassPackage setPackage(ClassPackage c) {return currentPackage = c;}
    public ClassPackage getPackage() {return currentPackage;}
    public ArrayList<ClassPackage> getFuturePackages() {return futurePackages;}

    public JButton getButton() {return overviewButton;}
    public void updateButton() {
        if (overviewButton == null) {
            rebuildButton();
        }
        overviewButton.setText(studentLastName + ", " + studentFirstName + "  |  " + studentAge + "  |  $" + studentBalance);
    }
}
