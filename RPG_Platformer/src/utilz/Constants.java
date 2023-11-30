package utilz;

import main.Game;

public class Constants {
    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int PLAYER_ANI_SPEED = 15;
    public static final int ENEMY_ANI_SPEED = 25;
    public static final int OBJECT_ANI_SPEED = 30;

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Projectiles {
        public static final int VFX1_WIDTH = (int) (Game.SCALE * 32);
        public static final int VFX1_HEIGHT = (int) (Game.SCALE * 40);

        public static final int VFX2_WIDTH = (int) (Game.SCALE * 32);
        public static final int VFX2_HEIGHT = (int) (Game.SCALE * 40);

        public static final int PROJECTILE_XDRAW_OFFSET = (int) (Game.SCALE * 10);
        public static final int PROJECTILE_YDRAW_OFFSET = (int) (Game.SCALE * 20);

        public static final float SPEED = 0.5f * Game.SCALE;
    }

    public static class ObjectConstants {
        public static final int CHEST = 0;
        public static final int LIFE_POTION = 1;
        public static final int STM_POTION = 2;
        public static final int HELMET = 3;
        public static final int CHEST_PLATE = 4;
        public static final int LEGS = 5;
        public static final int SHOES = 6;
        public static final int WEAPON = 7;


        public static final int CHEST_DEFAULT_WIDTH = 48;
        public static final int CHEST_DEFAULT_HEIGHT = 32;
        public static final int CHEST_WIDTH = (int) (Game.SCALE * CHEST_DEFAULT_WIDTH);
        public static final int CHEST_HEIGHT = (int) (Game.SCALE * CHEST_DEFAULT_HEIGHT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static final int ARMOR_WIDTH_DEFAULT = 32;
        public static final int ARMOR_HEIGHT_DEFAULT = 32;
        public static final int ARMOR_WIDTH = (int) (Game.SCALE * ARMOR_WIDTH_DEFAULT / 2.5);
        public static final int ARMOR_HEIGHT = (int) (Game.SCALE * ARMOR_HEIGHT_DEFAULT / 2.5);


        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case CHEST -> {
                    return 5;
                }
                default -> {
                    return 1;
                }
            }
        }

    }

    public static class EnemyConstants {
        public static final int FLYING_EYE = 0;
        public static final int GOBLIN = 1;
        public static final int MUSHROOM = 2;
        public static final int SKELETON = 3;
        public static final int NECROMANCER = 4;
        public static final int NIGHTBORNE = 5;

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int HURT = 3;
        public static final int DEATH = 4;
        public static final int ATTACK_2 = 5;
        public static final int ATTACK_3 = 6;


        public static final int ENEMIES_WIDTH_DEFAULT = 150;
        public static final int ENEMIES_HEIGHT_DEFAULT = 150;

        public static final int FLYING_EYE_WIDTH = (int) (ENEMIES_WIDTH_DEFAULT * Game.SCALE * 0.8);
        public static final int FLYING_EYE_HEIGHT = (int) (ENEMIES_HEIGHT_DEFAULT * Game.SCALE * 0.8);
        public static final int FLYING_EYE_DRAWOFFSET_X = (int) (57 * Game.SCALE * 0.8);
        public static final int FLYING_EYE_DRAWOFFSET_Y = (int) (61 * Game.SCALE * 0.8);

        public static final int GOBLIN_WIDTH = (int) (ENEMIES_WIDTH_DEFAULT * Game.SCALE);
        public static final int GOBLIN_HEIGHT = (int) (ENEMIES_HEIGHT_DEFAULT * Game.SCALE);
        public static final int GOBLIN_DRAWOFFSET_X = (int) (65 * Game.SCALE);
        public static final int GOBLIN_DRAWOFFSET_Y = (int) (61 * Game.SCALE);

        public static final int MUSHROOM_WIDTH = (int) (ENEMIES_WIDTH_DEFAULT * Game.SCALE * 0.8);
        public static final int MUSHROOM_HEIGHT = (int) (ENEMIES_HEIGHT_DEFAULT * Game.SCALE * 0.8);
        public static final int MUSHROOM_DRAWOFFSET_X = (int) (65 * Game.SCALE * 0.8);
        public static final int MUSHROOM_DRAWOFFSET_Y = (int) (50 * Game.SCALE * 0.8);

        public static final int SKELETON_WIDTH = (int) (ENEMIES_WIDTH_DEFAULT * Game.SCALE * 0.8);
        public static final int SKELETON_HEIGHT = (int) (ENEMIES_HEIGHT_DEFAULT * Game.SCALE * 0.8);
        public static final int SKELETON_DRAWOFFSET_X = (int) (65 * Game.SCALE * 0.8);
        public static final int SKELETON_DRAWOFFSET_Y = (int) (50 * Game.SCALE * 0.8);

        public static final int NECROMANCER_WIDTH_DEFAULT = 160;
        public static final int NECROMANCER_HEIGHT_DEFAULT = 128;
        public static final int NECROMANCER_WIDTH = (int) (NECROMANCER_WIDTH_DEFAULT * Game.SCALE * 1.8);
        public static final int NECROMANCER_HEIGHT = (int) (NECROMANCER_HEIGHT_DEFAULT * Game.SCALE * 1.8);
        public static final int NECROMANCER_DRAWOFFSET_X = (int) (125 * Game.SCALE);
        public static final int NECROMANCER_DRAWOFFSET_Y = (int) (130 * Game.SCALE);


        public static final int NIGHTBORNE_WIDTH_DEFAULT = 80;
        public static final int NIGHTBORNE_HEIGHT_DEFAULT = 80;
        public static final int NIGHTBORNE_WIDTH = (int) (NIGHTBORNE_WIDTH_DEFAULT * Game.SCALE * 2.5);
        public static final int NIGHTBORNE_HEIGHT = (int) (NIGHTBORNE_HEIGHT_DEFAULT * Game.SCALE * 2.5);
        public static final int NIGHTBORNE_DRAWOFFSET_X = (int) (73 * Game.SCALE);
        public static final int NIGHTBORNE_DRAWOFFSET_Y = (int) (75 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case FLYING_EYE -> {
                    switch (enemy_state) {
                        case ATTACK, ATTACK_2, RUN -> {
                            return 8;
                        }
                        case ATTACK_3 -> {
                            return 6;
                        }
                        case DEATH, HURT -> {
                            return 4;
                        }
                        default -> {
                            return 1;
                        }
                    }
                }
                case GOBLIN -> {
                    switch (enemy_state) {
                        case ATTACK, ATTACK_2, RUN -> {
                            return 8;
                        }
                        case ATTACK_3 -> {
                            return 12;
                        }
                        case DEATH, HURT, IDLE -> {
                            return 4;
                        }
                        default -> {
                            return 1;
                        }
                    }
                }
                case MUSHROOM -> {
                    switch (enemy_state) {
                        case ATTACK, ATTACK_2, RUN -> {
                            return 8;
                        }
                        case ATTACK_3 -> {
                            return 11;
                        }
                        case DEATH, HURT, IDLE -> {
                            return 4;
                        }
                        default -> {
                            return 1;
                        }
                    }
                }
                case SKELETON -> {
                    switch (enemy_state) {
                        case ATTACK, ATTACK_2 -> {
                            return 8;
                        }
                        case ATTACK_3 -> {
                            return 6;
                        }
                        case DEATH, HURT, IDLE, RUN -> {
                            return 4;
                        }
                        default -> {
                            return 1;
                        }
                    }
                }
                case NECROMANCER -> {
                    switch (enemy_state) {
                        case ATTACK, ATTACK_2, RUN -> {
                            return 8;
                        }
                        case ATTACK_3 -> {
                            return 6;
                        }
                        case DEATH, HURT, IDLE -> {
                            return 4;
                        }
                        default -> {
                            return 1;
                        }
                    }
                }
                case NIGHTBORNE -> {
                    switch (enemy_state) {
                        case ATTACK -> {
                            return 12;
                        }
                        case RUN -> {
                            return 6;
                        }
                        case HURT -> {
                            return 5;
                        }
                        case DEATH -> {
                            return 23;
                        }
                        case IDLE -> {
                            return 9;
                        }
                        default -> {
                            return 1;
                        }
                    }
                }
                default -> {
                    return -1;
                }
            }
        }

        public static int GetMaxHealth(int enemyType) {
            switch (enemyType) {
                case FLYING_EYE, GOBLIN, MUSHROOM, SKELETON -> {
                    return 10;
                }
                case NECROMANCER, NIGHTBORNE -> {
                    return 100;
                }
                default -> {
                    return 1;
                }
            }
        }

        public static int getEnemyDmg(int enemyType) {
            switch (enemyType) {
                case GOBLIN -> {
                    return 30;
                }
                case FLYING_EYE -> {
                    return 20;
                }
                case MUSHROOM -> {
                    return 15;
                }
                case SKELETON -> {
                    return 25;
                }
                case NECROMANCER -> {
                    return 100;
                }
                case NIGHTBORNE -> {
                    return 50;
                }
                default -> {
                    return 0;
                }
            }
        }
    }

    public static class PlayerConstants {

        public static final int ATTACK = 0;
        public static final int ATTACK_2 = 1;
        public static final int ATTACK_COMBO = 2;
        public static final int DEATH = 3;
        public static final int FALL = 4;
        public static final int HIT = 5;
        public static final int IDLE = 6;
        public static final int JUMP = 7;
        public static final int ROLL = 8;
        public static final int RUN = 9;
        public static final int TURN_AROUND = 10;


        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case ATTACK -> {
                    return 4;
                }
                case ATTACK_2 -> {
                    return 6;
                }
                case ATTACK_COMBO, RUN, DEATH, IDLE -> {
                    return 10;
                }
                case FALL, TURN_AROUND, JUMP -> {
                    return 3;
                }
                case HIT -> {
                    return 1;
                }
                case ROLL -> {
                    return 12;
                }
                default -> {
                    return 0;
                }
            }
        }

    }
}
