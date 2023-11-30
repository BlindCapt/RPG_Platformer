package gameStates;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends State implements StateMethods {
    /// ------------------------------- ATTRIBUTES ------------------------------- ///

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///

    public Menu(Game game) {
        super(game);
    }

    /// ------------------------------- METHOD ------------------------------- ///

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString("PRESS ENTER TO START", Game.GAME_WIDTH / 2 - 50, Game.GAME_HEIGHT / 2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /// ------------------------------- GETTER AND SETTERS ------------------------------- ///

}
