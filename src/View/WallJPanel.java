package View;

import javax.swing.*;
import java.awt.*;

public class WallJPanel extends JPanel {
    private Image image;

    WallJPanel() {
        setBackground(Color.BLUE);
        setOpaque(true);
    }

    public int getPanelWidth() {
        return getWidth();
    }

    public int getPanelHeight() {
        return getHeight();
    }

    public Color getColor() {
        return getBackground();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(this), image.getHeight(this));
    }
}
