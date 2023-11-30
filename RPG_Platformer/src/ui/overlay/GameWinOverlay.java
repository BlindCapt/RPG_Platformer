package ui.overlay;

import main.Game;
import main.GameWindow;

import java.awt.*;

public class GameWinOverlay {

    public GameWinOverlay() {

    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(Game.GAME_WIDTH / 2 - (300 / 2), Game.GAME_HEIGHT / 2 - (400 / 2), 300, 400);
        g.setColor(Color.WHITE);
        g.drawString("GGWP you win", Game.GAME_WIDTH / 2 - 30, Game.GAME_HEIGHT / 2);
    }
}
