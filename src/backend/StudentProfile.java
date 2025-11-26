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
    private ArrayList<ArtClass> studentClasses = new ArrayList<ArtClass>();
    private ArrayList<StudentBreak> studentBreaks = new ArrayList<StudentBreak>();
    private JButton overviewButton;

    public StudentProfile(String firstName, String lastName, int age, double balance) {
        studentFirstName = firstName;
        studentLastName = lastName;
        studentAge = age;
        studentBalance = balance;
        overviewButton = new JButton(studentLastName + ", " + studentFirstName + "  |  " + age + "  |  $" + balance);
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

    public void addStudentClass(ArtClass c) {studentClasses.add(c);}
    public ArrayList<ArtClass> getStudentClasses() {return studentClasses;}
    public void addStudentBreak(StudentBreak b) {studentBreaks.add(b);}
    public ArrayList<StudentBreak> getStudentBreaks() {return studentBreaks;}
    public JButton getButton() {return overviewButton;}
    public void updateButton() {
        overviewButton.setText(studentLastName + ", " + studentFirstName + "  |  " + studentAge + "  |  $" + studentBalance);
    }
}
