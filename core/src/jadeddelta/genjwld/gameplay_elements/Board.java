package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Board {

    private Array<Array<Gem>> gems;

    public Board(Array<Array<Gem>> gems) {
        this.gems = gems;
    }

    public static Board defaultBoard() {
        Array<Array<Gem>> gems = new Array<>();
        for (int i = 0; i < 8; i++) {
            Array<Gem> gemArray = new Array<>(8);
            for (int j = 0; j < 8; j++) {
                gemArray.add(Gem.randomGem());
            }
            gems.add(gemArray);

        }
        return new Board(gems);
    }

    public void render(float delta, SpriteBatch batch) {
        // TODO for tomorrow! figure out this nonsense!
    }
}
