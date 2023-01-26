package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

import java.awt.*;
import java.util.Iterator;

public class Board {

    private Array<Array<Gem>> gems;

    private Texture redGem, blueGem, greenGem, purpleGem, whiteGem, yellowGem, orangeGem;
    private Texture selectedGem;

    private GridPoint2 selectSlot, swapSlot;

    // TODO: eventually turn this into customizable board logic
    // thoughts- make this into a "preferences" or whatnot, map?
    private final int BOARD_X_LENGTH = 8;
    private final int BOARD_Y_LENGTH = 8;
    private final int SLOT_WIDTH = 75;

    private final GridPoint2 min, center, max;

    public final ScoreIndicator scoreIndicator;

    public Board(Array<Array<Gem>> gems, GridPoint2 center) {
        this.gems = gems;
        this.redGem = new Texture(Gdx.files.internal("gems/redgem.png"));
        this.blueGem = new Texture(Gdx.files.internal("gems/bluegem.png"));
        this.greenGem = new Texture(Gdx.files.internal("gems/greengem.png"));
        this.purpleGem = new Texture(Gdx.files.internal("gems/purplegem.png"));
        this.whiteGem = new Texture(Gdx.files.internal("gems/whitegem.png"));
        this.yellowGem = new Texture(Gdx.files.internal("gems/yellowgem.png"));
        this.orangeGem = new Texture(Gdx.files.internal("gems/orangegem.png"));
        this.selectedGem = new Texture(Gdx.files.internal("effects/selected.png"));

        this.center = center;
        int width = BOARD_X_LENGTH * SLOT_WIDTH;
        int height = BOARD_Y_LENGTH * SLOT_WIDTH;
        this.min = new GridPoint2(center.x - width/2, center.y - height/2);
        this.max = new GridPoint2(center.x + width/2, center.y + width/2);

        selectSlot = new GridPoint2(-1, -1);
        swapSlot = new GridPoint2(-1, -1);

        this.scoreIndicator = new ScoreIndicator(min.x, min.y);
    }

    /**
     * Generates the default 8x8 board used for most games.
     * @return a default board with randomized gems
     */
    public static Board defaultBoard() {
        Array<Array<Gem>> gems = new Array<>(true, 8);
        for (int i = 0; i < 8; i++) {
            Array<Gem> gemArray = new Array<>(true, 8);
            for (int j = 0; j < 8; j++) {
                gemArray.add(Gem.randomGem());
            }
            gems.add(gemArray);
        }
        return new Board(gems, new GridPoint2(1600/2, 900/2));
    }

    public void selectGem(int x, int y) {
        if ((x > min.x && y > min.y)
                && (x < max.x && y < max.y)) {
            int selectedX = (x - min.x) / SLOT_WIDTH;
            int selectedY = (y - min.y) / SLOT_WIDTH;

            if (selectSlot.x == selectedX && selectSlot.y == selectedY) {
                swapSlot.x = -1;
                swapSlot.y = -1;
                return;
            }

            if (selectSlot.x >= 0 && selectSlot.y >= 0) {
                swapSlot.x = selectedX;
                swapSlot.y = selectedY;
                return;
            }

            selectSlot.x = selectedX;
            selectSlot.y = selectedY;
            return;
        }

        selectSlot.x = -1;
        selectSlot.y = -1;
        swapSlot.x = -1;
        swapSlot.y = -1;
    }

    public void swapGem() {
        Gem selectedGem = gems.get(selectSlot.y).get(selectSlot.x);
        Gem swapGem = gems.get(swapSlot.y).get(swapSlot.x);

        gems.get(swapSlot.y).set(swapSlot.x, selectedGem);
        gems.get(selectSlot.y).set(selectSlot.x, swapGem);

        System.out.println("Select   Swap");
        System.out.println(selectSlot + "   " + swapSlot);
        System.out.println(selectedGem.getColor() + "  " + swapGem.getColor());

        selectSlot.x = -1;
        selectSlot.y = -1;
        swapSlot.x = -1;
        swapSlot.y = -1;
    }

    public ObjectSet<Match> checkBoard() {
        // must detect 3s, 4s + return gem creation slot, 5s + slot, 6s + slot, T + slot
        // COWABUNGAAAAAAAAAAAAA
        // today i learned: point class exists LOL :3
        ObjectSet<Match> matches = new ObjectSet<>();

        GemColor currentColor;
        Match possibleMatch = new Match(MatchType.HORIZONTAL);

        for (int i = 0; i < 8; i++) {
            currentColor = gems.get(i).get(0).getColor();
            for (int j = 0; j < 8; j++) {
                GemColor check = gems.get(i).get(j).getColor();
                if (check == currentColor) {
                    if (possibleMatch.addSlot(new GridPoint2(j, i)))
                        matches.add(possibleMatch);
                }
                else {
                    possibleMatch = new Match(MatchType.HORIZONTAL);
                    possibleMatch.addSlot(new GridPoint2(j, i));
                    currentColor = check;
                }
            }
            possibleMatch = new Match(MatchType.HORIZONTAL);
        }

        possibleMatch = new Match(MatchType.VERTICAL);
        for (int i = 0; i < 8; i++) {
            currentColor = gems.get(0).get(i).getColor();
            for (int j = 0; j < 8; j++) {
                GemColor check = gems.get(j).get(i).getColor();
                if (check == currentColor) {
                    if (possibleMatch.addSlot(new GridPoint2(i, j)))
                        matches.add(possibleMatch);
                }
                else {
                    possibleMatch = new Match(MatchType.VERTICAL);
                    possibleMatch.addSlot(new GridPoint2(i, j));
                    currentColor = check;
                }
            }
            possibleMatch = new Match(MatchType.VERTICAL);
        }

        return matches;
    }

    public void render(float delta, SpriteBatch batch) {
        int x = min.x;
        int y = min.y;

        if (selectSlot.x >= 0 && selectSlot.y >= 0) {
            batch.disableBlending();
            batch.draw(selectedGem, min.x + (selectSlot.x * SLOT_WIDTH), min.y + (selectSlot.y * SLOT_WIDTH), 75, 75);
            batch.enableBlending();
        }
        if (swapSlot.x >= 0 && swapSlot.y >= 0) {
            swapGem();
            ObjectSet<Match> matches = checkBoard();
            matches = Match.processTShapes(matches);

            if (matches.size > 0) {
                for (Match m : matches) {
                    scoreIndicator.updateScore(m.getScore());
                }
            }
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
                        batch.draw(orangeGem, x, y, 75, 75);
                        break;
                    case RED:
                        batch.draw(redGem, x, y, 75, 75);
                        break;
                    case SPECIAL:
                        batch.draw(whiteGem, x + 20, y + 20, 35, 35);
                        break;
                }
                x += SLOT_WIDTH;
            }
            y += SLOT_WIDTH;
            x = min.x;
        }

    }
}
