package jadeddelta.genjwld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jadeddelta.genjwld.data.Assets;
import jadeddelta.genjwld.screens.SplashScreen;

public class GenjeweledGame extends Game {
	public SpriteBatch batch;
	public Assets manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new Assets();

		manager.load();

		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
