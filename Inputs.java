import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inputs implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Game.CurrentState = Game.GameStates.Game;
            GameLogic.StartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
