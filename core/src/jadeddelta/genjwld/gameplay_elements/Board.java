package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Board {

    private Array<Array<Gem>> gems;

    private Texture redGem, blueGem, greenGem, purpleGem, whiteGem, yellowGem, orangeGem;
    private Texture selectedGem;
    private int selectX, selectY;

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
        this.selectedGem = new Texture(Gdx.files.internal("effects/selected.png"));

        this.selectX = 0;
        this.selectY = 0;
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

    public void selectGem(int x, int y) {
        int minX = 500;
        int minY = 150;
        int selectedX = (x - minX) / SLOT_WIDTH;
        int selectedY = (y - minY) / SLOT_WIDTH;

        if (selectedX < 0 || selectedY < 0) {
            selectedX = -1;
            selectedY = -1;
        }
        if (selectedX >= 8 || selectedY >= 8) {
            selectedX = -1;
            selectedY = -1;
        }
        // special case cause x in (-1, 0) will mean selectedX truncated to 0
        if ((selectedX == 0 || selectedY == 0) && (x < minX || y < minY)) {
            selectedX = -1;
            selectedY = -1;
        }
        selectX = selectedX;
        selectY = selectedY;
    }

    public void render(float delta, SpriteBatch batch) {
        // TODO for tomorrow! figure out this nonsense!
        int minX = 500;
        int y = 150;

        if (selectX >= 0 && selectY >= 0) {
            batch.draw(selectedGem, minX + (selectX * SLOT_WIDTH), y + (selectY * SLOT_WIDTH), 75, 75);
        }

        for (Iterator<Array<Gem>> iter = gems.iterator() ; iter.hasNext();) {
            int x = minX;
            for (Iterator<Gem> iter2 = iter.next().iterator() ; iter2.hasNext();) {
                switch (iter2.next().getColor()) {
                    case WHITE:
                        batch.draw(whiteGem, x, y, 75, 75);
                        break;
                    case PURPLE:
                        batch.draw(purpleGem, x, y, 75, 75);
                        break;
                    case YELLOW:
                        batch.draw(yellowGem, x, y, 75, 75);
                        break;
                    case BLUE:
                        batch.draw(blueGem, x, y, 75, 75);
                        break;
                    case GREEN:
                        batch.draw(greenGem, x, y, 75, 75);
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
