package jadeddelta.genjwld.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import jadeddelta.genjwld.gameplay_elements.GemColor;
import jadeddelta.genjwld.gameplay_elements.GemEnhancement;

public class Assets {

    private final AssetManager manager;

    public Assets() {
        manager = new AssetManager();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    public void load() {
        manager.load("gems/redgem.png", Texture.class);
        manager.load("gems/bluegem.png", Texture.class);
        manager.load("gems/greengem.png", Texture.class);
        manager.load("gems/purplegem.png", Texture.class);
        manager.load("gems/whitegem.png", Texture.class);
        manager.load("gems/yellowgem.png", Texture.class);
        manager.load("gems/orangegem.png", Texture.class);
        manager.load("gems/none.png", Texture.class);
        manager.load("effects/selected.png", Texture.class);
        manager.load("effects/flame.png", Texture.class);
        manager.load("effects/lightning.png", Texture.class);
        manager.load("effects/smoke.png", Texture.class);

        manager.load("elements/score-indicator/scoreBar.png", Texture.class);
        manager.load("elements/score-indicator/scoreFill.png", Texture.class);

        FreetypeFontLoader.FreeTypeFontLoaderParameter mainTitleFont =
                new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mainTitleFont.fontFileName = "fonts/oxygen.ttf";
        mainTitleFont.fontParameters.size = 54;
        mainTitleFont.fontParameters.color = Color.TEAL;

        FreetypeFontLoader.FreeTypeFontLoaderParameter mainSubtitleFont =
                new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mainSubtitleFont.fontFileName = "fonts/oxygen.ttf";
        mainSubtitleFont.fontParameters.size = 36;
        mainSubtitleFont.fontParameters.color = Color.TEAL;

        FreetypeFontLoader.FreeTypeFontLoaderParameter scoreFont =
                new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        scoreFont.fontFileName = "fonts/oxygen.ttf";
        scoreFont.fontParameters.size = 30;
        scoreFont.fontParameters.color = Color.LIGHT_GRAY;

        manager.load("oxygen54.ttf", BitmapFont.class, mainTitleFont);
        manager.load("oxygen36.ttf", BitmapFont.class, mainSubtitleFont);
        manager.load("oxygen30.ttf", BitmapFont.class, scoreFont);
    }

    public BitmapFont getMainFonts(boolean isSubtitle) {
        return manager.get(isSubtitle ? "oxygen54.ttf" : "oxygen36.ttf", BitmapFont.class);
    }

    public BitmapFont getScoreText() {
        return manager.get("oxygen30.ttf", BitmapFont.class);
    }

    public Texture getGemTexture(GemColor color) {
        StringBuilder path = new StringBuilder("gems/");
        switch (color) {
            case RED:
                path.append("redgem");
                break;
            case GREEN:
                path.append("greengem");
                break;
            case BLUE:
                path.append("bluegem");
                break;
            case YELLOW:
                path.append("yellowgem");
                break;
            case PURPLE:
                path.append("purplegem");
                break;
            case SPECIAL:
            case WHITE:
                path.append("whitegem");
                break;
            case ORANGE:
                path.append("orangegem");
                break;
            case NONE:
                path.append("none");
                break;
        }
        path.append(".png");
        return manager.get(path.toString());
    }

    public Texture getGemEffects(GemEnhancement enhancement) {
        StringBuilder path = new StringBuilder("effects/");
        switch (enhancement) {
            case FLAME:
                path.append("flame.png");
                break;
            case LIGHTNING:
                path.append("lightning.png");
                break;
            case NONE:
                return null;
        }
        return manager.get(path.toString());
    }

    public Texture getGemSelected() {
        return manager.get("effects/selected.png");
    }

    public Texture getSmoke() {
        return manager.get("effects/smoke.png");
    }

    public Texture getScoreBar() {
        return manager.get("elements/score-indicator/scoreBar.png");
    }

    public Texture getScoreFill() {
        return manager.get("elements/score-indicator/scoreFill.png");
    }

    public boolean update() {
        return manager.update();
    }

    public float getProgress() {
        return manager.getProgress();
    }

}
