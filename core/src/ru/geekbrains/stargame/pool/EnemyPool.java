package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    protected EnemyShip newObject() {
       return new EnemyShip(bulletPool, explosionPool, worldBounds);
    }


}
