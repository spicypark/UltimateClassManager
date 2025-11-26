package frontend;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import backend.Database;
import backend.Teacher;

public class ManagerFrame extends JFrame {
    private static ManagerFrame instance = null;

    private ManagerFrame() {        
        this.add(StudentProfileOverview.getInstance());
        this.add(Home.getInstance());
        this.setTitle(PageNames.HOME);
        this.setMinimumSize(new Dimension(1000, 750));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Database.saveStudentProfiles(StudentProfileOverview.getInstance().profiles);
                Database.saveTeacherClasses(Teacher.getInstance().getAllClasses());
            }
        });
        this.setVisible(true);
    }

    public static ManagerFrame getInstance() {
        if (instance == null) instance = new ManagerFrame();
        return instance;
    }
}
