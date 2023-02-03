package jadeddelta.genjwld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import jadeddelta.genjwld.data.Assets;
import jadeddelta.genjwld.screens.SplashScreen;

public class GenjeweledGame extends Game {
	public SpriteBatch batch;
	public Assets manager;
	private Viewport viewport;
	public OrthographicCamera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new Assets();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 900);
		viewport = new FitViewport(1600, 900, camera);

		manager.load();

		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
		camera.update();
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
