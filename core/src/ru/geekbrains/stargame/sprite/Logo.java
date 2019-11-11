package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Logo extends Sprite {

   public Vector2 v; // speed
    public Vector2 ptouch;
    public Vector2 buff;
    public static float V_LENGTH = 0.01f;
    private Rect worldBounds;


    public Logo(TextureRegion region) {
        super(region);
        v = new Vector2();
        ptouch = new Vector2();
        buff = new Vector2();
        worldBounds = new Rect();

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ptouch.set(touch);
        v.set(ptouch.cpy().sub(getPos()));
        v.setLength(V_LENGTH);
        return false;
    }

    @Override
    public void update(float delta)     {
// здесь не срабатывает проверка не убегания за границы
        checkBounds();
}


    private void checkBounds() {

        if(getRight()<worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if(getLeft()>worldBounds.getRight()) setRight(worldBounds.getLeft());
        if(getTop()<worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if(getBottom()>worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

}
