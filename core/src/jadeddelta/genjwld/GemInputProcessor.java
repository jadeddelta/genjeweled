package jadeddelta.genjwld;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import jadeddelta.genjwld.screens.GameScreen;

public class GemInputProcessor implements InputProcessor {

    private GameScreen screen;
    private Vector3 tp = new Vector3();

    public GemInputProcessor(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0)
            return false;
        tp = screen.unproject(tp.set(screenX, screenY, 0));

        // TODO: doesn't exactly work on resize, but we'll figure something out
        screen.board.selectGem((int)tp.x, (int)tp.y);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
