package utilz;

import entities.*;
import main.Game;
import objects.Chest;
import objects.Potion;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utilz.Constants.Direction.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.ObjectConstants.*;

public class HelpMethod {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        if (!IsSolid(x, y + height / 2, lvlData)) {
                            if (!IsSolid(x + width, y + height / 2, lvlData)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        int maxHeight = lvlData.length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }

        if (y < 0 || y >= maxHeight) {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        if (value != 11) {
            return true;
        }
        return false;
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        // Check the pixel below bottom left and bottom right
        if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) {
            if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else {
            // Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // FALLING - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (hitBox.height / 1.46);
            return tileYPos + yOffset - 1 * Game.SCALE;
        } else {
            // JUMPING
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData, int walkDir) {
        if (walkDir == LEFT) {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 5, lvlData);
        } else {
            return IsSolid(hitbox.x + xSpeed + hitbox.width, hitbox.y + hitbox.height + 5, lvlData);
        }
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitBox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitBox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
    }

    public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++)
            if (IsTileSolid(xStart + i, y, lvlData) || !IsTileSolid(xStart + i, y + 2, lvlData))
                return false;
        return true;
    }

    public static List<FlyingEye> GetFlyingEyes() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_MAP);
        ArrayList<FlyingEye> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == FLYING_EYE) {
                    FlyingEye flyingEye = new FlyingEye(i * Game.TILES_SIZE, j * Game.TILES_SIZE - Game.TILES_SIZE / 2);
                    list.add(flyingEye);
                    setLevelEnemy(i, j, flyingEye);
                }
            }
        }
        return list;
    }

    public static List<Goblin> GetGoblins() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_MAP);
        ArrayList<Goblin> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == GOBLIN) {
                    Goblin goblin = new Goblin(i * Game.TILES_SIZE, j * Game.TILES_SIZE - Game.TILES_SIZE / 2);
                    list.add(goblin);
                    setLevelEnemy(i, j, goblin);
                }
            }
        }
        return list;
    }

    public static List<Mushroom> GetMushrooms() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_MAP);
        ArrayList<Mushroom> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == MUSHROOM) {
                    Mushroom mushroom = new Mushroom(i * Game.TILES_SIZE, j * Game.TILES_SIZE - Game.TILES_SIZE / 2);
                    list.add(mushroom);
                    setLevelEnemy(i, j, mushroom);
                }
            }
        }
        return list;
    }

    public static List<Skeleton> GetSkeletons() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_MAP);
        ArrayList<Skeleton> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == SKELETON) {
                    Skeleton skeleton = new Skeleton(i * Game.TILES_SIZE, j * Game.TILES_SIZE - Game.TILES_SIZE / 2);
                    list.add(skeleton);
                    setLevelEnemy(i, j, skeleton);
                }
            }
        }
        return list;
    }

    public static List<Necromancer> GetNecromancers() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_MAP);
        ArrayList<Necromancer> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == NECROMANCER) {
                    Necromancer necromancer = new Necromancer(i * Game.TILES_SIZE, (j - 1) * Game.TILES_SIZE - 25);
                    list.add(necromancer);
                    setLevelEnemy(i, j, necromancer);
                }
            }
        }
        return list;
    }

    public static List<NightBorne> GetNightBornes() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_MAP);
        ArrayList<NightBorne> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == NIGHTBORNE) {
                    NightBorne nightBorne = new NightBorne(i * Game.TILES_SIZE, (j - 1) * Game.TILES_SIZE - 25);
                    list.add(nightBorne);
                    setLevelEnemy(i, j, nightBorne);
                }
            }
        }
        return list;
    }

    private static void setLevelEnemy(int i, int j, Enemy enemy) {
        Random random = new Random();
        int level;
        if (i < 106) {
            if (j < 65) {
                level = random.nextInt(5) + 1;
                enemy.setEnemyLevel(level);
            } else if (j > 65) {
                level = random.nextInt(5) + 6;
                enemy.setEnemyLevel(level);
            }
        } else {
            level = random.nextInt(5) + 11;
            enemy.setEnemyLevel(level);
        }
    }

    public static ArrayList<Chest> GetChests(BufferedImage img) {
        ArrayList<Chest> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == CHEST) {
                    list.add(new Chest(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static ArrayList<Potion> GetPotions(BufferedImage img) {
        ArrayList<Potion> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == LIFE_POTION) {
                    list.add(new Potion(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }


}
