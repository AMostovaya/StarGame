package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Logo extends Sprite {

    public Vector2 v = new Vector2(); // speed
    private Vector2 endPoint = new Vector2();
    public Vector2 buff = new Vector2();
    public static float V_LENGTH = 0.01f;

    public Logo(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        endPoint.set(touch);
        v.set(touch.cpy().sub(pos)).setLength(V_LENGTH);
        return false;
    }

    @Override
    public void update(float delta)     {

        buff.set(endPoint);
        if (buff.sub(pos).len() > V_LENGTH ){

            pos.add(v);

        } else { pos.set(endPoint);}

}


    private void checkBounds() {


    }

}
