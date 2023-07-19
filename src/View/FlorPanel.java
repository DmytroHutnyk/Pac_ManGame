package View;

import javax.swing.*;
import java.awt.*;

public class FlorPanel extends JPanel {
    public static int count = 0;
    public boolean hasYellowCircle;
    public int bonusNumber;

    public FlorPanel() {
        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout());

        ImageIcon imageIconCircle = new ImageIcon("src/Resources/yellowCircle.png");
        Image imageCircle = imageIconCircle.getImage();
        JLabel circleLabel = new JLabel();
        hasYellowCircle = true;
        bonusNumber = -1;

        Image scaledImageCircle = imageCircle.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIconCircle = new ImageIcon(scaledImageCircle);
        circleLabel.setIcon(scaledImageIconCircle);
        circleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        circleLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(circleLabel, BorderLayout.CENTER);
        count++;
    }
}
