package backend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import backend.model.ArtClass;
import backend.model.ClassPackage;
import backend.model.Payment;
import backend.model.TeacherBreak;

public class Teacher {
    private static Teacher instance = null;

    private ArrayList<ClassPackage> packages = new ArrayList<ClassPackage>();
    private ArrayList<ArtClass> allClasses = new ArrayList<ArtClass>();
    private ArrayList<Payment> payments = new ArrayList<Payment>();
    private ArrayList<TeacherBreak> breaks = new ArrayList<TeacherBreak>();

    private Teacher() {
        ArrayList<ArtClass> loadedClasses = Database.loadTeacherClasses();
        if (loadedClasses != null) {
            for (ArtClass c : loadedClasses) {
                allClasses.add(c);
            }
        }
        ArrayList<ClassPackage> loadedPackages = Database.loadClassPackages();
        if (loadedPackages != null) {
            for (ClassPackage p : loadedPackages) {
                packages.add(p);
            }
        }
        ArrayList<Payment> loadedPayments = Database.loadPayments();
        if (loadedPayments != null) {
            for (Payment pay : loadedPayments) {
                payments.add(pay);
            }
        }
        ArrayList<TeacherBreak> loadedBreaks = Database.loadTeacherBreaks();
        if (loadedBreaks != null) {
            for (TeacherBreak b : loadedBreaks) {
                breaks.add(b);
            }
        }
    }

    public void addArtClass(ArtClass c) {
        allClasses.add(c); 
        Database.saveTeacherClasses(allClasses);
    }
    
    public ArrayList<ArtClass> getAllClasses() {return allClasses;}
    
    public boolean hasClassOnDay(LocalDate d) {
        for (int i = 0; i < allClasses.size(); i++) {
            if (allClasses.get(i).getClassDateTime().toLocalDate().equals(d)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ClassPackage> getPackages() {return packages;}
    
    public void addPackage(ClassPackage p) {
        packages.add(p);
        Database.saveClassPackages(packages);
    }
    
    public void removePackage(ClassPackage p) {
        packages.remove(p);
        Database.saveClassPackages(packages);
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment p) {
        payments.add(p);
        Database.savePayments(payments);
    }

    public double getTotalEarnings() {
        double total = 0.0;
        for (Payment p : payments) {
            total += p.getPaymentAmount();
        }
        return total;
    }

    public ArrayList<TeacherBreak> getBreaks() {
        return breaks;
    }

    public void addBreak(TeacherBreak b) {
        breaks.add(b);
        Database.saveTeacherBreaks(breaks);
        removeClassesDuringBreak(b);
    }

    public void removeBreak(TeacherBreak b) {
        breaks.remove(b);
        Database.saveTeacherBreaks(breaks);
    }

    public boolean isBreakDay(LocalDate date) {
        for (TeacherBreak b : breaks) {
            if (b.containsDate(date)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHoliday(LocalDate date) {
        int year = date.getYear();
        
        if (date.equals(LocalDate.of(year, 1, 1))) {
            return true;
        }
        
        LocalDate thanksgiving = LocalDate.of(year, 11, 1)
            .with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
        if (date.equals(thanksgiving)) {
            return true;
        }
        
        if (date.equals(LocalDate.of(year, 12, 25))) {
            return true;
        }
        
        return false;
    }

    public boolean isBlockedDay(LocalDate date) {
        return isBreakDay(date) || isHoliday(date);
    }

    private void removeClassesDuringBreak(TeacherBreak b) {
        ArrayList<ArtClass> toRemove = new ArrayList<>();
        for (ArtClass c : allClasses) {
            if (b.containsDate(c.getClassDateTime().toLocalDate())) {
                toRemove.add(c);
            }
        }
        allClasses.removeAll(toRemove);
        if (!toRemove.isEmpty()) {
            Database.saveTeacherClasses(allClasses);
        }
    }

    public void removeClassesOnBlockedDays() {
        ArrayList<ArtClass> toRemove = new ArrayList<>();
        for (ArtClass c : allClasses) {
            if (isBlockedDay(c.getClassDateTime().toLocalDate())) {
                toRemove.add(c);
            }
        }
        allClasses.removeAll(toRemove);
        if (!toRemove.isEmpty()) {
            Database.saveTeacherClasses(allClasses);
        }
    }

    public static Teacher getInstance() {
        if (instance == null) instance = new Teacher();
        return instance;
    }
}
