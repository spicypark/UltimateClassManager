package backend.model;

public class ClassPackage {
    private String packageName;
    private int classQuantity;
    private double packagePrice;

    public ClassPackage(String name, int classes, double price) {
        packageName = name;
        classQuantity = classes;
        packagePrice = price;
    }

    public String getName() {return packageName;}
    public void setName(String name) {this.packageName = name;}
    public int getQuantity() {return classQuantity;}
    public void setQuantity(int quantity) {this.classQuantity = quantity;}
    public double getPrice() {return packagePrice;}
    public void setPrice(double price) {this.packagePrice = price;}
}
