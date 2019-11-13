package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.ButtonExit;
import ru.geekbrains.stargame.sprite.ButtonPlay;
import ru.geekbrains.stargame.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUTNT = 256;
    private Texture bg;
    private Background background;
    private Game game;
    private TextureAtlas atlas;
    private Star[] stars;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Star[STAR_COUTNT];
        for (int i =0; i< STAR_COUTNT; i++) {
            stars[i] = new Star(atlas);

        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();

/*
        if (logo.buff.sub(logo.getPos()).len() > Logo.V_LENGTH) {
            logo.getPos().add(logo.v);
        } else
            logo.getPos().set(logo.ptouch);
*/

        // по нажатию кнопок: вверх, вниз, вправо, влево
        /*if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pos.x -= 100 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pos.x += 100 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            pos.y += 100 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            pos.y -= 100 * Gdx.graphics.getDeltaTime();
        }*/



    }

    @Override
    public void dispose() {

        bg.dispose();
        atlas.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {

        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return false;

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    private void update(float delta) {

        for (Star star: stars) {
            star.update(delta);
        }

    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }

        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();

    }
}
