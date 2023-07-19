package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomListCellRenderer extends DefaultListCellRenderer {


    private static final Color BACKGROUND_COLOR = new Color(221, 160, 221);
    private static final Color FOREGROUND_COLOR = Color.BLACK;
    private static final Font FONT = new Font("Georgia", Font.BOLD, 14);
    private static final Dimension CELL_SIZE = new Dimension(200, 100);
    private static final Border CELL_BORDER = new LineBorder(Color.BLACK, 1);


    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (renderer instanceof JLabel) {
            JLabel label = (JLabel) renderer;
            label.setBackground(BACKGROUND_COLOR);
            label.setForeground(FOREGROUND_COLOR);
            label.setFont(FONT);
        }
        renderer.setPreferredSize(CELL_SIZE);
        ((JComponent) renderer).setBorder(CELL_BORDER);

        return renderer;
    }

}