package backend;

public class ClassPackage {
    private final String packageName;
    private final int classQuantity;
    private final double packagePrice;

    public ClassPackage(String name, int classes, double price) {
        packageName = name;
        classQuantity = classes;
        packagePrice = price;
    }

    public String getName() {return packageName;}
    public int getQuantity() {return classQuantity;}
    public double getPrice() {return packagePrice;}
}
