package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.EnemyShip;

public class EnemyEmitters {

    private static final float SMALL_BULLET_HEIGHT = 0.01f;
    private static final float SMALL_BULLET_VY = -0.3f;
    private static final int SMALL_BULLET_DAMAGE = 1;
    private static final float SMALL_RELOAD_INTERVAL = 3f;
    private static final float SMALL_HEIGHT = 0.1f;
    private static final int SMALL_HP = 10;

    private static final float MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float MIDDLE_BULLET_VY = -0.25f;
    private static final int MIDDLE_BULLET_DAMAGE = 5;
    private static final float MIDDLE_RELOAD_INTERVAL = 4f;
    private static final float MIDDLE_HEIGHT = 0.15f;
    private static final int MIDDLE_HP = 15;

    private static final float BIG_BULLET_HEIGHT = 0.03f;
    private static final float BIG_BULLET_VY = -0.2f;
    private static final int BIG_BULLET_DAMAGE = 10;
    private static final float BIG_RELOAD_INTERVAL = 2f;
    private static final float BIG_HEIGHT = 0.2f;
    private static final int BIG_HP = 22;

    private float generateInterval = 4f;
    private float generateTimer;

    private EnemyPool enemyPool;
    private Rect worldBounds;

    private TextureRegion[] enemySmallRegions;
    private TextureRegion[] enemyMiddleRegions;
    private TextureRegion[] enemyBigRegions;
    private TextureRegion bulletRegion;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private Vector2 enemyMiddleV = new Vector2(0, -0.03f);
    private Vector2 enemyBigV = new Vector2(0, -0.005f);

    private Sound sound;

    public EnemyEmitters(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMiddleRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));

    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        SMALL_BULLET_HEIGHT,
                        SMALL_BULLET_VY,
                        SMALL_BULLET_DAMAGE,
                        SMALL_RELOAD_INTERVAL,
                        sound,
                        SMALL_HEIGHT,
                        SMALL_HP
                );
            } else if (type < 0.8f) {
                enemy.set(
                        enemyMiddleRegions,
                        enemyMiddleV,
                        bulletRegion,
                        MIDDLE_BULLET_HEIGHT,
                        MIDDLE_BULLET_VY,
                        MIDDLE_BULLET_DAMAGE,
                        MIDDLE_RELOAD_INTERVAL,
                        sound,
                        MIDDLE_HEIGHT,
                        MIDDLE_HP
                );
            } else {
                enemy.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        BIG_BULLET_HEIGHT,
                        BIG_BULLET_VY,
                        BIG_BULLET_DAMAGE,
                        BIG_RELOAD_INTERVAL,
                        sound,
                        BIG_HEIGHT,
                        BIG_HP
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

        public void dispose() {
            sound.dispose();
        }
    }

