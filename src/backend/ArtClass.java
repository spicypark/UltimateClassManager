package backend;

import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDateTime;
import javax.swing.JButton;

public class ArtClass {
    private int capacity;
    private LocalDateTime classDateTime;
    private double lengthInHours;
    private ClassPackage classPackage;
    private String className;
    private transient JButton classButton;

    public ArtClass(String name, int capacity, LocalDateTime dt, double length) {
        this.className = name;
        this.capacity = capacity;
        this.classDateTime = dt;
        this.lengthInHours = length;
        this.classButton = new JButton(name + "  |  " + dt.toLocalTime().toString() + "  |  " + length + " hrs  |  Capacity " + capacity);
        this.classButton = new JButton(name + "  |  " + dt.toLocalTime().toString() + " - " + dt.toLocalTime().plusMinutes((long) (length * 60)) + "  |  Capacity " + capacity);
        this.classButton.setFont(new Font("Arial", Font.PLAIN, 24));
        this.classButton.setPreferredSize(new Dimension(200, 50));
        this.classButton.setMinimumSize(new Dimension(200, 50));
        this.classButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
    }

    public void rebuildButton() {
        this.classButton = new JButton(className + "  |  " + classDateTime.toLocalTime().toString() + " - " + classDateTime.toLocalTime().plusMinutes((long) (lengthInHours * 60)) + "  |  Capacity " + capacity);
        this.classButton.setFont(new Font("Arial", Font.PLAIN, 24));
        this.classButton.setPreferredSize(new Dimension(200, 50));
        this.classButton.setMinimumSize(new Dimension(200, 50));
        this.classButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
    }

    public void setCapacity(int c) {this.capacity = c;}
    public int getCapacity() {return this.capacity;}
    public void setClassDateTime(LocalDateTime dt) {this.classDateTime = dt;}
    public LocalDateTime getClassDateTime() {return this.classDateTime;}
    public void setLengthInHours(double l) {this.lengthInHours = l;}
    public double getLengthInHours() {return this.lengthInHours;}
    public void setClassPackage(ClassPackage p) {this.classPackage = p;}
    public ClassPackage getClassPackage() {return this.classPackage;}
    // public void setClassName(String n) {this.className = n;}
    public String getClassName() {return this.className;}
    public JButton getButton() { if (this.classButton == null) rebuildButton(); return this.classButton; }
}
