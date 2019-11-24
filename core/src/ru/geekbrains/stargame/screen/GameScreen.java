package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.EnemyShip;
import ru.geekbrains.stargame.sprite.GameOver;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.NewGameButton;
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
    private ExplosionPool explosionPool;
    private Music music;
    private enum StateGame {PLAY, GAME_OVER}; // PLAY: в процессе игры, GAME_OVER: игра окончена
    StateGame stateGame;
    private GameOver messageGameOver; // надпись "Конец игры"
    private NewGameButton newGameButton;

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
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);

        ship = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitters(enemyPool, atlas, worldBounds);

       // фоновое сопровождение
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/MainTheme.mp3"));
        music.setLooping(true); //повтор
        music.setVolume(0.5f); //громкость
        music.play();

        messageGameOver = new GameOver(atlas);
        stateGame = StateGame.PLAY;
        newGameButton = new NewGameButton(atlas, this);

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
        explosionPool.dispose();
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
       if (stateGame == StateGame.PLAY) {
           ship.touchUp(touch, pointer);
       }
       else  {
           newGameButton.touchUp(touch, pointer);
       }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
       if (stateGame == StateGame.PLAY) {
           ship.touchDown(touch, pointer);
       }
       else {
           newGameButton.touchDown(touch, pointer);
       }
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
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    public void freeAllDestroyed() {
        // если корабль уничотжен, конец игре
        if (ship.isDestroyed()) {
            stateGame = StateGame.GAME_OVER;
        }
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);

        for (Star star: stars) {
            star.draw(batch);
        }

        switch (stateGame) {

            case PLAY:
                ship.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                explosionPool.drawActiveSprites(batch);
                break;

            case GAME_OVER:
                messageGameOver.draw(batch); // выводим надпись
                newGameButton.draw(batch); // выводим кнопку "Новая игра"
                break;
        }


        batch.end();
    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        // в зависимости от состояния игры
        switch (stateGame) {
            case PLAY:
                ship.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                explosionPool.updateActiveSprites(delta);
                enemyEmitter.generate(delta);
                break;

            case GAME_OVER:
                break;
        }
    }

    private void checkCollisions() {

        if (stateGame == StateGame.PLAY) { // коллизиии проверяем, только пока игра

            List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
            List<Bullet> bulletList = bulletPool.getActiveObjects();

            for (EnemyShip enemyShip : enemyShipList) {

                float minDist = enemyShip.getHalfWidth() + ship.getHalfWidth();

                if (ship.pos.dst(enemyShip.pos) < minDist) {
                    ship.damage(enemyShip.getDamage());
                    enemyShip.destroy();
                }

                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != ship) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(ship.getDamage());
                        bullet.destroy();
                    }
                }

                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() == ship) {
                        continue;
                    }
                    if (ship.isBulletCollision(bullet)) {
                        ship.damage(bullet.getDamage());
                        bullet.destroy();
                    }
                }

            }

        }

    }

    public void newGame() {

        stateGame = StateGame.PLAY;
        ship.unDestroy(); // восстановим корабль
        ship.setHp(10);
        freeAllDestroyed();

    }
}
