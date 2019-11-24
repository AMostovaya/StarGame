package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.Sprite;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {

       super(atlas.findRegion("message_game_over"));
       setHeightProportion(0.05f);
       setBottom(0.01f);

    }
}
