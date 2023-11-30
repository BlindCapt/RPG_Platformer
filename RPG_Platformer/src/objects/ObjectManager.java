package objects;

import entities.Player;
import gameStates.Playing;
import level.Level;
import main.Game;
import objects.equipment.*;
import org.w3c.dom.css.Rect;
import utilz.LoadSave;

import static utilz.Constants.ObjectConstants.*;
import static utilz.Constants.Projectiles.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class ObjectManager {
    /// ------------------------------- ATTRIBUTE ------------------------------- ///
    private Playing playing;

    // Spites
    private BufferedImage[][] chestImages;
    private BufferedImage[][] potionImages;
    private BufferedImage necromancerVfx1;

    // List of items
    private ArrayList<Chest> chests = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<Equipment> equipments = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
    }

    /// ------------------------------- METHOD ------------------------------- ///
//    public void checkObjectTouched(Rectangle2D.Float hitbox) {
//        for (Potion p : potions) {
//            if (p.isActive()) {
//                if (hitbox.intersects(p.getHitbox())) {
//                    p.setActive(false);
//                    applyEffectToPlayer(p);
//                }
//            }
//        }
//    }

//    public void applyEffectToPlayer(Potion p) {
//        if (p.getObjType() == RED_POTION) {
//            playing.getPlayer().changeHealth(RED_POTION_VALUE);
//        } else {
//            playing.getPlayer().changePower(BLUE_POTION_VALUE);
//        }
//    }

    public void checkPickUpItem(Rectangle2D.Float hitBox) {
        for (Equipment equipment : equipments) {
            if (equipment.isActive()) {
                if (hitBox.intersects(equipment.hitbox)) {
                    if (!pickUpItem(equipment)) {
                        playing.setInventoryFull(true);
                        playing.setInventoryFullClock(0);
                    } else {
                        equipment.setActive(false);
                        playing.getPlayer().setAction(false);
                        return;
                    }

                }
            }
        }
    }

    public void checkPickUpPotion(Rectangle2D.Float hitBox) {
        for (Potion p : potions) {
            if (p.isActive()) {
                if (hitBox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    if (p.getObjType() == LIFE_POTION) {
                        playing.getPlayer().changeNbLifePotions(1);
                    } else if (p.getObjType() == STM_POTION) {
                        playing.getPlayer().changeNbStaminaPotions(1);
                    }
                }
            }
        }
    }


    private void inventoryFull() {

    }

    private boolean pickUpItem(Equipment equipment) {
        return playing.getInventoryOverlay().pickUpItem(equipment);
    }

    public void checkObjectHit(Rectangle2D.Float attackbox) {
        for (Chest ch : chests) {
            if (ch.isActive() && !ch.doAnimation) {
                if (ch.getHitbox().intersects(attackbox)) {
                    ch.objState = 1;
                    ch.setDoAnimation(true);
                    switch (ch.getChestType()) {
                        case 0 -> chestArmorDrop(ch);
                        case 1 ->
                                equipments.add(new Sword(ch.x, (int) (ch.getHitbox().y + ch.getHitbox().height - ARMOR_HEIGHT * Game.SCALE), new Random().nextInt(3), ch.getLevel()));
                        case 2 ->
                                potions.add(new Potion((int) (ch.getHitbox().x + ch.getHitbox().width / 2), (int) (ch.getHitbox().y - ch.getHitbox().height / 2), 1));
                    }

                    return;
                }
            }
        }
    }

    private void chestArmorDrop(Chest ch) {
        Random r = new Random();
        int armorType = r.nextInt(4);
        switch (armorType) {
            case 0 ->
                    equipments.add(new Helmet(ch.x, (int) (ch.getHitbox().y + ch.getHitbox().height - ARMOR_HEIGHT * Game.SCALE), new Random().nextInt(4), ch.getLevel()));
            case 1 ->
                    equipments.add(new Chestplate(ch.x, (int) (ch.getHitbox().y + ch.getHitbox().height - ARMOR_HEIGHT * Game.SCALE), new Random().nextInt(4), ch.getLevel()));
            case 2 ->
                    equipments.add(new Pants(ch.x, (int) (ch.getHitbox().y + ch.getHitbox().height - ARMOR_HEIGHT * Game.SCALE), new Random().nextInt(4), ch.getLevel()));
            case 3 ->
                    equipments.add(new Boots(ch.x, (int) (ch.getHitbox().y + ch.getHitbox().height - ARMOR_HEIGHT * Game.SCALE), new Random().nextInt(4), ch.getLevel()));
        }
    }

    public void dropItem(Equipment equipment) {
        equipments.add(equipment);
    }

    public void loadObjects(Level level) {
        potions = new ArrayList<>(level.getPotions());
        chests = new ArrayList<>(level.getChests());
        projectiles.clear();
    }


    private void loadImages() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImages = new BufferedImage[2][7];

        for (int j = 0; j < potionImages.length; j++) {
            for (int i = 0; i < potionImages[j].length; i++) {
                potionImages[j][i] = potionSprite.getSubimage(i * POTION_WIDTH_DEFAULT, j * POTION_HEIGHT_DEFAULT, POTION_WIDTH_DEFAULT, POTION_HEIGHT_DEFAULT);
            }
        }

        BufferedImage chestSprite = LoadSave.GetSpriteAtlas(LoadSave.CHEST);
        chestImages = new BufferedImage[8][5];

        for (int j = 0; j < chestImages.length; j++) {
            for (int i = 0; i < chestImages[j].length; i++) {
                chestImages[j][i] = chestSprite.getSubimage(i * CHEST_DEFAULT_WIDTH, j * CHEST_DEFAULT_HEIGHT, CHEST_DEFAULT_WIDTH, CHEST_DEFAULT_HEIGHT);
            }
        }

        necromancerVfx1 = LoadSave.GetSpriteAtlas(LoadSave.BOSS_EFFECTS3);


    }

    public void update() {
        for (Potion p : potions) {
            if (p.isActive()) {
                p.update();
            }
        }
        for (Chest ch : chests) {
            if (ch.isActive()) {
                ch.update();
            }
        }

    }

    public void update(int[][] lvlData, Player player) {
        updateProjectiles(lvlData, player);

    }

    public void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.update(lvlData);
