import javax.swing.JFrame;
import java.io.File;
import javax.swing.*;
//Graphics Imports.
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import javax.swing.ImageIcon;
import java.awt.Image;
//Font Imports.
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
//Audio Imports
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game extends JPanel implements ActionListener {
    JFrame Frame;

    // Background Color var.
    Color BackgroundColor = new Color(95, 205, 228);

    // Image Vars;
    ImageIcon TrireimGif;
    Image Trireme;
    Image Water;
    Image ImgRocks;
    Image Sun;
    Image LargeCloud;

    float TimeInSeconds;
    float TimeCallAmount;

    // TextObj Vars.
    ArrayList<TextObj> AllTextObjs = new ArrayList<TextObj>();
    TextObj Title;
    TextObj UnderTitle;
    TextObj PressEnter;

    // Font Vars.
    Font PixleFont;

    // Custom GameObject Vars
    GameObject TriremeObj;
    GameObject TriremeObj1;
    GameObject TriremeObj2;
    GameObject SunObj;

    ArrayList<GameObject> Rocks = new ArrayList<GameObject>();
    ArrayList<GameObject> Clouds = new ArrayList<GameObject>();
    ArrayList<GameObject> WaterObjs = new ArrayList<GameObject>();
    ArrayList<GameObject> GameObjectsToRender = new ArrayList<GameObject>();

    // Cooldown Vars
    float CurrentCoolDownWater = 0;
    float CurrentCoolDownPressEnter = 50;
    int T;
    boolean TitleScreenVoiceActive = false;
    boolean IncreaseAlpha = true;

    Game(JFrame frame) {
        this.Frame = frame;
        setBackground(new Color(193, 228, 254));

        LoadImages();
        LoadFonts();

        Start();
        Timer timer = new Timer(8, this);
        timer.start();

        setSize(frame.getSize());

    }

    void LoadImages() {
        Trireme = new ImageIcon(getClass().getResource("Assets/Images/Trireme.gif")).getImage();
        Sun = new ImageIcon(getClass().getResource("Assets/Images/Sun.gif")).getImage();
        Water = new ImageIcon(getClass().getResource("Assets/Images/Water.gif")).getImage();
        ImgRocks = new ImageIcon(getClass().getResource("Assets/Images/Rock.gif")).getImage();
        LargeCloud = new ImageIcon(getClass().getResource("Assets/Images/CloudLarge.png")).getImage();

    }

    void LoadFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            PixleFont = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Fonts/PixleFont.ttf")).deriveFont(64f);
            ge.registerFont(PixleFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
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

    public class GameObject {
        float PosX;
        float PosY;
        float ObjSpeed;

        Image img;

        GameObject(int X, int Y, Image img, float Speed) {
            this.PosX = X;
            this.PosY = Y;
            this.ObjSpeed = Speed;

            if (img != null) {
                this.img = img;
                GameObjectsToRender.add(this);
            }
        }

        GameObject(int X, int Y, Image img) {
            this.PosX = X;
            this.PosY = Y;

            if (img != null) {
                this.img = img;
                GameObjectsToRender.add(this);
            }
        }
    }

    public class TextObj {
        int PosX;
        int PosY;

        String DisplayText;
        Font FontToUse;

        Color TextColor;

        TextObj(int x, int y, String Text, Font FontToUse, Color TextColor) {
            this.PosX = x;
            this.PosY = y;
            this.DisplayText = Text;
            this.FontToUse = FontToUse;
            this.TextColor = TextColor;

            AllTextObjs.add(this);
        }

    }

    void Start() {
        System.err.println("Start function has played so it should be all good.... Hopefully.");
        PlayMusic("Assets/Sounds/The_Oddyessy.wav");

        // Creates Rock objs.
        for (int i = 0; i < 10; i++) {
            int RockPosX = i + (int) (Math.random() * Frame.getWidth());
            int RockPosY = (int) ((Math.random() * Frame.getHeight() / 2) + (int) (Frame.getHeight() * 0.65));
            Rocks.add(new GameObject(RockPosX, RockPosY, ImgRocks));
        }

        // creates Cloud objs.
        for (int i = 0; i < 10; i++) {
            int RockPosX = i + (int) (Math.random() * Frame.getWidth());
            int RockPosY = (int) ((Math.random() * Frame.getHeight() / 8));
            Clouds.add(new GameObject(RockPosX, RockPosY, LargeCloud, (float) (Math.random() * 0.5f)));
        }

        // Creates Water objs.
        for (int i = -Water.getWidth(Frame); i < Frame.getWidth(); i += Water.getWidth(Frame)) {
            WaterObjs.add(new GameObject(i, Frame.getHeight() / 2 + (int) (Trireme.getHeight(Frame) / 1.5f), Water));
        }

        // Custom Objs.
        SunObj = new GameObject(Sun.getWidth(Frame), Sun.getHeight(Frame), Sun);
        TriremeObj = new GameObject(Frame.getWidth(), Frame.getHeight() / 2 + 80, Trireme);
        TriremeObj1 = new GameObject(Frame.getWidth() + 150, Frame.getHeight() / 2 + 125, Trireme);
        TriremeObj2 = new GameObject(Frame.getWidth() + 95, Frame.getHeight() / 2 + 45, Trireme);

        // Custom text.
        Title = new TextObj(Frame.getWidth(), Frame.getHeight() / 4, "The Odyssey", PixleFont, Color.black);
        UnderTitle = new TextObj(Frame.getWidth(), Frame.getHeight() / 4 + 64, "Legends of Odysseus", PixleFont,
                Color.black);

        PressEnter = new TextObj(Frame.getWidth(), (int) (Frame.getHeight() / 1.4), "Press Enter", PixleFont,
                Color.black);
    }

    void Update() {

        // Updates TriremeObj to move
        if (TriremeObj.PosX > Frame.getWidth() / 2) {
            TriremeObj.PosX -= 1;
            TriremeObj1.PosX -= 1.25f;
            TriremeObj2.PosX -= 1.35f;
        } else {
            for (GameObject Obj : WaterObjs) {
                Obj.PosX += 1.25f;
                if (Obj.PosX >= Frame.getWidth()) {
                    Obj.PosX = -Obj.img.getWidth(Frame);
                }
            }
            for (GameObject Obj : Rocks) {
                Obj.PosX += 1.25f;
                if (Obj.PosX >= Frame.getWidth()) {
                    Obj.PosX = -Obj.img.getWidth(Frame);
                    Obj.PosY = (int) ((Math.random() * Frame.getHeight() / 2) + (int) (Frame.getHeight() * 0.54));
                    System.err.println(Rocks.size());
                }
            }
        }

        // Moves Sun
        SunObj.PosX += 0.15;

        // Moves Clouds
        for (GameObject Obj : Clouds) {
            Obj.PosX -= Obj.ObjSpeed;
            if (Obj.PosX <= -Obj.img.getWidth(Frame)) {
                Obj.PosX = Frame.getWidth() + Obj.img.getWidth(Frame);
            }
        }

        // Timers for playing sound.
        if (TimeInSeconds >= CurrentCoolDownWater) {
            CurrentCoolDownWater += 2.25f;
            PlayMusic("Assets/Sounds/Water.wav");
        }

        if (TimeInSeconds >= CurrentCoolDownPressEnter) {
            CurrentCoolDownPressEnter += 30;
            PlayMusic("Assets/Sounds/PressEnterToStart.wav");
        }

        if (!TitleScreenVoiceActive && TimeInSeconds > 5) {
            PlayMusic("Assets/Sounds/LegendsOfOdysseus.wav");
            TitleScreenVoiceActive = true;
        }

        // In Game Timer.
        TimeInSeconds = TimeCallAmount / 31.25f;
        TimeCallAmount++;

        // Fades in and out the PressEnter text.
        if (T < 256 && IncreaseAlpha) {
            PressEnter.TextColor = new Color(0, 0, 0, T += 2);
            if (T >= 175) {
                IncreaseAlpha = false;
            }
        } else if (T > 0) {
            PressEnter.TextColor = new Color(0, 0, 0, T -= 2);
            if (T <= 50) {
                IncreaseAlpha = true;
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Draw(g);
    }

    void Draw(Graphics g) {
        // Sets background Color;
        g.setColor(BackgroundColor);

        // Creates sea background.
        g.fillRect(0, Frame.getHeight() / 2 + 45, Frame.getWidth(), Trireme.getHeight(Frame) * 25);

        // Draws all objects that have a image.
        for (GameObject Obj : GameObjectsToRender) {
            if (Obj.img != null) {
                g.drawImage(Obj.img, (int) Obj.PosX, (int) Obj.PosY, null);
            } else {
                GameObjectsToRender.remove(Obj);
            }
        }

        // Use the custom font
        for (TextObj textObj : AllTextObjs) {
            g.setFont(textObj.FontToUse);
            g.setColor(textObj.TextColor);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(textObj.DisplayText, (textObj.PosX - fm.stringWidth(textObj.DisplayText)) / 2, textObj.PosY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        Update();
    }
}