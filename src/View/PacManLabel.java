package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PacManLabel extends JLabel {
    private char direction; // w a s d
    public int playerRow = 0;
    public int playerColumn = 0;
    public int health;
    private ImageIcon[] pacmanIcons;

    PacManLabel() {
        health = 5;

        while (GameMap.elements[playerRow][playerColumn] instanceof WallJPanel) {
            playerRow = GameMap.random.nextInt(0, Menu.getRows());
            playerColumn = GameMap.random.nextInt(0, Menu.getColumns());
        }
        setLocation(playerColumn * GameMap.getCellWidth(),
                playerRow * GameMap.getCellHeight());
        direction = ' ';

        ImageIcon imageIcon = new ImageIcon("src/Resources/pac-man.png");
        Image image = imageIcon.getImage();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                setIcon(scaledImageIcon);

            }
        });

        pacmanIcons = new ImageIcon[4];
        pacmanIcons[0] = new ImageIcon("src/Resources/pacman-up.png");
        pacmanIcons[1] = new ImageIcon("src/Resources/pacman-left.png");
        pacmanIcons[2] = new ImageIcon("src/Resources/pacman-down.png");
        pacmanIcons[3] = new ImageIcon("src/Resources/pacman-right.png");
    }

    private void updateIcon() {
        int index = 0;
        if (direction == 'w') {
            index = 0;
        } else if (direction == 'a') {
            index = 1;
        } else if (direction == 's') {
            index = 2;
        } else if (direction == 'd') {
            index = 3;
        }

        Image image = pacmanIcons[index].getImage();
        Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        setIcon(scaledImageIcon);
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        if (direction == 'w' || direction == 'a' || direction == 's' || direction == 'd') {
            this.direction = direction;
            updateIcon();
        }
    }
}
