package frontend;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import backend.Database;
import backend.Teacher;
import frontend.home.Home;
import frontend.home.Login;
import frontend.overview.StudentProfileOverview;
import frontend.util.PageNames;

public class ManagerFrame extends JFrame {
    private static ManagerFrame instance = null;

    private ManagerFrame() {        
        StudentProfileOverview.getInstance();
        Home.getInstance();
        Home.getInstance().updateBalance();
        
        this.add(Login.getInstance());
        this.setTitle(PageNames.LOGIN);
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
