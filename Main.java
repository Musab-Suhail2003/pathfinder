import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        MyPanel mp = new MyPanel();

        window.add(mp);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        String x = "Press Enter to find path with BFS" + "\nPress C to clear Path" + "\nPress Esc to clear the maze" +
                "\nClick with mouse to toggle solid block";

        System.out.println(x);

    }
}