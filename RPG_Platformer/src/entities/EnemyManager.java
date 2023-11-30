package entities;

import gameStates.Playing;
import utilz.HelpMethod;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    /// ------------------------------- ATTRIBUTES ------------------------------- ///
    private Playing playing;
    private BufferedImage[][] flyingEyeArr;
    private BufferedImage[][] goblinArr;
    private BufferedImage[][] mushroomArr;
    private BufferedImage[][] skeletonArr;

    private BufferedImage[][] necromancerArr;
    private BufferedImage[][] nightBorneArr;
    private List<FlyingEye> flyingEyes = new ArrayList<>();
    private List<Goblin> goblins = new ArrayList<>();
    private List<Mushroom> mushrooms = new ArrayList<>();
    private List<Skeleton> skeletons = new ArrayList<>();
    private List<Necromancer> necromancer = new ArrayList<>();
    private List<NightBorne> nightBorne = new ArrayList<>();

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
        setPlayingEnemy();
    }

    /// ------------------------------- GETTER AND SETTER ------------------------------- ///

    /// ------------------------------- METHOD ------------------------------- ///

    public void setPlayingEnemy() {
        for (FlyingEye fe : flyingEyes) {
            fe.setPlaying(playing);
        }
        for (Goblin gob : goblins) {
            gob.setPlaying(playing);
        }
        for (Mushroom mush : mushrooms) {
            mush.setPlaying(playing);
        }
        for (Skeleton sk : skeletons) {
            sk.setPlaying(playing);
        }
        for (Necromancer nec : necromancer) {
            nec.setPlaying(playing);
        }
        for (NightBorne nb : nightBorne) {
            nb.setPlaying(playing);
        }
    }

    private void addEnemies() {
        flyingEyes = HelpMethod.GetFlyingEyes();
        goblins = HelpMethod.GetGoblins();
        mushrooms = HelpMethod.GetMushrooms();
        skeletons = HelpMethod.GetSkeletons();
        necromancer = HelpMethod.GetNecromancers();
        nightBorne = HelpMethod.GetNightBornes();
    }

    public void update(int[][] lvlData, Player player) {
        for (FlyingEye fe : flyingEyes) {
            if (fe.isActive())
                if (fe.isActive())
                    fe.update(lvlData, player);
        }
        for (Goblin gob : goblins) {
            if (gob.isActive())
                if (gob.isActive())
                    gob.update(lvlData, player);
        }
        for (Mushroom mush : mushrooms) {
            if (mush.isActive())
                if (mush.isActive())
                    mush.update(lvlData, player);
        }
        for (Skeleton sk : skeletons) {
            if (sk.isActive())
                if (sk.isActive())
                    sk.update(lvlData, player);
        }
        for (Necromancer nec : necromancer) {
            if (nec.isActive())
                if (nec.isActive())
                    nec.update(lvlData, player);
        }
        for (NightBorne nb : nightBorne) {
            if (nb.isActive())
                if (nb.isActive())
                    nb.update(lvlData, player);
        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        drawEnemies(g, xLvlOffset, yLvlOffset);
    }

    private void drawEnemies(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (FlyingEye fe : flyingEyes) {
            if (fe.isActive()) {
                g.drawImage(flyingEyeArr[fe.getState()][fe.getAniIndex()],
                        (int) (fe.getHitBox().x - xLvlOffset - FLYING_EYE_DRAWOFFSET_X) + fe.flipX(),
                        (int) (fe.getHitBox().y - yLvlOffset - FLYING_EYE_DRAWOFFSET_Y)
                        , FLYING_EYE_WIDTH * fe.flipY(), FLYING_EYE_HEIGHT, null);
//                fe.drawHitBox(g, xLvlOffset, yLvlOffset);
//                fe.drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
        for (Goblin gob : goblins) {
            if (gob.isActive()) {
                g.drawImage(goblinArr[gob.getState()][gob.getAniIndex()],
                        (int) (gob.getHitBox().x - xLvlOffset - GOBLIN_DRAWOFFSET_X) + gob.flipX(),
                        (int) (gob.getHitBox().y - yLvlOffset - GOBLIN_DRAWOFFSET_Y)
                        , GOBLIN_WIDTH * gob.flipY(), GOBLIN_HEIGHT, null);
//                gob.drawHitBox(g, xLvlOffset, yLvlOffset);
//                gob.drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
        for (Mushroom mush : mushrooms) {
            if (mush.isActive()) {
                g.drawImage(mushroomArr[mush.getState()][mush.getAniIndex()],
                        (int) (mush.getHitBox().x - xLvlOffset - MUSHROOM_DRAWOFFSET_X) + mush.flipX(),
                        (int) (mush.getHitBox().y - yLvlOffset - MUSHROOM_DRAWOFFSET_Y)
                        , MUSHROOM_WIDTH * mush.flipY(), MUSHROOM_HEIGHT, null);
//                mush.drawHitBox(g, xLvlOffset, yLvlOffset);
//                mush.drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
        for (Skeleton sk : skeletons) {
            if (sk.isActive()) {
                g.drawImage(skeletonArr[sk.getState()][sk.getAniIndex()],
                        (int) (sk.getHitBox().x - xLvlOffset - SKELETON_DRAWOFFSET_X) + sk.flipX(),
                        (int) (sk.getHitBox().y - yLvlOffset - SKELETON_DRAWOFFSET_Y)
                        , SKELETON_WIDTH * sk.flipY(), SKELETON_HEIGHT, null);
//                sk.drawHitBox(g, xLvlOffset, yLvlOffset);
//                sk.drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
        for (Necromancer nec : necromancer) {
            if (nec.isActive()) {
                g.drawImage(necromancerArr[nec.getState()][nec.getAniIndex()],
                        (int) (nec.getHitBox().x - xLvlOffset - NECROMANCER_DRAWOFFSET_X) + nec.flipX(),
                        (int) (nec.getHitBox().y - yLvlOffset - NECROMANCER_DRAWOFFSET_Y)
                        , NECROMANCER_WIDTH * nec.flipY(), NECROMANCER_HEIGHT, null);
                nec.drawHitBox(g, xLvlOffset, yLvlOffset);
                nec.drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
        for (NightBorne nb : nightBorne) {
            if (nb.isActive()) {
                g.drawImage(nightBorneArr[nb.getState()][nb.getAniIndex()],
                        (int) (nb.getHitBox().x - xLvlOffset - NIGHTBORNE_DRAWOFFSET_X) + nb.flipX(),
                        (int) (nb.getHitBox().y - yLvlOffset - NIGHTBORNE_DRAWOFFSET_Y)
                        , NIGHTBORNE_WIDTH * nb.flipY(), NIGHTBORNE_HEIGHT, null);
            }
            nb.drawHitBox(g, xLvlOffset, yLvlOffset);
        }
    }

    private void loadEnemyImgs() {
        flyingEyeArr = new BufferedImage[7][8];
        goblinArr = new BufferedImage[7][12];
        mushroomArr = new BufferedImage[7][11];
        skeletonArr = new BufferedImage[7][8];
        necromancerArr = new BufferedImage[7][17];
        nightBorneArr = new BufferedImage[5][23];

        fillEnemiesList(FLYING_EYE);
        fillEnemiesList(GOBLIN);
        fillEnemiesList(MUSHROOM);
        fillEnemiesList(SKELETON);
        fillEnemiesList(NECROMANCER);
        fillEnemiesList(NIGHTBORNE);
    }

    private void fillEnemiesList(int enemyType) {
        BufferedImage[][] returnArr;
        switch (enemyType) {
            case FLYING_EYE, SKELETON -> returnArr = new BufferedImage[7][8];
            case GOBLIN -> returnArr = new BufferedImage[7][12];
            case MUSHROOM -> returnArr = new BufferedImage[7][11];
            case NECROMANCER -> returnArr = new BufferedImage[7][17];
            case NIGHTBORNE -> returnArr = new BufferedImage[5][23];
            default -> returnArr = new BufferedImage[0][0];
        }

        BufferedImage temp = null;

        if (enemyType == NECROMANCER) {
            temp = LoadSave.GetSpriteAtlas("/ennemies/Necromancer/Necromancer.png");

            for (int j = 0; j < returnArr.length; j++) {
                for (int i = 0; i < returnArr[j].length; i++) {
                    if (i < GetSpriteAmount(NECROMANCER, j))
                        returnArr[j][i] = temp.getSubimage(i * NECROMANCER_WIDTH_DEFAULT, NECROMANCER_HEIGHT_DEFAULT * j, NECROMANCER_WIDTH_DEFAULT, NECROMANCER_HEIGHT_DEFAULT);
                }
            }
            System.arraycopy(returnArr[0], 0, necromancerArr[0], 0, returnArr[0].length);
            System.arraycopy(returnArr[1], 0, necromancerArr[1], 0, returnArr[1].length);
            System.arraycopy(returnArr[2], 0, necromancerArr[2], 0, returnArr[2].length);
            System.arraycopy(returnArr[3], 0, necromancerArr[5], 0, returnArr[3].length);
            System.arraycopy(returnArr[4], 0, necromancerArr[6], 0, returnArr[4].length);
            System.arraycopy(returnArr[5], 0, necromancerArr[3], 0, returnArr[5].length);
            System.arraycopy(returnArr[6], 0, necromancerArr[4], 0, returnArr[6].length);



        if (enemyType == NECROMANCER)
            temp = LoadSave.GetSpriteAtlas("/ennemies/Necromancer/Necromancer.png");
        } else if (enemyType == NIGHTBORNE) {

            temp = LoadSave.GetSpriteAtlas("/ennemies/NightBorne/NightBorne.png");

            for (int j = 0; j < returnArr.length; j++) {
                for (int i = 0; i < returnArr[j].length; i++) {
                    if (i < GetSpriteAmount(NIGHTBORNE, j))
                        nightBorneArr[j][i] = temp.getSubimage(i * NIGHTBORNE_WIDTH_DEFAULT, NIGHTBORNE_HEIGHT_DEFAULT * j, NIGHTBORNE_WIDTH_DEFAULT, NIGHTBORNE_HEIGHT_DEFAULT);
                }
            }

        } else {
            String[] listPath = new String[7];
            listPath[ATTACK] = "/Attack.png";
            listPath[ATTACK_2] = "/Attack2.png";
            listPath[ATTACK_3] = "/Attack3.png";
            listPath[DEATH] = "/Death.png";
            listPath[IDLE] = "/Idle.png";
            listPath[RUN] = "/Run.png";
            listPath[HURT] = "/Take_Hit.png";

            String[] listEnemy = new String[4];
            listEnemy[FLYING_EYE] = "Flying_eye";
            listEnemy[GOBLIN] = "Goblin";
            listEnemy[MUSHROOM] = "Mushroom";
            listEnemy[SKELETON] = "Skeleton";

            for (int j = 0; j < returnArr.length; j++) {
                temp = LoadSave.GetSpriteAtlas("/ennemies/" + listEnemy[enemyType] + listPath[j]);
                for (int i = 0; i < returnArr[j].length; i++)
                    switch (enemyType) {
                        case FLYING_EYE -> {
                            if (i < GetSpriteAmount(FLYING_EYE, j))
                                flyingEyeArr[j][i] = temp.getSubimage(i * ENEMIES_WIDTH_DEFAULT, 0, ENEMIES_WIDTH_DEFAULT, ENEMIES_HEIGHT_DEFAULT);
                        }
                        case GOBLIN -> {
                            if (i < GetSpriteAmount(GOBLIN, j))
                                goblinArr[j][i] = temp.getSubimage(i * ENEMIES_WIDTH_DEFAULT, 0, ENEMIES_WIDTH_DEFAULT, ENEMIES_HEIGHT_DEFAULT);
                        }
                        case MUSHROOM -> {
                            if (i < GetSpriteAmount(MUSHROOM, j))
                                mushroomArr[j][i] = temp.getSubimage(i * ENEMIES_WIDTH_DEFAULT, 0, ENEMIES_WIDTH_DEFAULT, ENEMIES_HEIGHT_DEFAULT);
                        }
                        case SKELETON -> {
                            if (i < GetSpriteAmount(SKELETON, j))
                                skeletonArr[j][i] = temp.getSubimage(i * ENEMIES_WIDTH_DEFAULT, 0, ENEMIES_WIDTH_DEFAULT, ENEMIES_HEIGHT_DEFAULT);
                        }
                    default -> {
                    }
                }
            }
        }

    }


    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (FlyingEye fe : flyingEyes) {
            if (fe.isActive() && fe.getCurrentHealth() > 0)
                if (attackBox.intersects(fe.getHitBox())) {
                    fe.hurt(playing.getPlayer().getAttack());
                    return;
                }
        }
        for (Goblin gob : goblins) {
            if (gob.isActive() && gob.getCurrentHealth() > 0) {
                if ((attackBox.intersects(gob.getHitBox()))) {
                    gob.hurt(playing.getPlayer().getAttack());
                    return;
                }
            }
        }
        for (Mushroom mush : mushrooms) {
            if (mush.isActive() && mush.getCurrentHealth() > 0)
                if (attackBox.intersects(mush.getHitBox())) {
                    mush.hurt(playing.getPlayer().getAttack());
                    return;
                }
        }
        for (Skeleton sk : skeletons) {
            if (sk.isActive() && sk.getCurrentHealth() > 0)
                if (attackBox.intersects(sk.getHitBox())) {
                    sk.hurt(playing.getPlayer().getAttack());
                    return;
                }
        }
        for (Necromancer nec : necromancer) {
            if (nec.isActive() && nec.getCurrentHealth() > 0)
                if (attackBox.intersects(nec.getHitBox())) {
                    nec.hurt(playing.getPlayer().getAttack());
                    return;
                }
        }
        for (NightBorne nb : nightBorne) {
            if (nb.isActive() && nb.getCurrentHealth() > 0)
                if (attackBox.intersects(nb.getHitBox())) {
                    nb.hurt(playing.getPlayer().getAttack());
                    return;
                }
        }
    }

    public void changeEnemiesLevel() {
        for (FlyingEye fe : flyingEyes) {
            fe.changeEnemyLevel();
        }

        for (Goblin gob : goblins) {
            gob.changeEnemyLevel();
        }

        for (Mushroom mush : mushrooms) {
            mush.changeEnemyLevel();
        }

        for (Skeleton sk : skeletons) {
            sk.changeEnemyLevel();
        }
    }

    public void changeEnemiesDropRate() {
        for (FlyingEye fe : flyingEyes) {
            fe.changeEnemyDropRate();
        }

        for (Goblin gob : goblins) {
            gob.changeEnemyDropRate();
        }

        for (Mushroom mush : mushrooms) {
            mush.changeEnemyDropRate();
        }

        for (Skeleton sk : skeletons) {
            sk.changeEnemyDropRate();
        }
    }

    public void changeEnemiesGoldRate() {
        for (FlyingEye fe : flyingEyes) {
            fe.changeEnemyGoldRate();
        }

        for (Goblin gob : goblins) {
            gob.changeEnemyGoldRate();
        }

        for (Mushroom mush : mushrooms) {
            mush.changeEnemyGoldRate();
        }

        for (Skeleton sk : skeletons) {
            sk.changeEnemyGoldRate();
        }
    }

    public void changeEnemiesXPRate() {
        for (FlyingEye fe : flyingEyes) {
            fe.changeEnemyXPRate();
        }

        for (Goblin gob : goblins) {
            gob.changeEnemyXPRate();
        }

        for (Mushroom mush : mushrooms) {
            mush.changeEnemyXPRate();
        }

        for (Skeleton sk : skeletons) {
            sk.changeEnemyXPRate();
        }
    }


    public void resetAllEnemies() {

        for (FlyingEye fe : flyingEyes) {
            fe.resetEnemy();
        }

        for (Goblin gob : goblins) {
            gob.resetEnemy();
        }

        for (Mushroom mush : mushrooms) {
            mush.resetEnemy();
        }

        for (Skeleton sk : skeletons) {
            sk.resetEnemy();
        }
    }

    public List<Necromancer> getNecromancer() {
        return necromancer;
    }
}

