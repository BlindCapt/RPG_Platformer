package objects;

import main.Game;
import objects.GameObject;

import java.util.Random;

public class Chest extends GameObject {
    /// ------------------------------- ATTRIBUTE ------------------------------- ///

    private int chestType;
    private int level;

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public Chest(int x, int y, int objType, int level) {
        super(x, y, objType);
        this.level = level;
        createHitbox();
        chestType = new Random().nextInt(3);
    }

    /// ------------------------------- METHOD ------------------------------- ///
    private void createHitbox() {
        initHitBox(25, 18);

        xDrawOffset = (int) (7 * Game.SCALE);
        yDrawOffset = (int) (12 * Game.SCALE);

        hitbox.y += yDrawOffset + (int) (Game.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    public void update() {
        updateAnimationTick();
    }

    /// ------------------------------- GETTER AND SETTER ------------------------------- ///


    public int getChestType() {
        return chestType;
    }

    public int getLevel() {
        return level;
    }
}
