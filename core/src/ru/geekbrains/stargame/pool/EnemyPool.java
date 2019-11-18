package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {
    private Sound shootSound; //звук выстрела
    private BulletPool bulletPool;
    private Rect worldBounds;



    public EnemyPool(BulletPool bulletPool) {

        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Shoot.wav")); //звук
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;

    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }

    public void update(float delta) {
    }
}
