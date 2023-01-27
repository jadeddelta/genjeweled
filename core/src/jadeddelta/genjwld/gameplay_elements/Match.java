package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StringBuilder;

import java.awt.geom.Point2D;

public class Match {

    private Array<GridPoint2> gemSlots;

    private boolean valid;
    private MatchType matchType;
    private GemEnhancement gemEnhancement;

    private int BASE_SCORE = 50;

    public Match(MatchType matchType) {
        gemSlots = new Array<>(true, 16);
        this.matchType = matchType;
        this.gemEnhancement = GemEnhancement.NONE;
    }

    public Match(MatchType matchType, GemEnhancement gemEnhancement) {
        gemSlots = new Array<>(true, 16);
        this.matchType = matchType;
        this.gemEnhancement = gemEnhancement;
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

    private void addWithoutCheck(GridPoint2 slot) {
        gemSlots.add(slot);
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

    // WARNING: WILL NOT DETECT BLOBS!
    public static ObjectSet<Match> processTShapes(ObjectSet<Match> matches) {
        ObjectSet<Match> horizMatches = new ObjectSet<>(8);
        ObjectSet<Match> vertMatches = new ObjectSet<>(8);
        ObjectSet<Match> finalMatches = new ObjectSet<>(16);

        for (Match m : matches) {
            if (m.getMatchType() == MatchType.HORIZONTAL)
                horizMatches.add(m);
            else
                vertMatches.add(m);
        }

        for (Match m : horizMatches) {
            for (Match n: vertMatches) {
                GridPoint2 intersection = m.intersectsOther(n);
                System.out.println(intersection);
                if (intersection == null)
                    continue;

                finalMatches.add(createTMatch(m, n));
                horizMatches.remove(m);
                vertMatches.remove(n);
                break;
            }
        }

        for (Match m : horizMatches)
            finalMatches.add(m);
        for (Match n : vertMatches)
            finalMatches.add(n);

        return finalMatches;
    }

    public static Match createTMatch(Match m, Match n) {
        Array<GridPoint2> mSlots = m.getGemSlots();
        Array<GridPoint2> nSlots = n.getGemSlots();
        GemEnhancement enhancement = GemEnhancement.FLAME;
        ObjectSet<GridPoint2> combinedSlots = new ObjectSet<>(16);
        for (GridPoint2 p : mSlots)
            combinedSlots.add(p);
        for (GridPoint2 p : nSlots)
            combinedSlots.add(p);

        if (mSlots.size == 5 || nSlots.size == 5)
            enhancement = GemEnhancement.LIGHTNING;

        Match ret = new Match(MatchType.TSHAPED, enhancement);
        for (GridPoint2 p : combinedSlots)
            ret.addWithoutCheck(p);

        return ret;
    }

    public GridPoint2 intersectsOther(Match other) {
        Array<GridPoint2> curr = gemSlots;
        Array<GridPoint2> oth = other.getGemSlots();

        for (int i = 0; i < curr.size; i++) {
            for (int j = 0; j < oth.size; j++) {
                if (curr.get(i).equals(oth.get(j)))
                    return curr.get(i);
            }
        }
        return null;
    }

    public Array<GridPoint2> getGemSlots() {
        return gemSlots;
    }

    public MatchType getMatchType() {
        return this.matchType;
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
