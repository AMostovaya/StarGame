package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class Star extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldbound;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.01f, 0.0065f));
        float vy = Rnd.nextFloat(-0.005f, -0.001f);
        float vx = Rnd.nextFloat(-0.0005f, 0.0005f);
        v.set(vx,vy);
    }

    @Override
    public void update(float delta) {
        pos.add(v);
        checkBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldbound = worldBounds;
        float posX = Rnd.nextFloat(worldbound.getLeft(), worldbound.getRight());
        float posY = Rnd.nextFloat(worldbound.getBottom(), worldbound.getTop());
        pos.set(posX, posY);

    }

    private void checkBounds() {
        if (getRight()< worldbound.getLeft()) setLeft(worldbound.getRight());
        if (getLeft() > worldbound.getRight()) setRight(worldbound.getLeft());
        if (getTop()< worldbound.getBottom()) setBottom(worldbound.getTop());
        if (getBottom() > worldbound.getTop()) setTop(worldbound.getBottom());
    }
}
