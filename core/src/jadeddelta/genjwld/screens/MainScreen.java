package jadeddelta.genjwld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import jadeddelta.genjwld.GenjeweledGame;

public class MainScreen implements Screen {
    final GenjeweledGame game;
    OrthographicCamera camera;

    public MainScreen(GenjeweledGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "genjeweled", 800, 450);
        game.font.draw(game.batch, "tap anywhere to begin", 800, 400);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new Zen(game));
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
