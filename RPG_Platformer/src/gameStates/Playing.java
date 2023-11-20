package gameStates;

import entities.EnemyManager;
import entities.Player;
import level.LevelManager;
import main.Game;
import objects.Altar;
import objects.ObjectManager;
import utilz.LoadSave;
import ui.overlay.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilz.Constants.ObjectConstants.LIFE_POTION;
import static utilz.Constants.ObjectConstants.STM_POTION;

public class Playing extends State implements StateMethods {
    /// ------------------------------- ATTRIBUTES ------------------------------- ///

    // Class to use in playing state
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private InventoryOverlay inventoryOverlay;
    private PauseOverlay pauseOverlay;
    private AltarOverlay altarOverlay;
    private Altar altar;

    // Game border and level Offset var
    private int xLvlOffset;
    private int yLvlOffset;
    private int leftBorder = (int) (0.50 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.50 * Game.GAME_WIDTH);
    private int topBorder = (int) (0.50 * GAME_HEIGHT);
    private int downBorder = (int) (0.70 * GAME_HEIGHT);

    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int lvlTilesHeight = LoadSave.GetLevelData().length;

    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxTilesOffsetY = lvlTilesHeight - Game.TILES_IN_HEIGHT;

    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
    private int maxLvlOffsetY = maxTilesOffsetY * Game.TILES_SIZE;

    // Overlay boolean
    private boolean inventory = false;
    private boolean paused = false;
    private boolean isAltar = false;

    //
    private boolean inventoryFull = false;
    private int inventoryFullClock = 241;
    private boolean drawInventoryFull;

    // Background
    private BufferedImage backgroundImage, spawnBgImage;


    /// ------------------------------- CONSTRUCTOR ------------------------------- ///

    public Playing(Game game) {
        super(game);
        initClass();

        loadBackground();
        loadObject();
    }


    /// ------------------------------- METHOD ------------------------------- ///

    private void loadObject() {
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.BG_LEVEL);
        spawnBgImage = LoadSave.GetSpriteAtlas(LoadSave.SPAWN_BG);
    }

    private void initClass() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        player = new Player(8 * Game.TILES_SIZE, 34 * Game.TILES_SIZE, 18, 39, this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        inventoryOverlay = new InventoryOverlay(this);
        pauseOverlay = new PauseOverlay(this);
        altarOverlay = new AltarOverlay(this);
        altar = new Altar(10 * Game.TILES_SIZE, 33 * Game.TILES_SIZE);

    }

    private void checkCloseBorder() {
        int playerX = (int) player.getHitBox().x;
        int playerY = (int) player.getHitBox().y;

        int diffX = playerX - xLvlOffset;
        int diffY = playerY - yLvlOffset;

        // TODO: fix method to avoid code duplication

        // X Level Offset
//        xLvlOffset = handleLvlOffset(diffX, leftBorder, rightBorder, xLvlOffset, maxLvlOffsetX);
        if (diffX > rightBorder) {
            xLvlOffset += diffX - rightBorder;
        } else if (diffX < leftBorder) {
            xLvlOffset += diffX - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }

        // Y Level Offset
//        yLvlOffset = handleLvlOffset(diffY, topBorder, downBorder, yLvlOffset, maxLvlOffsetY);
        if (diffY > downBorder) {
            yLvlOffset += diffY - downBorder;
        } else if (diffY < topBorder) {
            yLvlOffset += diffY - topBorder;
        }

        if (yLvlOffset > maxLvlOffsetY) {
            yLvlOffset = maxLvlOffsetY;
        } else if (yLvlOffset < 0) {
            yLvlOffset = 0;
        }

    }
//    private int handleLvlOffset(int diff, int startBorder, int endBorder, int offset, int maxOffset) {
//        if (diff > endBorder) {
//            return offset + diff - endBorder;
//        } else if (diff < startBorder) {
//            return offset + diff - startBorder;
//        }
//
//        if (offset > maxOffset) {
//            return maxOffset;
//        } else if (offset < 0) {
//            return 0;
//        }
//        return offset;
//    }


    public void checkSpeakToAtlas() {
        if (player.getHitBox().intersects(altar.getHitBox())) {
            isAltar = true;
        }
    }

    public void checkPickUpItem(Rectangle2D.Float hitBox) {
        objectManager.checkPickUpItem(hitBox);
    }

    public void checkPickUpPotion(Rectangle2D.Float hitBox) {
        objectManager.checkPickUpPotion(hitBox);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }


    @Override
    public void update() {
        if (!inventory && !paused && !isAltar) {
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            objectManager.update();
            player.update();
            checkCloseBorder();
            altar.update();

            if (inventoryFullClock < 240) {
                inventoryFullClock++;
                drawInventoryFull = true;
            } else {
                drawInventoryFull = false;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0 - xLvlOffset, 0 - yLvlOffset, (int) (GAME_WIDTH * 2.4), (int) (GAME_HEIGHT * 1.8), null);
        g.drawImage(spawnBgImage, (int) (0.3 * Game.TILES_SIZE - xLvlOffset), 27 * Game.TILES_SIZE - yLvlOffset, GAME_WIDTH - 2 * Game.TILES_SIZE, GAME_HEIGHT - 4 * Game.TILES_SIZE, null);

        levelManager.draw(g, xLvlOffset, yLvlOffset);
        enemyManager.draw(g, xLvlOffset, yLvlOffset);
        objectManager.draw(g, xLvlOffset, yLvlOffset);
        altar.draw(g, xLvlOffset, yLvlOffset);
        player.render(g, xLvlOffset, yLvlOffset);

        if (drawInventoryFull)
            g.drawString("INVENTORY FULL", (int) (player.getHitBox().x - xLvlOffset), (int) (player.getHitBox().y - yLvlOffset));


        if (inventory || paused || isAltar) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            if (inventory)
                inventoryOverlay.draw(g);
            else if (paused)
                pauseOverlay.draw(g);
            else if (isAltar)
                altarOverlay.draw(g);
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (!gameOver)
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q -> player.setLeft(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_F -> player.setAction(true);
            case KeyEvent.VK_I -> {
                if (!paused && !isAltar)
                    inventory = !inventory;
            }
            case KeyEvent.VK_ESCAPE -> {
                if (!inventory && !isAltar)
                    paused = !paused;
                if (isAltar)
                    isAltar = false;
            }
            case KeyEvent.VK_SPACE -> player.setJump(true);

            case KeyEvent.VK_1 -> player.usePotion(LIFE_POTION);
            case KeyEvent.VK_2 -> player.usePotion(STM_POTION);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q -> player.setLeft(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_F -> player.setAction(false);
            case KeyEvent.VK_SPACE -> player.setJump(false);
        }
    }

    public void resetAll(){
        getObjectManager().resetObjects();
        getEnemyManager().resetAllEnemies();
    }


    /// ------------------------------- GETTER AND SETTER ------------------------------- ///

    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public boolean isInventory() {
        return inventory;
    }

    public InventoryOverlay getInventoryOverlay() {
        return inventoryOverlay;
    }

    public boolean isInventoryFull() {
        return inventoryFull;
    }

    public void setInventoryFull(boolean inventoryFull) {
        this.inventoryFull = inventoryFull;
    }

    public void setInventoryFullClock(int inventoryFullClock) {
        this.inventoryFullClock = inventoryFullClock;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isAltar() {
        return isAltar;
    }

    public AltarOverlay getAltarOverlay() {
       return altarOverlay;
    }

    public void setAltar(boolean altar) {
        isAltar = altar;
    }

    public EnemyManager getEnemyManager(){
        return enemyManager;
    }
}
