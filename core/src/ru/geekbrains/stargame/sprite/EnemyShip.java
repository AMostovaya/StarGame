package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class EnemyShip extends Ships {

    private enum State {ENTRY, FIGHT} //состояние корабля ENTRY:  только появляется на экране, FIGHT: начинает бой
    private State state;
    private Vector2 entrySpeed = new Vector2(0, -0.5f); //при появлении скорость должна быть выше

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {

        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.v.set(vDelta);
        this.bulletSpeed = new Vector2();

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v, delta);
        switch (state){
            case ENTRY:
                if (getTop() <= worldBounds.getTop()){ //если появляется передняя часть корабля
                    v.set(vDelta);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval){
                    reloadTimer = 0f;
                    // стреляем
                    shoot();
                }
                if (getBottom() < worldBounds.getBottom()){
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



