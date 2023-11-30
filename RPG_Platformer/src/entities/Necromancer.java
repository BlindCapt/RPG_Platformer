package entities;

import gameStates.Playing;
import main.Game;
import objects.Projectile;

import static main.Game.GAME_HEIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Direction.*;
import static utilz.HelpMethod.IsSightClear;

public class Necromancer extends Enemy {

    public Necromancer(float x, float y) {
        super(x, y, NECROMANCER_WIDTH, NECROMANCER_HEIGHT, NECROMANCER);
        initHitBox(38, 80);
        initAttackBox(85, 35);
        defense = 50;
        maxHealth = 500;
        currentHealth = maxHealth;
        walkDir = LEFT;
        attackDistance = Game.TILES_SIZE * 5;
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if (walkDir == RIGHT) {
            attackBox.x = hitBox.x - (int) (Game.SCALE * 35);
        } else if (walkDir == LEFT) {
            attackBox.x = hitBox.x - (int) (attackBox.width - (Game.SCALE * 35) - hitBox.width);
        }
        attackBox.y = hitBox.y + (Game.SCALE * 4);
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
                    newState(RUN);
                }
                case RUN -> {
                    if (player.getCurrentHealth() > 0) {
                        if (canSeePlayer(lvlData, player)) {
                            turnTowardsPlayer(player);

                            if (isPlayerCloseForAttack(player)) {
                                newState(ATTACK);
                            }
                        }
                    }
                    move(lvlData);
                }
                case ATTACK -> {
                    if (aniIndex == 0) {
                        attackChecked = false;
                    }
                    int dir = 1;
                    if (walkDir == LEFT) {
                        dir = -1;
                    }
                    if (aniIndex == 6 && !attackChecked) {
                        playing.getObjectManager().getProjectiles().add(new Projectile((int)getHitBox().x, (int)getHitBox().y, dir,playing));
                    }

                }
            }
        }
    }

    @Override
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        if (playing.getDownBorder() == GAME_HEIGHT - 2 * Game.TILES_SIZE) {
            return true;
        }

        return false;
    }

    public int flipX() {
        if (walkDir == RIGHT) {

            return 0;
        } else {

            return (NECROMANCER_WIDTH);
        }
    }

    public int flipY() {
        if (walkDir == RIGHT) {
            return 1;
        } else {
            return -1;
        }
    }


}
