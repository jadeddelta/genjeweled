package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import java.awt.geom.Point2D;

public class Match {

    private Array<GridPoint2> gemSlots;

    private boolean valid;
    private MatchType matchType;
    private GemEnhancement gemEnhancement;

    private int BASE_SCORE = 50;
    private int score;

    public Match(MatchType matchType) {
        gemSlots = new Array<>(true, 16);
        this.matchType = matchType;
        this.gemEnhancement = GemEnhancement.NONE;
        this.score = 0;
    }

    public boolean addSlot(GridPoint2 slot) {
        gemSlots.add(slot);
        if (gemSlots.size >= 3)
            valid = true;
        if (gemSlots.size == 4)
            gemEnhancement = GemEnhancement.FLAME;
        if (gemSlots.size == 5)
            gemEnhancement = GemEnhancement.LIGHTNING;

        return valid;
    }

    public GridPoint2 specialPosition() {
        if (matchType == MatchType.TSHAPED) {
            // find intersection
            return null;
        }
        else {
            // switch side -> determine position
            return null;
        }
    }

    public int getScore() {
        return gemSlots.size * BASE_SCORE;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(16);
        s.append(gemEnhancement + " " + matchType + " [");
        for (GridPoint2 point : gemSlots) {
            s.append(point.toString() + " ");
        }
        s.append("]");
        return s.toString();
    }
}
