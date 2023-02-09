package jadeddelta.genjwld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import jadeddelta.genjwld.GenjeweledGame;

public class MainScreen implements Screen {
    final GenjeweledGame game;
    BitmapFont titleFont;
    BitmapFont subtitleFont;

    public MainScreen(GenjeweledGame game) {
        this.game = game;
        titleFont = game.manager.getMainFonts(true);
        subtitleFont = game.manager.getMainFonts(false);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.batch.begin();
        titleFont.draw(game.batch, "genjeweled", 600, 650);
        subtitleFont.draw(game.batch, "tap anywhere or press a key to begin", 500, 450);
        subtitleFont.draw(game.batch, "made w/ love", 1300, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new Classic(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
