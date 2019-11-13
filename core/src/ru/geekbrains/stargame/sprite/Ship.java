package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Ship extends Sprite {

    private Vector2 v = new Vector2(); // скорость
    private boolean isLeft; // нажата левая стрелка
    private boolean isRight; // нажата правая стрелка
    private Vector2 vDelta = new Vector2(0.2f, 0f); //скорость
    private Rect worldbound;


    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"),1,2,2);
        setHeightProportion(0.19f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldbound = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        // не убегаем за границы
        checkBounds();
    }

    private void checkBounds() {

        if (getRight()> worldbound.getRight()) setRight(worldbound.getRight());
        if (getLeft() < worldbound.getLeft()) setLeft(worldbound.getLeft());
    }

    // движение по нажатию клавиши
    public boolean keyDown(int keycode) {
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
        return false;
    }

    private void moveRight() {
        v.set(vDelta);
    }

    private void moveLeft() {
        v.set(vDelta).rotate(180);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(touch.x < worldbound.pos.x){
            moveLeft();
        }
        else {
            moveRight();
        }
        return super.touchDown(touch, pointer);
    }
}
