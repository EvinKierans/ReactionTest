import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {
    public JFrame frame;
    public Window(int width, int height, String title, Test test) {
        frame = new JFrame();
        frame.setTitle(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(test);
        frame.setVisible(true);
        test.start();
    }
}
