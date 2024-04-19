import javax.swing.JFrame;
import java.awt.Color;


public class Game{
    public static void main(String[] args){
        JFrame frame = new JFrame("The-Oddyesy");
        frame.getContentPane().setBackground(new Color(120, 52, 124));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);
    }
}