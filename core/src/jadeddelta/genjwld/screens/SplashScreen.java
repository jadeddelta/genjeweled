package jadeddelta.genjwld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import jadeddelta.genjwld.GenjeweledGame;

public class SplashScreen implements Screen {

    final GenjeweledGame game;
    private Texture splashBg;

    public SplashScreen(GenjeweledGame game) {
        this.game = game;
        this.splashBg = new Texture(Gdx.files.internal("bgs/splash.png"));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (game.manager.update()) {
            game.setScreen(new MainScreen(game));
            dispose();
        }
        else {
            game.batch.begin();
            game.batch.disableBlending();
            game.batch.draw(splashBg, 0, 0, 1600, 900);
            game.batch.enableBlending();
            game.batch.end();
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
        splashBg.dispose();
    }
}
