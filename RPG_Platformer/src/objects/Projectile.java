package objects;

import gameStates.Playing;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Projectiles.*;
import static utilz.HelpMethod.IsSolid;

public class Projectile {

    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;
    private Playing playing;

    public Projectile(int x, int y, int dir, Playing playing) {
        hitbox = new Rectangle2D.Float(x, y, VFX1_WIDTH, VFX1_HEIGHT);
        this.dir = dir;
        this.playing = playing;
    }

    public void update(int[][] lvlData) {
        hitbox.x += dir * SPEED;
        checkProjectilHit();
        checkProjectileHitWall(lvlData);
    }

    private void checkProjectilHit() {
        if (this.hitbox.intersects(playing.getPlayer().getHitBox())) {
            playing.getPlayer().changeHealth(-50);
            active = false;

        }

    }

    private void checkProjectileHitWall(int[][] lvlData) {
        if (IsSolid(hitbox.x, hitbox.y, lvlData)) {
            active = false;
        }
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
