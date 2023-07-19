package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class EnemyLabel extends JLabel {
    public PacManLabel pacManLabel;
    private char direction; // w a s d
    public int enemyRow = 0;
    public int enemyColumn = 0;

    EnemyLabel(PacManLabel pacManLabel) {
        this.pacManLabel = pacManLabel;

        while (GameMap.elements[enemyRow][enemyColumn] instanceof WallJPanel) {
            enemyRow = GameMap.random.nextInt(0, Menu.getRows());
            enemyColumn = GameMap.random.nextInt(0, Menu.getColumns());
        }
        setLocation(enemyColumn * GameMap.getCellWidth() + enemyColumn, enemyRow * GameMap.getCellHeight() + enemyRow);
        direction = ' ';

        ImageIcon imageIcon = new ImageIcon("src/Resources/pac-man2.png");
        Image image = imageIcon.getImage();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                setIcon(scaledImageIcon);

            }
        });
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        if (direction == 'w' || direction == 'a' || direction == 's' || direction == 'd') {
            this.direction = direction;
        }
    }
}
