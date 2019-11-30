package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Bonus;

public class BonusPool extends SpritesPool<Bonus> {
    private TextureAtlas atlas;
    private Rect worldBounds;
    private Sound sound;

    public BonusPool(TextureAtlas atlas, Rect worldBounds) {
        this.atlas = atlas;
        this.worldBounds = worldBounds;
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Bonus.wav"));


    }

    @Override
    protected Bonus newObject() {
        return new Bonus(atlas, worldBounds, sound);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
