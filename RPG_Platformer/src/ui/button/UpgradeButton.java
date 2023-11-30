package ui.button;

import gameStates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class UpgradeButton {
    /// ------------------------------- ATTRIBUTE ------------------------------- ///

    private int x, y, width, height, upgradeType;
    private BufferedImage[] buttonImage;
    private int price;
    private boolean mousePressed;
    private Rectangle2D.Float hitBox;
    private Playing playing;

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///

    public UpgradeButton(int x, int y, int price, int upgradeType, Playing playing) {
        loadImage();
        width = buttonImage[0].getWidth() / 3;
        height = buttonImage[0].getHeight() / 3;
        this.x = x;
        this.y = y;
        this.price = price;
        this.upgradeType = upgradeType;
        this.playing = playing;
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    /// ------------------------------- METHOD ------------------------------- ///

    public void draw(Graphics g) {
        if (mousePressed)
            g.drawImage(buttonImage[1], x, y, width, height, null);
        else
            g.drawImage(buttonImage[0], x, y, width, height, null);
    }

    public void loadImage() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.UPGRADE_BUTTON_ATLAS);

        buttonImage = new BufferedImage[2];
        buttonImage[0] = temp.getSubimage(0, 0, 256, 128);
        buttonImage[1] = temp.getSubimage(0, 128, 256, 128);
    }

    public void action() {
        if (playing.getPlayer().getArgent() >= price){
            playing.getPlayer().setArgent(playing.getPlayer().getArgent() - price);
            switch (upgradeType) {
                case 0 -> playing.getEnemyManager().changeEnemiesLevel();
                case 1 -> playing.getEnemyManager().changeEnemiesXPRate();
                case 2 -> playing.getEnemyManager().changeEnemiesGoldRate();
                case 3 -> playing.getEnemyManager().changeEnemiesDropRate();
            }
            price += 100;
        }



    }


    /// ------------------------------- GETTER AND SETTER ------------------------------- ///


    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getHeight() {
        return height;
    }

    public int getPrice() {
        return price;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getUpgradeType() {
        return upgradeType;
    }
}