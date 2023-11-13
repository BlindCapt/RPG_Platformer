package entities;

import main.Game;

import static utilz.Constants.Direction.LEFT;
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.EnemyConstants.*;

public class Skeleton extends Enemy {
    /// ------------------------------- ATTRIBUTE ------------------------------- ///

    /// ------------------------------- CONSTRUCTOR ------------------------------- ///

    public Skeleton(float x, float y) {
        super(x, y, SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON);
        initHitBox(20, 39);
        initAttackBox(47, 35);
        defense = 5;
        maxHealth = 20;
        currentHealth = maxHealth;

    }

    /// ------------------------------- METHOD ------------------------------- ///

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if (walkDir == RIGHT) {
            attackBox.x = hitBox.x + hitBox.width;
        } else if (walkDir == LEFT) {
            attackBox.x = hitBox.x - attackBox.width;
        }
        attackBox.y = hitBox.y - (Game.SCALE * 2);
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAIr(lvlData);
        } else {
            switch (state) {
                case IDLE -> {
                    newState(ATTACK);
                }
                case RUN -> {
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);

                        if (isPlayerCloseForAttack(player)) {
                            newState(ATTACK);
                        }
                    }
                    move(lvlData);
                }
                case ATTACK -> {
                    if (aniIndex == 0) {
                        attackChecked = false;
                    }
                    // Frame où on check les dmg
                    if (aniIndex == 6 && !attackChecked) {
                        checkEnemyHit(attackBox, player);
                    }
                }
                case HURT -> {

                }
            }
        }
    }

    public int flipX() {
        if (walkDir == RIGHT) {

            return 0;
        } else {

            return width + (int) (Game.SCALE * 5);
        }
    }

    public int flipY() {
        if (walkDir == RIGHT) {
            return 1;
        } else {
            return -1;
        }
    }


/// ------------------------------- GETTER AND SETTER ------------------------------- ///
}
