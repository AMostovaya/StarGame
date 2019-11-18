package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class MainShip extends Ships {

    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;

    private Vector2 v = new Vector2(); // скорость
    private boolean isLeft; // нажата левая стрелка
    private boolean isRight; // нажата правая стрелка
    private Vector2 vDelta = new Vector2(0.2f, 0f); //скорость
    private Rect worldbound;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV = new Vector2(0, 0.5f);
    private final float reloadInterval = 0.2f;
    private float reloadTimer = 0f;



    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldbound = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {

        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        pos.mulAdd(v, delta);
        if (getRight() > worldbound.getRight()) {
            setRight(worldbound.getRight());
            stop();
        }
        if (getLeft() < worldbound.getLeft()) {
            setLeft(worldbound.getLeft());
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
        if (touch.x < worldbound.pos.x) {
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

    public void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldbound, 1);
    }
}
