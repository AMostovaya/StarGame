package ru.geekbrains.stargame.sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class Bonus extends Sprite {

    private Vector2 v = new Vector2();
    private float animateTimer;
    private int countRecover;
    private float animateInterval = 0.01f;
    private Sound sound;

    private Rect worldBounds;

    public int getCountRecover() {
        return countRecover = (int)(Rnd.nextFloat(1.0f, 3.0f));
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public Bonus(TextureAtlas atlas, Rect worldBounds, Sound sound) {
        super(atlas.findRegion("bulletMainShip"));
        this.worldBounds = worldBounds;
        this.sound = sound;
        setHeightProportion(0.03f);
        float vy = Rnd.nextFloat(-0.005f, -0.001f);
        float vx = Rnd.nextFloat(-0.0005f, 0.0005f);
        v.set(vx,vy);

    }

    @Override
    public void update(float delta) {
       animateTimer+= delta;
       pos.mulAdd(v, animateTimer);
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }

    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);


    }

    public void Play() {
        sound.play();
    }




}
