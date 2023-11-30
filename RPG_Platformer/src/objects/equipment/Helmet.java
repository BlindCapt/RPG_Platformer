package objects.equipment;

import objects.equipment.Armor;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.ObjectConstants.*;


public class Helmet extends Armor {

    /// ------------------------------- ATTRIBUTE ------------------------------- ///


    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public Helmet(int x, int y, int set, int level) {
        super("Helmet", x, y, 2, HELMET, set);
        this.level = level;
        armor += level;


        loadImage();

    }

    /// ------------------------------- METHOD ------------------------------- ///


    public void loadImage() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ARMOR_ATLAS);

        switch (setNumber) {
            case 0 -> image = temp.getSubimage(0, 0, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 1 -> image = temp.getSubimage(0, 3 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            case 2 -> image = temp.getSubimage(0, 6 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
            default -> image = temp.getSubimage(0, 7 * 32, ARMOR_WIDTH_DEFAULT, ARMOR_HEIGHT_DEFAULT);
        }
    }


    /// ------------------------------- GETTER AND SETTER ------------------------------- ///
    public int getArmor() {
        return armor;
    }

}
