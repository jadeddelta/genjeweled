package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import jadeddelta.genjwld.data.BoardProperties;

public class Board {

    private Array<Array<Gem>> gems;

    private GridPoint2 selectSlot, swapSlot;

    private BoardProperties props;

    private Array<GridPoint2> smokedGems = new Array<>();
    private long smokeTime = 0;

    private long lastMatchTime;

    public Board(Array<Array<Gem>> gems, BoardProperties props) {
        this.gems = gems;

        this.props = props;

        selectSlot = new GridPoint2(-1, -1);
        swapSlot = new GridPoint2(-1, -1);

        this.lastMatchTime = 0;
    }

    /**
     * Generates the default 8x8 board used for most games.
     * @return a default board with randomized gems with no possible matches
     */
    public static Board defaultBoard() {
        while (true) {
            Array<Array<Gem>> gems = new Array<>(true, 8);
            Board b;
            for (int i = 0; i < 8; i++) {
                Array<Gem> gemArray = new Array<>(true, 8);
                for (int j = 0; j < 8; j++) {
                    gemArray.add(Gem.randomGem());
                }
                gems.add(gemArray);
            }
            b = new Board(gems, new BoardProperties());
            if (b.checkBoard().size == 0)
                return b;
        }
    }

    /**
     * Selects the gem on the board given coordinates from <code>GemInputProcessor</code>,
     * differentiating between the gem to be selected and the gem to be swapped with.
     * @param x the x coordinate of the mouse click
     * @param y the y coordinate of the mouse click
     */
    public void selectGem(int x, int y) {
        GridPoint2 min = props.getBottomLeftCorner();
        GridPoint2 max = props.getTopRightCorner();
        if ((x > min.x && y > min.y)
                && (x < max.x && y < max.y)) {
            int selectedX = (x - min.x) / props.getSlotDiameter();
            int selectedY = (y - min.y) / props.getSlotDiameter();

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

    /**
     * Swaps the selected gem with the gem to be swapped, then resets both selectors.
     */
    public void swapGem() {
        Gem selectedGem = gems.get(selectSlot.y).get(selectSlot.x);
        Gem swapGem = gems.get(swapSlot.y).get(swapSlot.x);

        gems.get(swapSlot.y).set(swapSlot.x, selectedGem);
        gems.get(selectSlot.y).set(selectSlot.x, swapGem);

        selectSlot.x = -1;
        selectSlot.y = -1;
        swapSlot.x = -1;
        swapSlot.y = -1;
    }

    /**
     * Checks the board if any matches exist.
     * @return an <code>ObjectSet</code> with all matches, including condensed TShape matches.
     */
    public ObjectSet<Match> checkBoard() {
        ObjectSet<Match> matches = new ObjectSet<>();

        GemColor currentColor;
        Match possibleMatch = new Match(MatchType.HORIZONTAL);

        for (int i = 0; i < props.getBoardY(); i++) {
            currentColor = gems.get(i).get(0).getColor();
            for (int j = 0; j < props.getBoardX(); j++) {
                GemColor check = gems.get(i).get(j).getColor();
                if (check == currentColor) {
                    possibleMatch.setGemColor(currentColor);
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
        for (int i = 0; i < props.getBoardY(); i++) {
            currentColor = gems.get(0).get(i).getColor();
            for (int j = 0; j < props.getBoardX(); j++) {
                GemColor check = gems.get(j).get(i).getColor();
                if (check == currentColor && check != GemColor.NONE) {
                    possibleMatch.setGemColor(currentColor);
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

        matches = Match.processTShapes(matches);
        return matches;
    }

    /**
     * Removes matched gems and replaces them with blank gems.
     * @param matches a set of matches describing the positions of gems that will be removed
     */
    public void removeMatchedGems(ObjectSet<Match> matches) {
        for (Match m : matches) {
            for (GridPoint2 p : m.getGemSlots()) {
                gems.get(p.y).set(p.x, new Gem(GemColor.NONE, GemEnhancement.NONE));
            }
            if (m.getGemEnhancement() != GemEnhancement.NONE) {
                GridPoint2 slot = m.specialPosition();
                gems.get(slot.y).set(slot.x, new Gem(m.getGemColor(), m.getGemEnhancement()));
            }
        }
        update();
    }

    /**
     * Updates the board by letting gems fall down to the bottom whilst creating
     * new ones.
     */
    public void update() {
        int replaced = 1;
        int generated = 1;

        while (replaced > 0 && generated > 0) {
            replaced = 0;
            generated = 0;
            for (int i = 0; i < props.getBoardY() - 1; i++) {
                for (int j = 0; j < props.getBoardX(); j++) {
                    Gem bottom = gems.get(i).get(j).clone();
                    if (bottom.getColor() == GemColor.NONE) {
                        Gem top = gems.get(i+1).get(j).clone();

                        gems.get(i).set(j, top);
                        gems.get(i+1).set(j, bottom);
                        replaced++;
                    }
                }
            }
            for (int k = 0; k < props.getBoardX(); k++) {
                if (gems.get(props.getBoardY() - 1).get(k).getColor() == GemColor.NONE) {
                    gems.get(props.getBoardY() - 1).set(k, Gem.randomGem());
                    generated++;
                }
            }
        }
    }

    public Array<Array<Gem>> getGems() {
        return gems;
    }

    public BoardProperties getProps() {
        return props;
    }

    public GridPoint2 getSelectSlot() {
        return selectSlot;
    }

    public boolean isSelected() {
        return (selectSlot.x >= 0 && selectSlot.y >= 0);
    }

    public boolean isSwappable() {
        return (swapSlot.x >= 0 && selectSlot.y >= 0);
    }
}
