package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.utils.Regions;

public class EnemyShip extends Ships {

   private Vector2 v = new Vector2(0, -0.2f);
   private TextureRegion[] enemyRegions;
   private TextureRegion bulletRegion; //текстура пули
   private EnemyPool enemyPool;
   private Rect worldBounds;

    public EnemyShip() {

    }

    public EnemyShip(TextureAtlas atlas, EnemyPool enemyPool) {

       this.enemyPool = enemyPool;

       TextureRegion textureRegion0 = atlas.findRegion("enemy0");
       this.enemyRegions = Regions.split(textureRegion0, 1, 2, 2); //разделяем тектсуру
       this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getTop() + 0.05f);
    }

    @Override
        public void destroy() {

        }
    }



