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

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
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
                    list.add(new FlyingEye(i * Game.TILES_SIZE - (FLYING_EYE_WIDTH - Game.TILES_SIZE) / 2, j * Game.TILES_SIZE - Game.TILES_SIZE / 2));
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
                    list.add(new Goblin(i * Game.TILES_SIZE - (GOBLIN_WIDTH - Game.TILES_SIZE) / 2, j * Game.TILES_SIZE - Game.TILES_SIZE / 2));
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
                    list.add(new Mushroom(i * Game.TILES_SIZE - (MUSHROOM - Game.TILES_SIZE) / 2, j * Game.TILES_SIZE - Game.TILES_SIZE / 2));
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
                    list.add(new Skeleton(i * Game.TILES_SIZE - (SKELETON_WIDTH - Game.TILES_SIZE) / 2, j * Game.TILES_SIZE - Game.TILES_SIZE / 2));
                }
            }
        }
        return list;
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
