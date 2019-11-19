package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.utils.EnemyEmitters;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUTNT = 55;
    private Texture bg;
    private TextureAtlas atlas;
    private MainShip ship;
    private Star[] stars;
    private Background background;
    private BulletPool bulletPool;
    private EnemyEmitters enemyEmitter;
    private EnemyPool enemyPool;


    private Music music;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUTNT];
        for (int i =0; i< STAR_COUTNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool( bulletPool, worldBounds);
        ship = new MainShip(atlas, bulletPool);
        enemyEmitter = new EnemyEmitters(enemyPool, atlas, worldBounds);

       // фоновое сопровождение
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/MainTheme.mp3"));
        music.setLooping(true); //повтор
        music.setVolume(0.5f); //громкость
        music.play();

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);

    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        music.dispose();
        ship.dispose();
        enemyPool.dispose();
        enemyEmitter.dispose();
        super.dispose();
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        ship.touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }

    public void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();

    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        ship.draw(batch);

        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();

    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        ship.update(delta);


        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }
}
