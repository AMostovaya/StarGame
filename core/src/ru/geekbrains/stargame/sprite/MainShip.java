package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class MainShip extends Ships {

    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;


    private boolean isLeft; // нажата левая стрелка
    private boolean isRight; // нажата правая стрелка

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Shoot.wav"));
        bulletRegion = atlas.findRegion("bulletMainShip");
        vDelta.set(0.5f, 0);
        reloadInterval = 0.2f;
        damage = 1;
        bulletHeight = 0.01f;
        bulletSpeed.set(0,0.5f);
        hp = 100;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void dispose() {
        shootSound.dispose();
    }

    @Override
    public void update(float delta) {

        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;

        if (reloadTimer >= reloadInterval){ //
            reloadTimer = 0f;
            shoot();
        }

        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }



    // движение по нажатию клавиши
    public void keyDown(int keycode) {
        switch (keycode){ //если нажата
            case Input.Keys.LEFT:
                isLeft = true;
                moveLeft(); //движение влево
                break;
            case Input.Keys.RIGHT:
                isRight = true;
                moveRight(); //движение вправо
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isRight = false;
                if (isLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isLeft = false;
                if (isRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
    }

    private void moveRight() {
        v.set(vDelta);
    }

    private void moveLeft() {
        v.set(vDelta).rotate(180);
    }

    public  void stop() {
        v.setZero();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {

        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }


}
