import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {
    JFrame Frame;

    ImageIcon TrireimGif;
    Image Trireim;
    Image Water;
    Image ImgRocks;
    Image Sun;

    Game(JFrame frame) {
        this.Frame = frame;
        setBackground(new Color(193, 228, 254));

        LoadImages();

        Start();
        Timer timer = new Timer(8, this);
        timer.start();

        setSize(frame.getSize());

    }

    void LoadImages() {
        Trireim = new ImageIcon(getClass().getResource("Assets/Images/Trireme.gif")).getImage();
        Sun = new ImageIcon(getClass().getResource("Assets/Images/Sun.gif")).getImage();
        Water = new ImageIcon(getClass().getResource("Assets/Images/Water.gif")).getImage();
        ImgRocks = new ImageIcon(getClass().getResource("Assets/Images/Rock.gif")).getImage();
        if (Trireim != null) {
            System.err.println("It worked image");
        }

    }

    public void PlayMusic(String location) {
        try {
            File MusicPath = new File(location);

            if (MusicPath.exists()) {
                AudioInputStream AudioInput = AudioSystem.getAudioInputStream(MusicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(AudioInput);
                clip.start();
            }

        } catch (Exception e) {
        }
    }

    float TimeInSeconds;
    float TimeCallAmount;
    int StartPos;

    ArrayList<GameObject> Rocks = new ArrayList<GameObject>();

    public class GameObject {
        int PosX;
        int PosY;

        GameObject(int X, int Y) {
            PosX = X;
            PosY = Y;
        }
    }

    void Start() {
        StartPos = Frame.getWidth();
        System.err.println("Starting Game");
        PlayMusic("Assets/Sounds/The_Oddyessy.wav");
        System.err.println(Frame.getWidth());
        for (int i = 0; i < 10; i++) {
            Rocks.add(new GameObject(i + (int) (Math.random() * StartPos),
                    (int) (Math.random() * Frame.getHeight() / 2) + (int) (Frame.getHeight() * 0.5)));
        }
    }

    float CurrentCoolDownWater = 0;

    float CurrentCoolDownPressEnter = 50;

    boolean TitleScreenVoice = false;

    void Update() {

        if (StartPos <= 0 - Trireim.getWidth(Frame)) {
            StartPos = Frame.getWidth();
        }
        if (TimeInSeconds >= CurrentCoolDownWater) {
            CurrentCoolDownWater += 2.25f;
            PlayMusic("Assets/Sounds/Water.wav");
        }

        if (TimeInSeconds >= CurrentCoolDownPressEnter) {
            CurrentCoolDownPressEnter += 30;
            PlayMusic("Assets/Sounds/PressEnterToStart.wav");
        }

        if (TimeInSeconds > 5 && !TitleScreenVoice) {
            PlayMusic("Assets/Sounds/LegendsOfOdysseus.wav");
            TitleScreenVoice = true;
        }

        TimeInSeconds = TimeCallAmount / 31.25f;
        TimeCallAmount++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Draw(g);
    }

    void Draw(Graphics g) {
        StartPos -= 1;
        g.setColor(new Color(95, 205, 228));
        g.fillRect(0, Frame.getHeight() / 2 + 45, Frame.getWidth(), Trireim.getHeight(Frame) * 25);

        for (int i = 0; i < Frame.getWidth(); i += Water.getWidth(Frame)) {
            g.drawImage(Water, i, Frame.getHeight() / 2 + (int) (Trireim.getHeight(Frame) / 1.5f), null);
        }
        for (int e = 0; e < Rocks.size(); e++) {
            g.drawImage(ImgRocks, Rocks.get(e).PosX, Rocks.get(e).PosY, null);
        }

        g.drawImage(Trireim, StartPos, Frame.getHeight() / 2 + 80, null);
        g.drawImage(Sun, Sun.getWidth(Frame), Sun.getHeight(Frame), null);

        Font font = new Font("TimesRoman", Font.BOLD, 50);
        g.setFont(font);
        g.drawString("The Odyssey", Frame.getWidth() / 2, Frame.getHeight() / 3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        Update();
    }
}
