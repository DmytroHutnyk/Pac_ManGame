package Logic;

import View.*;

import javax.swing.*;
import java.awt.*;

public class EnemyUpdateThread extends Thread {
    private final EnemyLabel enemyLabel;
    private final GameMap gameMap;
    public static final int NUM_OF_FRAMES = 10;
    private boolean running;
    private boolean isLeftUp;
    public static int speedDebuff;

    public EnemyUpdateThread(EnemyLabel enemyLabel, GameMap gameMap) {
        super("EnemyUpdateThread");
        this.enemyLabel = enemyLabel;
        this.gameMap = gameMap;
        running = true;
        isLeftUp = true;
        speedDebuff = 0;
    }

    @Override
    public void run() {
        PacManLabel pacManLabel = enemyLabel.pacManLabel;
        while (running) {
            int newRow, newCol;
            do {
                switch (GameMap.random.nextInt(0, 4)) {
                    case 0 -> enemyLabel.setDirection('w');
                    case 1 -> enemyLabel.setDirection('s');
                    case 2 -> enemyLabel.setDirection('a');
                    case 3 -> enemyLabel.setDirection('d');
                }

                newRow = enemyLabel.enemyRow;
                newCol = enemyLabel.enemyColumn;
                if (enemyLabel.getDirection() == 'w') {
                    isLeftUp = true;
                    newRow -= 1;
                } else if (enemyLabel.getDirection() == 's') {
                    isLeftUp = false;
                    newRow += 1;
                } else if (enemyLabel.getDirection() == 'a') {
                    isLeftUp = true;
                    newCol -= 1;
                } else if (enemyLabel.getDirection() == 'd') {
                    isLeftUp = false;
                    newCol += 1;
                }
            } while (!canMoveTo(newRow, newCol));

            move(newCol * GameMap.getCellWidth() + newCol, newRow * GameMap.getCellHeight());
            enemyLabel.setLocation(newCol * GameMap.getCellWidth() + newCol, newRow * GameMap.getCellHeight());

            if (pacManLabel.health > 0 &&
                    (pacManLabel.playerRow == newRow && pacManLabel.playerColumn == newCol ||
                            pacManLabel.playerRow == enemyLabel.enemyRow && pacManLabel.playerColumn == enemyLabel.enemyColumn)) {
                pacManLabel.health--;
                gameMap.removeOneHealth();

            }
            enemyLabel.enemyRow = newRow;
            enemyLabel.enemyColumn = newCol;

            generateBonus();
        }
    }

    private void generateBonus() {
        if (GameMap.random.nextInt(0, 100) < 5) {
            FlorPanel florPanel = (FlorPanel) GameMap.elements[enemyLabel.enemyRow][enemyLabel.enemyColumn];
            if (florPanel.bonusNumber == -1) {
                florPanel.bonusNumber = GameMap.random.nextInt(0, 3);
                String bonusName;
                switch (florPanel.bonusNumber) {
                    case 0 -> bonusName = "src/Resources/plus_speed.png";
                    case 1 -> bonusName = "src/Resources/minus_speed.png";
                    default -> bonusName = "src/Resources/health.png"; // 2
                }

                ImageIcon imageIconBonus = new ImageIcon(bonusName);
                Image imageBonus = imageIconBonus.getImage();

                JLabel bonusLabel = new JLabel();
                Image scaledImageBonus = imageBonus.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledImageIconBonus = new ImageIcon(scaledImageBonus);
                bonusLabel.setIcon(scaledImageIconBonus);
                bonusLabel.setHorizontalAlignment(SwingConstants.CENTER);
                bonusLabel.setVerticalAlignment(SwingConstants.CENTER);
                florPanel.add(bonusLabel, BorderLayout.CENTER);
            }
        }
    }

    private void move(int x, int y) {
        double differenceX = (enemyLabel.getX() - x) / (double) NUM_OF_FRAMES;
        double differenceY = (enemyLabel.getY() - y) / (double) NUM_OF_FRAMES;
        if (isLeftUp) {
            differenceX -= differenceX / NUM_OF_FRAMES;
            differenceY -= differenceY / NUM_OF_FRAMES;
        }

        for (int i = 0; i < NUM_OF_FRAMES; i++) {
            enemyLabel.setLocation((int) (enemyLabel.getX() - differenceX), (int) (enemyLabel.getY() - differenceY));

            try {
                Thread.sleep(500 / (NUM_OF_FRAMES - speedDebuff));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
