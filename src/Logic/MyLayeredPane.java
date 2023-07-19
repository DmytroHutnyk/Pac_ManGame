package Logic;

import javax.swing.*;
import java.awt.*;

public class MyLayeredPane extends JLayeredPane {



    @Override
    public void doLayout() {
        super.doLayout();

        synchronized (getTreeLock()) {
            int w = getWidth();
            int h = getHeight();
            for (Component c : getComponents()) {
                c.setBounds(0, 0, w, h);
            }
        }
    }
}
