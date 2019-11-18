package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class Ships extends Sprite {

    protected Rect worldBounds;
    protected Vector2 v = new Vector2(); //скорость корабля

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion; //текстура пули

    protected Vector2 bulletSpeed;

    protected float reloadInterval;
    protected float reloadTimer;

    private float damageInterval = 0.1f;
    private float damageTimer = damageInterval;
    protected float bulletSize;
    protected int bulletDamage;
    protected Sound shootSound; //звук выстрела


    public Ships() {
        super();
    }

    public Ships(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageTimer += delta;
        if (damageTimer >= damageInterval){
            frame = 0;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot(){ //Стрельба
        shootSound.play(0.25f);
        Bullet bullet = bulletPool.obtain(); //

        bullet.set(this, bulletRegion, pos, bulletSpeed, bulletSize, worldBounds, bulletDamage);
    }

    public void dispose() {
        shootSound.dispose(); //освобождаем ресурсы
    }
}
