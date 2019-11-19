package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public abstract class Ships extends Sprite {

    protected Rect worldBounds;
    protected Vector2 v = new Vector2(); //скорость корабля
    protected Vector2 vDelta = new Vector2(0.2f, 0f); //скорость

    protected int hp; // количество жизней

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion; //текстура пули

    protected Vector2 bulletSpeed = new Vector2();

    protected float reloadInterval =0f;
    protected float reloadTimer =0f;

    protected float damageInterval = 0.1f;
    protected float damageTimer = damageInterval;
    protected float bulletSize;
    protected int bulletDamage;
    protected Sound shootSound; //звук выстрела

    protected float bulletHeight;
    protected int damage;



    public Ships() {
        super();
    }

    public Ships(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {

        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        pos.mulAdd(v, delta);


    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    protected void shoot(){ //Стрельба
      shootSound.play(0.25f);
      Bullet bullet = bulletPool.obtain(); //
      bullet.set(this, bulletRegion, pos, bulletSpeed, bulletHeight, worldBounds, damage);
    }

    public void dispose() {
        shootSound.dispose(); //освобождаем ресурсы
    }
}
