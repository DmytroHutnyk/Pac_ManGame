package Logic;

import javax.swing.*;

public class TimeUpdateThread extends Thread {
    private JLabel timeLabel;
    private long startTime;
    private boolean running;

    public TimeUpdateThread(JLabel timeLabel) {
        this.timeLabel = timeLabel;
        this.startTime = System.currentTimeMillis();
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            SwingUtilities.invokeLater(() -> {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                String time = formatElapsedTime(elapsedTime);
                timeLabel.setText(time);
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String formatElapsedTime(long elapsedTime) {
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }

    public void stopThread() {
        running = false;
    }
}
