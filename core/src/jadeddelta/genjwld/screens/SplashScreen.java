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
    private Texture splashBar, splashFill;

    public SplashScreen(GenjeweledGame game) {
        this.game = game;
        this.splashBg = new Texture(Gdx.files.internal("splash/splash.png"));
        this.splashBar = new Texture(Gdx.files.internal("splash/splashBar.png"));
        this.splashFill = new Texture(Gdx.files.internal("splash/splashFill.png"));
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
            float progress = game.manager.getProgress();
            int barPortion = (int) (128 * progress);
            int limitPortion = (int) (500 * progress);

            game.batch.begin();
            game.batch.disableBlending();
            game.batch.draw(splashBg, 0, 0, 1600, 900);
            game.batch.enableBlending();
            game.batch.draw(splashFill,
                    100, 500,
                    limitPortion, 250,
                    0, 0,
                    barPortion, 64,
                    false, false);
            game.batch.draw(splashBar, 100, 500, 500, 250);
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
        splashBar.dispose();
        splashFill.dispose();
    }
}
