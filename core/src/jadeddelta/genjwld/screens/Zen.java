package jadeddelta.genjwld.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import jadeddelta.genjwld.GenjeweledGame;

public class Zen implements Screen {
    final GenjeweledGame game;
    OrthographicCamera camera;
    public Zen(GenjeweledGame game) {
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
