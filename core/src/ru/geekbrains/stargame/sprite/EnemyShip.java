package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class EnemyShip extends Ships {

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {

        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.v.set(vDelta);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
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
        this.v.set(vDelta);

    }



    }



