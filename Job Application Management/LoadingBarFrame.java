import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoadingBarFrame extends JFrame {
    private JProgressBar progressBar;

    public LoadingBarFrame() {
        super("Job Application System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 90);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null); // Center the frame

        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true); // Show percentage

        add(progressBar);

        // Example: Simulate loading
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    progressBar.setValue(i);
                    Thread.sleep(20); // Simulate some work
                   //opening Dashboard
                    if (i == 100) {
                        SwingUtilities.invokeLater(() -> {
                           new LoginWindow(); // Assuming you have a Dashboard class
                            dispose(); // Close the loading frame
                        });
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoadingBarFrame::new);
    }
}