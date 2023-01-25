package jadeddelta.genjwld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import jadeddelta.genjwld.GemInputProcessor;
import jadeddelta.genjwld.GenjeweledGame;
import jadeddelta.genjwld.gameplay_elements.Board;

/**
 * The Zen difficulty screen, which includes always available matches
 * and infinite level design.
 */
public class Zen implements Screen {
    final GenjeweledGame game;
    OrthographicCamera camera;
    Board board;

    public Zen(GenjeweledGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
        this.board = Board.defaultBoard();

        Gdx.input.setInputProcessor(new GemInputProcessor(board));
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
        board.render(delta, game.batch);
        board.scoreIndicator.render(delta, game.batch, game.font);
        game.batch.end();
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
