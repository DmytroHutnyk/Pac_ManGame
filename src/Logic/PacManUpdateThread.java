package Logic;

import View.FlorPanel;
import View.GameMap;
import View.PacManLabel;
import View.WallJPanel;

import javax.swing.*;
import java.awt.*;

public class PacManUpdateThread extends Thread {
    private PacManLabel pacManLabel;
    private final int NUM_OF_FRAMES = 10;
    private GameMap gameMap;
    private boolean running;
    private boolean isLeftUp;
    private int speedBonus;

    public PacManUpdateThread(PacManLabel pacManLabel, GameMap gameMap) {
        super("PacManUpdateThread");
        this.pacManLabel = pacManLabel;
        this.gameMap = gameMap;
        running = true;
        isLeftUp = true;
        speedBonus = 0;
    }

    @Override
    public void run() {
        while (running) {
            int newRow = pacManLabel.playerRow;
            int newCol = pacManLabel.playerColumn;
            if (pacManLabel.getDirection() == 'w') {
                isLeftUp = true;
                newRow -= 1;
            } else if (pacManLabel.getDirection() == 's') {
                isLeftUp = false;
                newRow += 1;
            } else if (pacManLabel.getDirection() == 'a') {
                isLeftUp = true;
                newCol -= 1;
            } else if (pacManLabel.getDirection() == 'd') {
                isLeftUp = false;
                newCol += 1;
            }

            if (pacManLabel.getDirection() != ' ' && canMoveTo(newRow, newCol)) {
                move(newCol * GameMap.getCellWidth(), newRow * GameMap.getCellHeight());
                pacManLabel.setLocation(newCol * GameMap.getCellWidth(), newRow * GameMap.getCellHeight()); // ok

                pacManLabel.playerRow = newRow;
                pacManLabel.playerColumn = newCol;
                removeBall();
                removeBonus();
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void removeBonus() {
        FlorPanel florPanel = (FlorPanel) GameMap.elements[pacManLabel.playerRow][pacManLabel.playerColumn];
        if (florPanel.bonusNumber != -1) {
            florPanel.removeAll();
            florPanel.revalidate();
            florPanel.repaint();

            if (florPanel.bonusNumber == 0 && speedBonus <= 10) {
                speedBonus++;
                gameMap.addImg("src/Resources/plus_speed.png");
            } else if (florPanel.bonusNumber == 1 && EnemyUpdateThread.speedDebuff < EnemyUpdateThread.NUM_OF_FRAMES) {
                EnemyUpdateThread.speedDebuff++;
                gameMap.addImg("src/Resources/minus_speed.png");
            } else if (florPanel.bonusNumber == 2 && pacManLabel.health <= 10) {
                pacManLabel.health++;
                gameMap.addImg("src/Resources/health.png");
            }

            florPanel.bonusNumber = -1;
        }
    }

    private void removeBall() {
        FlorPanel florPanel = (FlorPanel) GameMap.elements[pacManLabel.playerRow][pacManLabel.playerColumn];
        if (florPanel.hasYellowCircle) {
            florPanel.removeAll();
            florPanel.revalidate();
            florPanel.repaint();
            gameMap.increaseScore();
            florPanel.hasYellowCircle = false;
        }
    }

    private void move(int x, int y) {
        double differenceX = (pacManLabel.getX() - x) / (double) NUM_OF_FRAMES;
        double differenceY = (pacManLabel.getY() - y) / (double) NUM_OF_FRAMES;
        if (isLeftUp) {
            differenceX -= differenceX / NUM_OF_FRAMES;
            differenceY -= differenceY / NUM_OF_FRAMES;
        }

        for (int i = 0; i < NUM_OF_FRAMES; i++) {
            pacManLabel.setLocation((int) (pacManLabel.getX() - differenceX), (int) (pacManLabel.getY() - differenceY));

            updateIcon(i);
            try {
                Thread.sleep(500 / (NUM_OF_FRAMES + speedBonus));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateIcon(int i) {
        String name;
        if (pacManLabel.getDirection() == 'w') {
            name = i % 2 == 0 ? "src/Resources/pacman-up-mouth.png" : "src/Resources/pacman-up.png";
        } else if (pacManLabel.getDirection() == 's') {
            name = i % 2 == 0 ? "src/Resources/pacman-down-mouth.png" : "src/Resources/pacman-down.png";
        } else if (pacManLabel.getDirection() == 'a') {
            name = i % 2 == 0 ? "src/Resources/pacman-left-mouth.png" : "src/Resources/pacman-left.png";
        } else {
            name = i % 2 == 0 ? "src/Resources/pacman-right-mouth.png" : "src/Resources/pacman-right.png";
        }

        Image image = new ImageIcon(name).getImage();
        Image scaledImage = image.getScaledInstance(pacManLabel.getWidth(), pacManLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        pacManLabel.setIcon(scaledImageIcon);
    }

    private boolean canMoveTo(int row, int col) {
        JPanel panel = GameMap.elements[row][col];

        if (panel instanceof WallJPanel) {
            return false;
        } else if (panel instanceof FlorPanel) {
            return true;
        }

        return false;
    }

    public void stopThread() {
        running = false;
    }
}
