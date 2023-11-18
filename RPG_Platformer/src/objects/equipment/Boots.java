package objects.equipment;

import utilz.LoadSave;

import java.awt.image.BufferedImage;

import static utilz.Constants.ObjectConstants.*;

public class Boots extends Armor {
    /// ------------------------------- ATTRIBUTE ------------------------------- ///

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public Boots(int x, int y, int set, int level) {
        super("Boots", x, y, 10, SHOES, set);
        this.level = level;
        armor += level * 2;

        loadImage();
    }

    /// ------------------------------- METHOD ------------------------------- ///

    public void loadImage() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ARMOR_ATLAS);

        switch (setNumber) {
            case 0 -> image = temp.getSubimage(96, 0, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 1 -> image = temp.getSubimage(96, 3 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 2 -> image = temp.getSubimage(96, 6 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            default -> image = temp.getSubimage(96, 7 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
        }
    }

    /// ------------------------------- GETTER AND SETTER ------------------------------- ///
    public int getArmor() {
        return armor;
    }


}
