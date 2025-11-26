package backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class Teacher {
    private static Teacher instance = null;

    // private int totalBalance;
    private static ArrayList<ClassPackage> packages = new ArrayList<ClassPackage>();
    private ArrayList<ArtClass> allClasses = new ArrayList<ArtClass>();

    private Teacher() {
        // this.totalBalance = 0;
        packages.add(new ClassPackage("Test Package", 10, 599.99));
        // Load previously saved classes (if any)
        ArrayList<ArtClass> loaded = Database.loadTeacherClasses();
        if (loaded != null) {
            for (ArtClass c : loaded) {
                allClasses.add(c);
            }
        }
        
    }

    public void addArtClass(ArtClass c) {allClasses.add(c); Database.saveTeacherClasses(allClasses);}    
    public ArrayList<ArtClass> getAllClasses() {return allClasses;}
    public boolean hasClassOnDay(LocalDate d) {
        for (int i = 0; i < allClasses.size(); i++) {
            if (allClasses.get(i).getClassDateTime().toLocalDate().equals(d)) {
                return true;
            }
        }
        return false;
    }

    public static Teacher getInstance() {
        if (instance == null) instance = new Teacher();
        return instance;
    }
}
