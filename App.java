import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.ImageIcon;

public class App extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("The-Oddyesy");

        ImageIcon GameIcon = new ImageIcon("Assets/Images/Icon.png");
        frame.setIconImage(GameIcon.getImage());

        frame.getContentPane().setBackground(new Color(193, 228, 254));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Game game = new Game(frame);
        frame.add(game);

        Inputs input = new Inputs();
        frame.addKeyListener(input);

    }
}