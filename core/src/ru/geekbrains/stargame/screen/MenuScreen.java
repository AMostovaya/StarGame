package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;
    private static final float V_LENGTH = 0.5f;
    private Vector2 buff;


    @Override
    public void show() {

        super.show();

        img = new Texture("ScreenSaver.png");
        pos = new Vector2();
        v = new Vector2(2,1);
        touch = new Vector2();
        buff = new Vector2();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, (Gdx.graphics.getHeight() - screenY));
        v.set(touch.cpy().sub(pos)).setLength(V_LENGTH);
        return false;
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1f, 0.45f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // batch.draw(img, 200, 115, 250,250);
        batch.draw(img, pos.x, pos.y, 250, 250);
        batch.setColor(1f, 1f, 1f, 0.2f);
        batch.end();
        buff.set(touch);

        if ((buff.sub(pos)).len()> V_LENGTH) {
            pos.add(v);
        } else {
            pos.set(touch);
        }


        // по нажатию кнопок: вверх, вниз, вправо, влево
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
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
        }

        // по нажатию клавиши мыши
        //if (Gdx.input.isTouched()) {
          //  pos.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
//
  //      }

        if (Gdx.input.getX()<0) pos.x=0;
        if (Gdx.input.getY()<0) pos.y =0;

        if (Gdx.graphics.getHeight() > pos.y + img.getHeight()
        && Gdx.graphics.getWidth()> pos.x + img.getWidth()) {
            //  pos.add(v);
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }


}
