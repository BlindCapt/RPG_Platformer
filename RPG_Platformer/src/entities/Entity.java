package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.PlayerConstants.*;

public abstract class Entity {
    /// ------------------------------- ATTRIBUTES ------------------------------- ///

    // Size and position
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;


    // Animation
    protected int aniIndex;
    protected int aniTick;
    protected int state;

    // In air and movement
    protected float airSpeed;
    protected boolean inAir = false;
    protected float walkSpeed = 1.5f;

    //Health
    protected int maxHealth;
    protected int currentHealth;
    protected int attack;
    protected int defense;


    // AttackBox
    protected Rectangle2D.Float attackBox;

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /// ------------------------------- METHOD ------------------------------- ///

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    protected void initAttackBox(int width, int height) {
        attackBox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    protected void drawHitBox(Graphics g, int xLVlOffset, int yLvlOffset) {
        // for debugging the hitbox
        g.setColor(Color.RED);
        g.drawRect((int) hitBox.x - xLVlOffset, (int) hitBox.y - yLvlOffset, (int) hitBox.width, (int) hitBox.height);
    }

    protected void drawAttackBox(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);
    }


    /// ------------------------------- GETTER AND SETTER ------------------------------- ///


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getState() {
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getAniTick() {
        return aniTick;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
}
