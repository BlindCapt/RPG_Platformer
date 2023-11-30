package ui.overlay;

import entities.Enemy;
import gameStates.Playing;
import main.Game;
import ui.button.UpgradeButton;
import ui.button.UpgradeLabel;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class AltarOverlay {

    // Access to playing method
    private final Playing playing;

    // Position and size
    private int bgX;
    private int bgY;
    private int bgWidth;
    private int bgHeight;
    private Rectangle2D.Float hitBoxReloadButton;
    private int reloadX, reloadY, reloadWidth, reloadHeight;

    // Background sprite
    private BufferedImage background;

    // Button and label
    private UpgradeLabel[] listLabel;
    private UpgradeButton[] listButton;
    private BufferedImage[] reloadMapButton;

    // Prices
    private int upLevelPrice, upXPPrice, upGoldPrice, upDropRatePrice;

    private boolean reloadIsPressed;

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public AltarOverlay(Playing playing) {
        this.playing = playing;
        background = LoadSave.GetSpriteAtlas(LoadSave.ALTAR_UI);

        bgX = Game.GAME_WIDTH / 2 - background.getWidth() / 2;
        bgY = Game.GAME_HEIGHT / 2 - background.getHeight() / 2;
        bgWidth = background.getWidth();
        bgHeight = background.getHeight();

        initButtonAndLabel();
    }

    /// ------------------------------- METHOD ------------------------------- ///

    public void initButtonAndLabel() {
        listLabel = new UpgradeLabel[4];
        listButton = new UpgradeButton[4];

        // Level
        listLabel[0] = new UpgradeLabel(450, 95, 0);
        listButton[0] = new UpgradeButton(705, 95 + listLabel[0].getHeight() / 4, 100, 0, playing);

        // XP
        listLabel[1] = new UpgradeLabel(450, 190, 1);
        listButton[1] = new UpgradeButton(705, 190 + listLabel[0].getHeight() / 4, 150, 1, playing);

        // Gold
        listLabel[2] = new UpgradeLabel(450, 285, 2);
        listButton[2] = new UpgradeButton(705, 285 + listLabel[0].getHeight() / 4, 350, 2, playing);

        // Drop rate item
        listLabel[3] = new UpgradeLabel(450, 380, 3);
        listButton[3] = new UpgradeButton(705, 380 + listLabel[0].getHeight() / 4, 500, 3, playing);

        // Reload
        reloadMapButton = new BufferedImage[2];

        reloadMapButton[0] = LoadSave.GetSpriteAtlas(LoadSave.RELOAD_BUTTON_ATLAS).getSubimage(0, 0, 648, 190);
        reloadMapButton[1] = LoadSave.GetSpriteAtlas(LoadSave.RELOAD_BUTTON_ATLAS).getSubimage(0, 190, 648, 190);

        reloadWidth = reloadMapButton[0].getWidth() / 2;
        reloadHeight = reloadMapButton[0].getHeight() / 2;
        reloadX = Game.GAME_WIDTH / 2 - reloadWidth / 2;
        reloadY = (int) (Game.GAME_HEIGHT * 0.8) - reloadHeight / 2;

        hitBoxReloadButton = new Rectangle2D.Float(reloadX, reloadY, reloadWidth, reloadHeight);

    }

    public void update() {
    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(background, bgX, bgY, bgWidth, bgHeight, null);

        for (UpgradeLabel label : listLabel) {
            label.draw(g);
        }

        for (UpgradeButton button : listButton) {
            button.draw(g);
            g.drawString(String.valueOf(button.getPrice()), button.getX() + button.getWidth() / 2 - 10, button.getY() + button.getHeight() / 2 + 5);

        }

        if (reloadIsPressed)
            g.drawImage(reloadMapButton[1], reloadX, reloadY, reloadWidth, reloadHeight, null);
        else
            g.drawImage(reloadMapButton[0], reloadX, reloadY, reloadWidth, reloadHeight, null);

    }



    //////////// Event handlers ////////////

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        for (UpgradeButton button : listButton) {
            if (button.getHitBox().contains(e.getX(), e.getY())) {
                button.setMousePressed(true);
            }
        }

        if (hitBoxReloadButton.contains(e.getX(), e.getY())) {
            reloadIsPressed = true;
        }

    }

    public void mouseReleased(MouseEvent e) {
        for (UpgradeButton button : listButton) {
            if (button.getHitBox().contains(e.getX(), e.getY()) && button.isMousePressed()) {
                button.action();
            }
            button.setMousePressed(false);
        }

        if (hitBoxReloadButton.contains(e.getX(), e.getY()) && reloadIsPressed) {
            playing.resetAll();
        }

        reloadIsPressed = false;

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }


    /// ------------------------------- GETTER AND SETTER ------------------------------- ///

}
