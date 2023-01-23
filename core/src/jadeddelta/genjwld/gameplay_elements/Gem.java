package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Gem {

    private GemColor color;
    private GemEnhancement enhancement;
    private final static Array<GemColor> GEM_COLORS =
            new Array<>(new GemColor[] {GemColor.RED, GemColor.ORANGE, GemColor.YELLOW,
            GemColor.GREEN, GemColor.BLUE, GemColor.PURPLE, GemColor.WHITE});

    public Gem(GemColor color, GemEnhancement enhancement) {
        this.color = color;
        this.enhancement = enhancement;
    }

    public static Gem randomGem() {
        GemColor color = GEM_COLORS.random();
        return new Gem(color, GemEnhancement.NONE);
    }
}
