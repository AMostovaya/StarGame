package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BonusPool;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class EnemyShip extends Ships {

    private enum State {ENTRY, FIGHT} //состояние корабля ENTRY:  только появляется на экране, FIGHT: начинает бой
    private State state;
    private Vector2 entrySpeed = new Vector2(0, -0.5f); //при появлении скорость должна быть выше

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, BonusPool bonusPool) {

        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.v.set(vDelta);
        this.bulletSpeed = new Vector2();
        this.bonusPool = bonusPool;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        switch (state){
            case ENTRY:
            reloadTimer = 0f;
            if (getTop() <= worldBounds.getTop()) {
                v.set(vDelta);
                state = State.FIGHT;
                reloadTimer = reloadInterval;
            }
            break;
            case FIGHT:
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            Sound sound,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.vDelta.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.shootSound = sound;
        setHeightProportion(height);
        this.hp = hp;

        reloadTimer = reloadInterval; //начинаем стрельбу при появлении
        v.set(entrySpeed);
        state = State.ENTRY; // начальное состояние корабля

    }

}



