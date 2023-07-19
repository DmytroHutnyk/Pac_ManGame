package View;

import javax.swing.*;
import java.awt.*;

public class MyJPanel extends JPanel {
    public MyJPanel(LayoutManager manager, JLabel label) {
        super(manager);
        setSize(new Dimension(250, 100));
        setOpaque(false);
        setLayout(null);
        add(label);
    }
}
