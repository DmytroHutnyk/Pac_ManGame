package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;  

public class HighScores extends JFrame implements Serializable {
    HighScores() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        ArrayList<PacManSerialization> pacManListRead = deserializeObjects();

        JList<PacManSerialization> jList = new JList<>(pacManListRead.toArray(new PacManSerialization[pacManListRead.size()]));

        jList.setCellRenderer(new CustomListCellRenderer());
        getContentPane().setBackground(Color.BLUE);
        getContentPane().add(new JScrollPane(jList));


    }

    public static void Ser() {
        ArrayList<PacManSerialization> books = deserializeObjects();
        books.add(new PacManSerialization(GameMap.name, GameMap.score));

        try {
            FileOutputStream fileOut = new FileOutputStream("src/Resources/HighScores.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(books);
            out.close();
            fileOut.close();
            System.out.println("PacManSerialization list serialized and saved to HighScores.ser");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PacManSerialization> deserializeObjects() {
        ArrayList<PacManSerialization> pacManListRead;
        try {
            FileInputStream fileIn = new FileInputStream("src/Resources/HighScores.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pacManListRead = (ArrayList<PacManSerialization>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException |
                 ClassNotFoundException e) {
            pacManListRead = new ArrayList<>();
        }
        return pacManListRead;
    }
}
