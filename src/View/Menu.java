package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    public static int rows = 20;
    public static int columns = 20;

    public Menu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon imageIcon1 = new ImageIcon("src/Resources/pac-man.png");
        setIconImage(imageIcon1.getImage());

        ImageIcon imageIcon = new ImageIcon("src/Resources/Background.png");
        Image image = imageIcon.getImage();

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };


        panel.setLayout(null);

        JButton newGame = new JButton("New Game");
        JButton highScores = new JButton("High Scores");
        JButton exit = new JButton("Exit");

        newGame.setBounds(150, 100, 200, 100);
        highScores.setBounds(150, 250, 200, 100);
        exit.setBounds(150, 400, 200, 100);

        newGame.setFocusable(false);
        highScores.setFocusable(false);
        exit.setFocusable(false);

        newGame.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
        highScores.setFont(new Font("Georgia", Font.BOLD, 20));
        exit.setFont(new Font("Georgia", Font.BOLD, 20));

        newGame.setBackground(new Color(221, 160, 221));
        highScores.setBackground(new Color(221, 160, 221));
        exit.setBackground(new Color(221, 160, 221));

        newGame.setBorder(BorderFactory.createEtchedBorder());
        highScores.setBorder(BorderFactory.createEtchedBorder());
        exit.setBorder(BorderFactory.createEtchedBorder());


        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Rows:"));
                JTextField rowsField = new JTextField();
                panel.add(rowsField);
                panel.add(new JLabel("Columns:"));
                JTextField columnsField = new JTextField();
                panel.add(columnsField);

                // Show the JOptionPane with the input fields
                int result = JOptionPane.showConfirmDialog(Menu.this, panel, "Enter Grid Size",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    rows = Integer.parseInt(rowsField.getText());
                    columns = Integer.parseInt(columnsField.getText());
                    if ((rows > 100 || rows < 10) && columns > 100 || columns < 10) {
                        JOptionPane.showMessageDialog(null,
                                "Rows and columns must be bigger then 10 and smaller than 100",
                                "Hint",
                                JOptionPane.ERROR_MESSAGE);

                    } else {
                        SwingUtilities.invokeLater(() -> new GameMap());
                    }
                }
            }
        });

        highScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new HighScores());
            }
        });

        panel.add(newGame);
        panel.add(highScores);
        panel.add(exit);
        add(panel);

        setVisible(true);
    }

    public static int getRows() {
        return rows;
    }

    public static int getColumns() {
        return columns;
    }


}
