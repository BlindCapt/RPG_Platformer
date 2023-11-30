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
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.*;
import static utilz.Constants.ObjectConstants.*;

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
    private GameOverOverlay gameOverOverlay;
    private GameWinOverlay gameWinOverlay;

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
    private boolean gameWin = false;

    //
    private boolean inventoryFull = false;
    private int inventoryFullClock = 241;
    private boolean drawInventoryFull;

    // Background
    private BufferedImage backgroundImageZone1, backgroundImageZone2, backgroundImageZone3, spawnBgImage, corridorImage, bossRoomImage;


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
        backgroundImageZone1 = LoadSave.GetSpriteAtlas(LoadSave.BG_LEVEL_1);
        backgroundImageZone2 = LoadSave.GetSpriteAtlas(LoadSave.BG_LEVEL_2);
        backgroundImageZone3 = LoadSave.GetSpriteAtlas(LoadSave.BG_LEVEL_3);
        spawnBgImage = LoadSave.GetSpriteAtlas(LoadSave.SPAWN_BG);
        corridorImage = LoadSave.GetSpriteAtlas(LoadSave.CORRIDOR_BG);
        bossRoomImage = LoadSave.GetSpriteAtlas(LoadSave.BOSS_ROOM_BG);
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
        gameOverOverlay = new GameOverOverlay(this);
        gameWinOverlay = new GameWinOverlay();

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
        if (!inventory && !paused && !isAltar && !gameWin) {
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            objectManager.update();
            objectManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            player.update();
            checkCloseBorder();
            altar.update();

            if (inventoryFullClock < 240) {
                inventoryFullClock++;
                drawInventoryFull = true;
            } else {
                drawInventoryFull = false;
            }

            checkBossRoom();
        }
    }

    private void checkBossRoom() {
        if (player.getHitBox().intersects(levelManager.getCurrentLevel().getBossRoomHitBoxBottom())) {
            downBorder = GAME_HEIGHT - 2 * Game.TILES_SIZE;
            leftBorder = 0;
        }
        if (player.getHitBox().intersects(levelManager.getCurrentLevel().getBossRoomHitBoxCenter())) {
            rightBorder = GAME_WIDTH;
        }
    }

    @Override
    public void draw(Graphics g) {
        drawBackgrounds(g);


        levelManager.draw(g, xLvlOffset, yLvlOffset);
        enemyManager.draw(g, xLvlOffset, yLvlOffset);
        objectManager.draw(g, xLvlOffset, yLvlOffset);
        altar.draw(g, xLvlOffset, yLvlOffset);
        player.render(g, xLvlOffset, yLvlOffset);
        drawUI(g);

        if (drawInventoryFull)
            g.drawString("INVENTORY FULL", (int) (player.getHitBox().x - xLvlOffset), (int) (player.getHitBox().y - yLvlOffset));


        if (inventory || paused || isAltar || player.isDeadBody() || gameWin) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            if (inventory)
                inventoryOverlay.draw(g);
            else if (paused)
                pauseOverlay.draw(g);
            else if (isAltar)
                altarOverlay.draw(g);
            else if (player.isDeadBody())
                gameOverOverlay.draw(g);
            else if (gameWin) {
                gameWinOverlay.draw(g);
            }
        }


        // debug boss room hitbox
//        Rectangle2D.Float hitbox = levelManager.getCurrentLevel().getBossRoomHitBoxBottom();
//        g.setColor(Color.red);
//        g.drawRect((int) (hitbox.x) - xLvlOffset, (int) (hitbox.y) - yLvlOffset, (int) (hitbox.width), (int) (hitbox.height));
//        hitbox = levelManager.getCurrentLevel().getBossRoomHitBoxCenter();
//        g.drawRect((int) (hitbox.x) - xLvlOffset, (int) (hitbox.y) - yLvlOffset, (int) (hitbox.width), (int) (hitbox.height));

    }

    private void drawUI(Graphics g) {
        BufferedImage lifePotion = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS).getSubimage(0, 16, 12, 16);

        g.drawImage(lifePotion, GAME_WIDTH - 100, 20, POTION_WIDTH, POTION_HEIGHT, null);
        g.drawString(String.valueOf(player.getNbLifePotions()), GAME_WIDTH - 80, 40);
        if (leftBorder == 0) {
            g.setColor(Color.RED);
            int bossHealthBar = (int) ((enemyManager.getNecromancer().get(0).getCurrentHealth() / (float) enemyManager.getNecromancer().get(0).getMaxHealth()) * (GAME_WIDTH - 40));

            g.fillRect(20, GAME_HEIGHT - 30, bossHealthBar, 10);
        }
    }

    public void drawBackgrounds(Graphics g) {
        g.fillRect(0, 0, 106 * TILES_SIZE, 65 * TILES_SIZE);

        g.drawImage(backgroundImageZone3, TILES_SIZE * 62 - xLvlOffset, TILES_SIZE - yLvlOffset, TILES_SIZE * 44, TILES_SIZE * 64, null);
        g.drawImage(backgroundImageZone1, -xLvlOffset, 36 * Game.TILES_SIZE - yLvlOffset, TILES_SIZE * 64, TILES_SIZE * 28, null);
        g.fillRect((48 * Game.TILES_SIZE - xLvlOffset), (int) (24.5 * Game.TILES_SIZE - yLvlOffset), (int) (GAME_WIDTH + 1.5 * Game.TILES_SIZE), (int) (GAME_HEIGHT + 0.5 * Game.TILES_SIZE));
        g.drawImage(bossRoomImage, (48 * Game.TILES_SIZE - xLvlOffset), (int) (24.5 * Game.TILES_SIZE - yLvlOffset), (int) (GAME_WIDTH + 1.5 * Game.TILES_SIZE), (int) (GAME_HEIGHT + 0.5 * Game.TILES_SIZE), null);
        g.drawImage(backgroundImageZone2, -xLvlOffset, TILES_SIZE - yLvlOffset, TILES_SIZE * 62, TILES_SIZE * 25, null);


        g.drawImage(spawnBgImage, (int) (0.3 * Game.TILES_SIZE - xLvlOffset), 27 * Game.TILES_SIZE - yLvlOffset, GAME_WIDTH - 2 * Game.TILES_SIZE, GAME_HEIGHT - 4 * Game.TILES_SIZE, null);
        g.drawImage(corridorImage, (24 * Game.TILES_SIZE - xLvlOffset), 24 * Game.TILES_SIZE - yLvlOffset, GAME_WIDTH, GAME_HEIGHT - 1 * Game.TILES_SIZE, null);

    }

    public void resetAll() {
        getObjectManager().resetObjects();
        getEnemyManager().resetAllEnemies();
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

            case KeyEvent.VK_A -> player.usePotion(LIFE_POTION);
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

    public GameOverOverlay getGameOverOverlay() {
        return gameOverOverlay;
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

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setDownBorder(int downBorder) {
        this.downBorder = downBorder;
    }

    public void setRightBorder(int rightBorder) {
        this.rightBorder = rightBorder;
    }

    public void setLeftBorder(int leftBorder) {
        this.leftBorder = leftBorder;
    }

    public int getDownBorder() {
        return downBorder;
    }

    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
    }
}
