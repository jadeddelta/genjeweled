package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Board {

    private Array<Array<Gem>> gems;

    private Texture redGem, blueGem, greenGem, purpleGem, whiteGem, yellowGem, orangeGem;

    //TODO: all of these will be changed for the sake of customizable boards
    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private final int SLOT_WIDTH = 75;

    public Board(Array<Array<Gem>> gems) {
        this.gems = gems;
        this.redGem = new Texture(Gdx.files.internal("gems/redgem.png"));
        this.blueGem = new Texture(Gdx.files.internal("gems/bluegem.png"));
        this.greenGem = new Texture(Gdx.files.internal("gems/greengem.png"));
        this.purpleGem = new Texture(Gdx.files.internal("gems/purplegem.png"));
        this.whiteGem = new Texture(Gdx.files.internal("gems/whitegem.png"));
        this.yellowGem = new Texture(Gdx.files.internal("gems/yellowgem.png"));
        this.orangeGem = new Texture(Gdx.files.internal("gems/orangegem.png"));
    }

    /**
     * Generates the default 8x8 board used for most games.
     * @return a default board with randomized gems
     */
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
        int minX = 500;
        int y = 150;

        for (Iterator<Array<Gem>> iter = gems.iterator() ; iter.hasNext();) {
            int x = minX;
            for (Iterator<Gem> iter2 = iter.next().iterator() ; iter2.hasNext();) {
                switch (iter2.next().getColor()) {
                    case WHITE:
                        batch.draw(whiteGem, x, y);
                        break;
                    case PURPLE:
                        batch.draw(purpleGem, x, y);
                        break;
                    case YELLOW:
                        batch.draw(yellowGem, x, y);
                        break;
                    case BLUE:
                        batch.draw(blueGem, x, y);
                        break;
                    case GREEN:
                        batch.draw(greenGem, x, y);
                        break;
                    case ORANGE:
                        batch.draw(orangeGem, x, y);
                        break;
                    case RED:
                        batch.draw(redGem, x, y);
                        break;
                    case SPECIAL:

                        break;
                }
                x += SLOT_WIDTH;
            }
            y += SLOT_WIDTH;
        }

    }
}
