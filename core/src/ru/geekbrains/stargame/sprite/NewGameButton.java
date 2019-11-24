package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.ScaledTouchUpButton;
import ru.geekbrains.stargame.screen.GameScreen;

public class NewGameButton extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.05f);
        setBottom(-0.2f);
        this.gameScreen = gameScreen;
    }

    @Override
    public void action() {
        // новая игра
        gameScreen.newGame();
    }
}
