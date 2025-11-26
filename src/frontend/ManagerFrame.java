package frontend;

import java.awt.Dimension;
import javax.swing.JFrame;

public class ManagerFrame extends JFrame {
    private static ManagerFrame instance = null;

    private ManagerFrame() {        
        this.add(StudentProfileOverview.getInstance());
        this.add(Home.getInstance());
        this.setTitle(PageNames.HOME);
        this.setMinimumSize(new Dimension(1000, 750));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static ManagerFrame getInstance() {
        if (instance == null) instance = new ManagerFrame();
        return instance;
    }
}
