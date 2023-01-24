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
    private int swapX, swapY;

    // TODO: eventually turn this into customizable board logic
    private final int BOARD_X_LENGTH = 8;
    private final int BOARD_Y_LENGTH = 8;
    private final int SLOT_WIDTH = 75;

    private final int minX, minY;
    private final int maxX, maxY;

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

        this.selectX = -1;
        this.selectY = -1;
        this.swapX = -1;
        this.swapY = -1;

        this.minX = 500;
        this.minY = 150;
        this.maxX = minX + (BOARD_X_LENGTH * SLOT_WIDTH);
        this.maxY = minY + (BOARD_Y_LENGTH * SLOT_WIDTH);
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
        // select and swap have the same logic

        if ((x > minX && y > minY)
                && (x < maxX && y < maxY)) {
            int selectedX = (x - minX) / SLOT_WIDTH;
            int selectedY = (y - minY) / SLOT_WIDTH;

            if (selectX >= 0 && selectY >= 0) {
                swapX = selectedX;
                swapY = selectedY;
                return;
            }

            selectX = selectedX;
            selectY = selectedY;
            return;
        }

        selectX = -1;
        selectY = -1;
        swapX = -1;
        swapY = -1;
    }

    public void swapGem() {
        Gem selectedGem = gems.get(selectY).get(selectX);
        Gem swapGem = gems.get(swapY).get(swapX);

        gems.get(swapY).set(swapX, selectedGem);
        gems.get(selectY).set(selectX, swapGem);

        selectX = -1;
        selectY = -1;
        swapX = -1;
        swapY = -1;
    }

    public void render(float delta, SpriteBatch batch) {
        int x = minX;
        int y = minY;

        if (selectX >= 0 && selectY >= 0) {
            batch.draw(selectedGem, minX + (selectX * SLOT_WIDTH), minY + (selectY * SLOT_WIDTH), 75, 75);
        }
        if (swapX >= 0 && swapY >= 0) {
            swapGem();
        }

        for (Iterator<Array<Gem>> iter = gems.iterator() ; iter.hasNext();) {
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
            x = minX;
        }

    }
}
