package jadeddelta.genjwld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import jadeddelta.genjwld.screens.MainScreen;
import jdk.tools.jmod.Main;

public class GenjeweledGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/oxygen.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter para = new FreeTypeFontGenerator.FreeTypeFontParameter();
		para.size = 30;
		para.color = Color.YELLOW;
		para.borderColor = Color.BLACK;
		para.borderWidth = 3;
		font = gen.generateFont(para);
		gen.dispose();

		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
