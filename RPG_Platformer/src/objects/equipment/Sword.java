package objects.equipment;

import utilz.LoadSave;

import java.awt.image.BufferedImage;

import static utilz.Constants.ObjectConstants.*;

public class Sword extends Weapon {

    /// ------------------------------- ATTRIBUTE ------------------------------- ///
    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public Sword(int x, int y, int tier, int level) {
        super("Sword", 5, WEAPON);
        this.x = x;
        this.y = y;
        this.tier = tier;
        this.level = level;
        damage += level + tier * 10;

        loadImage();
        initHitBox(ARMOR_WIDTH, ARMOR_HEIGHT);
    }
    /// ------------------------------- GETTER AND SETTER ------------------------------- ///
    /// ------------------------------- METHOD ------------------------------- ///

    public void loadImage() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.WEAPON_ATLAS);

        switch (tier) {
            case 0 -> image = temp.getSubimage(0, 2 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 1 -> image = temp.getSubimage(0, 3 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 2 -> image = temp.getSubimage(0, 4 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 3 -> image = temp.getSubimage(0, 6 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 4 -> image = temp.getSubimage(0, 7 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
        }
    }

    public boolean isGodSlayer() {
        return tier == 5;
    }

    public void buffGod() {
        if (isGodSlayer()) {
            damage = damage * 2000000000;
        }
    }


}
