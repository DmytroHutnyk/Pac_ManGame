package View;

import java.io.Serializable;

public class PacManSerialization implements Serializable {

    private String pacName = "Player";
    private int score = 0;

    public PacManSerialization() {
    }

    public PacManSerialization(String pacName, int score) {
        this.pacName = pacName;
        this.score = score;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getPacName() {
        return pacName;
    }

    public void setPacName(String name) {
        pacName = name;
    }

    public String toString() {
        return "name: " + this.pacName + " score: " + this.score;
    }

    public PacManSerialization createNewClass(String name, int score) {
        PacManSerialization pacManSerialization = new PacManSerialization(name, score);
        return pacManSerialization;
    }
}
