package View;

import Logic.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GameMap extends JFrame implements KeyListener {
    public static int level = 0;
    public static int score = 0;
    public static JPanel[][] elements;
    public static Random random;
    private final PacManLabel pacManLabel;
    private static JPanel mapPanel;
    private static JTable jTable;
    private static ArrayList<JLabel> healthLabels;
    private final BottomPanel bottomPanel;
    private JLabel scoreLabel;
    public static String name;
    private final TimeUpdateThread timeUpdateThread;
    private final PacManUpdateThread pacmanThread;
    private final EnemyUpdateThread enemyThread0;
    private final EnemyUpdateThread enemyThread1;
    private final EnemyUpdateThread enemyThread2;
    private final EnemyUpdateThread enemyThread3;

    public GameMap() {
        level++;
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mapPanel = new JPanel();
        mapPanel.setBackground(Color.black);
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setSize(new Dimension(700, 700));

        random = new Random();
        elements = drawMap(Menu.getRows(), Menu.getColumns());

        MapTableModel mapTableModel = new MapTableModel(elements);
        jTable = new JTable(mapTableModel);
        jTable.setShowGrid(false);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                return (JPanel) value;
            }
        };

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        mapPanel.add(jTable);

        pacManLabel = new PacManLabel();
        JPanel pacManPanel = new MyJPanel(null, pacManLabel);


        EnemyLabel enemyLabel0 = new EnemyLabel(pacManLabel);
        JPanel enemyPanel0 = new MyJPanel(null, enemyLabel0);
        EnemyLabel enemyLabel1 = new EnemyLabel(pacManLabel);
        JPanel enemyPanel1 = new MyJPanel(null, enemyLabel1);
        EnemyLabel enemyLabel2 = new EnemyLabel(pacManLabel);
        JPanel enemyPanel2 = new MyJPanel(null, enemyLabel2);
        EnemyLabel enemyLabel3 = new EnemyLabel(pacManLabel);
        JPanel enemyPanel3 = new MyJPanel(null, enemyLabel3);

        UpperPanel upperPanel = getUpperPanel();
        bottomPanel = getBottomPanel();

        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        timeLabel.setForeground(Color.white);

        timeUpdateThread = new TimeUpdateThread(timeLabel);
        timeUpdateThread.start();
        upperPanel.add(timeLabel);

        MyLayeredPane layeredPane = new MyLayeredPane();
        layeredPane.setPreferredSize(new Dimension(700, 700));
        layeredPane.add(mapPanel, Integer.valueOf(0));
        layeredPane.add(pacManPanel, Integer.valueOf(2));
        layeredPane.add(enemyPanel0, Integer.valueOf(1));
        layeredPane.add(enemyPanel1, Integer.valueOf(1));
        layeredPane.add(enemyPanel2, Integer.valueOf(1));
        layeredPane.add(enemyPanel3, Integer.valueOf(1));

        add(upperPanel, BorderLayout.NORTH);
        add(layeredPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    int keyCode = e.getKeyCode();
                    int modifiers = e.getModifiersEx();

                    if (modifiers == (KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK) && keyCode == KeyEvent.VK_Q) {
                        end(true);
                    } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                        pacManLabel.setDirection('w');
                    } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                        pacManLabel.setDirection('s');
                    } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                        pacManLabel.setDirection('a');
                    } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                        pacManLabel.setDirection('d');
                    }
                }
                return false;
            }
        });


        jTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int rowHeight = mapPanel.getHeight() / jTable.getRowCount();
                jTable.setRowHeight(rowHeight);
                int rowWidth = mapPanel.getWidth() / jTable.getColumnCount();
                pacManLabel.setSize(rowWidth, rowHeight);
                enemyLabel0.setSize(rowWidth, rowHeight);
                enemyLabel1.setSize(rowWidth, rowHeight);
                enemyLabel2.setSize(rowWidth, rowHeight);
                enemyLabel3.setSize(rowWidth, rowHeight);

                pacManLabel.setLocation(pacManLabel.playerColumn * getCellWidth(),
                        pacManLabel.playerRow * getCellHeight());
                enemyLabel0.setLocation(enemyLabel0.enemyColumn * getCellWidth(),
                        enemyLabel0.enemyRow * getCellHeight());
                enemyLabel1.setLocation(enemyLabel1.enemyColumn * getCellWidth(),
                        enemyLabel1.enemyRow * getCellHeight());
                enemyLabel2.setLocation(enemyLabel2.enemyColumn * getCellWidth(),
                        enemyLabel2.enemyRow * getCellHeight());
                enemyLabel3.setLocation(enemyLabel3.enemyColumn * getCellWidth(),
                        enemyLabel3.enemyRow * getCellHeight());

                pacManPanel.setPreferredSize(new Dimension(rowWidth, rowHeight));
                enemyPanel0.setPreferredSize(new Dimension(rowWidth, rowHeight));
                enemyPanel1.setPreferredSize(new Dimension(rowWidth, rowHeight));
                enemyPanel2.setPreferredSize(new Dimension(rowWidth, rowHeight));
                enemyPanel3.setPreferredSize(new Dimension(rowWidth, rowHeight));
            }
        });



        ImageIcon imageIcon1 = new ImageIcon("src/Resources/pac-man.png");
        setIconImage(imageIcon1.getImage());


        addKeyListener(this);
        setFocusable(true);
        pacmanThread = new PacManUpdateThread(pacManLabel, this);
        pacmanThread.start();
        enemyThread0 = new EnemyUpdateThread(enemyLabel0, this);
        enemyThread0.start();
        enemyThread1 = new EnemyUpdateThread(enemyLabel1, this);
        enemyThread1.start();
        enemyThread2 = new EnemyUpdateThread(enemyLabel2, this);
        enemyThread2.start();
        enemyThread3 = new EnemyUpdateThread(enemyLabel3, this);
        enemyThread3.start();


        setVisible(true);
        pack();
        validate();
    }

    public void increaseScore() {
        score++;
        scoreLabel.setText("     Score: " + score);
        if (score == FlorPanel.count) {
            end(false);
        }
    }

    private void end(boolean isEnd) {
        pacmanThread.stopThread();
        enemyThread0.stopThread();
        enemyThread1.stopThread();
        enemyThread2.stopThread();
        enemyThread3.stopThread();
        timeUpdateThread.stopThread();

        FlorPanel.count = 0;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isEnd) {

                JPanel panel = new JPanel(new GridLayout(1, 1));
                panel.add(new JLabel("Name: "));
                JTextField rowsField = new JTextField();
                panel.add(rowsField);
                int result = JOptionPane.showConfirmDialog(GameMap.this, panel, "Save game",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    name = rowsField.getText();
                    HighScores.Ser();

                }
                dispose();


            dispose();
        }
        else {

            dispose();
            SwingUtilities.invokeLater(() -> new GameMap());
        }

    }

    private UpperPanel getUpperPanel() {
        UpperPanel upperPanel = new UpperPanel();
        upperPanel.setPreferredSize(new Dimension(700, 40));

        scoreLabel = new JLabel("     Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreLabel.setForeground(Color.white);

        upperPanel.add(scoreLabel);
        return upperPanel;
    }

    public void removeOneHealth() {
        JLabel healthLabel = healthLabels.remove(healthLabels.size() - 1);
        bottomPanel.remove(healthLabel);
        bottomPanel.revalidate();
        bottomPanel.repaint();
        if (healthLabels.size() == 0) {
            end(true);
        }
    }

    private BottomPanel getBottomPanel() {
        BottomPanel bottomPanel = new BottomPanel();
        bottomPanel.setPreferredSize(new Dimension(700, 40));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        healthLabels = new ArrayList<>();
        for (int i = 0; i < pacManLabel.health; i++) {
            ImageIcon imageIcon = new ImageIcon("src/Resources/health.png");
            Image imageHealth = imageIcon.getImage();
            JLabel healthLabel = new JLabel();

            Image scaledImageCircle = imageHealth.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIconCircle = new ImageIcon(scaledImageCircle);
            healthLabel.setIcon(scaledImageIconCircle);
            healthLabel.setHorizontalAlignment(SwingConstants.CENTER);
            healthLabel.setVerticalAlignment(SwingConstants.CENTER);
            bottomPanel.add(healthLabel, BorderLayout.CENTER);
            healthLabels.add(healthLabel);
        }

        return bottomPanel;
    }

    public void addImg(String imgName) {
        ImageIcon imageIcon = new ImageIcon(imgName);
        Image imageHealth = imageIcon.getImage();
        JLabel healthLabel = new JLabel();

        Image scaledImageCircle = imageHealth.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIconCircle = new ImageIcon(scaledImageCircle);
        healthLabel.setIcon(scaledImageIconCircle);
        healthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        healthLabel.setVerticalAlignment(SwingConstants.CENTER);
        bottomPanel.add(healthLabel, BorderLayout.CENTER);
        if (imgName.equals("src/Resources/health.png")) {
            healthLabels.add(healthLabel);
        }
    }

    public static int getCellHeight() {
        return mapPanel.getHeight() / jTable.getRowCount();
    }

    public static int getCellWidth() {
        return mapPanel.getWidth() / jTable.getColumnCount();
    }

    private JPanel[][] drawMap(int rows, int columns) {
        JPanel[][] result = new JPanel[rows][columns];

        for (int i = 0; i < result[0].length; i++) {
            result[0][i] = new WallJPanel();
            result[result.length - 1][i] = new WallJPanel();
        }

        for (int i = 1; i < result.length - 1; i++) {
            result[i][0] = new WallJPanel();
            result[i][result[0].length - 1] = new WallJPanel();
        }

        for (int i = 1; i < result.length - 1; i++) {
            for (int j = 1; j < result[0].length - 1; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    result[i][j] = new WallJPanel();
                } else {
                    result[i][j] = new FlorPanel();
                }
            }
        }

        return result;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