//                if (p.getHitbox().intersects(player.getHitBox())) {
//                    player.changeHealth(-10);
//                    p.setActive(false);
//                }
            }
        }
    }


    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        drawPotions(g, xLvlOffset, yLvlOffset);
        drawChests(g, xLvlOffset, yLvlOffset);
        drawEquipment(g, xLvlOffset, yLvlOffset);
        drawProjectiles(g, xLvlOffset, yLvlOffset);
    }

    public void drawProjectiles(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                g.drawImage(necromancerVfx1,
                        (int) (p.getHitbox().x - xLvlOffset - PROJECTILE_XDRAW_OFFSET),
                        (int) (p.getHitbox().y - yLvlOffset - PROJECTILE_YDRAW_OFFSET),
                        (int) (VFX1_WIDTH * Game.SCALE),
                        (int) (VFX1_HEIGHT * Game.SCALE),
                        null);

//                g.setColor(Color.RED);
//                g.drawRect((int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y - yLvlOffset), (int) p.getHitbox().width, (int) p.getHitbox().height);
            }
        }
    }

    private void drawEquipment(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Equipment equipment : equipments) {
            if (equipment.isActive())
                equipment.draw(g, xLvlOffset, yLvlOffset);
        }
    }

    private void drawChests(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Chest c : chests) {
            if (c.isActive()) {
                g.drawImage(chestImages[c.getChestType() * 2 + c.objState][c.getAniIndex()],
                        (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset),
                        (int) (c.getHitbox().y - c.getyDrawOffset() - yLvlOffset),
                        CHEST_WIDTH,
                        CHEST_HEIGHT,
                        null);
//                c.drawHitBox(g, xLvlOffset, yLvlOffset);
            }
        }
    }

    private void drawPotions(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Potion p : potions) {
            if (p.isActive()) {
                int type = 0;
                if (p.getObjType() == LIFE_POTION) {
                    type = 1;
                }

                g.drawImage(potionImages[type][p.getAniIndex()],
                        (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                        (int) (p.getHitbox().y - p.getyDrawOffset() - yLvlOffset),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null);
                p.drawHitBox(g, xLvlOffset, yLvlOffset);
            }
        }
    }


    public void resetObjects() {
        equipments = new ArrayList<>();
        loadObjects(playing.getLevelManager().getCurrentLevel());

        for (Chest c : chests) {
            c.reset();
            c.setChestType(new Random().nextInt(3));
        }
    }


    /// ------------------------------- GETTER AND SETTER ------------------------------- ///

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
