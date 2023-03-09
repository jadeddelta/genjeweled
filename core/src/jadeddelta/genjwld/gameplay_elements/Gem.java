package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Gem {

    private final GemColor color;
    private final GemEnhancement enhancement;
    private final static Array<GemColor> GEM_COLORS =
            new Array<>(new GemColor[] {GemColor.RED, GemColor.ORANGE, GemColor.YELLOW,
            GemColor.GREEN, GemColor.BLUE, GemColor.PURPLE, GemColor.WHITE});

    private GridPoint2 originalPosition = new GridPoint2(-1, -1);
    private GridPoint2 currentPosition = new GridPoint2(-1, -1);
    private GridPoint2 slotTravel = new GridPoint2(-1, -1);
    private boolean inTransit = false;

    public Gem(GemColor color, GemEnhancement enhancement) {
        this.color = color;
        this.enhancement = enhancement;
    }

    /**
     * Generates a random, regular gem for use in regular play.
     * @return a gem with no enhancement and a valid color
     */
    public static Gem randomGem() {
        GemColor color = GEM_COLORS.random();
        return new Gem(color, GemEnhancement.NONE);
    }

    public GemColor getColor() {
        return color;
    }

    public GemEnhancement getEnhancement() {
        return enhancement;
    }

    public void putInMotion(GridPoint2 curPos, GridPoint2 finPos) {
        inTransit = true;
        originalPosition = new GridPoint2(curPos.x, curPos.y);
        slotTravel = finPos;
        currentPosition = new GridPoint2(curPos.x, curPos.y);
    }

    public boolean move(float delta) {
        if (!inTransit)
            return false;

        float speed = 1f * delta;

        float xShift = (originalPosition.x - slotTravel.x) * speed;
        float yShift = (originalPosition.y - slotTravel.y) * speed;
        System.out.println(color);
        System.out.println(xShift);
        System.out.println(yShift);

        // down, yshift pos / left, xshift pos
        boolean passed = false;
        if (xShift > 0) { // passing left
            passed = currentPosition.x - xShift < slotTravel.x;
        }
        if (xShift < 0) {
            passed = currentPosition.x - xShift > slotTravel.x;
        }
        if (yShift > 0) {
            passed = currentPosition.y - yShift < slotTravel.y;
        }
        if (yShift < 0) {
            passed = currentPosition.y - yShift > slotTravel.x;
        }

        if (slotTravel.equals(currentPosition) || passed) {
            inTransit = false;
            resetPositions();
            return true;
        }
        currentPosition.x -= xShift;
        currentPosition.y -= yShift;
        return false;
    }

    private void resetPositions() {
        originalPosition.x = -1;
        originalPosition.y = -1;
        currentPosition.x = -1;
        currentPosition.y = -1;
        slotTravel.x = -1;
        slotTravel.y = -1;
    }

    public GridPoint2 getCurrentPosition() {
        return currentPosition;
    }

    public boolean isInTransit() {
        return inTransit;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Gem))
            return false;
        return ((Gem) o).getColor() == this.getColor()
                && ((Gem) o).getEnhancement() == this.getEnhancement();
    }

    @Override
    public Gem clone() {
        return new Gem(this.getColor(), this.getEnhancement());
    }

    @Override
    public String toString() {
        return color + " Gem";
    }
}
