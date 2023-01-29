package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StringBuilder;

public class Match {

    private Array<GridPoint2> gemSlots;

    private boolean valid;
    private MatchType matchType;
    private GemEnhancement gemEnhancement;
    private GemColor gemColor;

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

    /**
     * Adds a slot to the <code>Match</code>, used in building and validating itself
     * iteratively.
     * @param slot the slot that is to be added.
     * @return a boolean that describes if the match is a valid match (>=3 size)
     */
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

    /**
     * Adds a slot to the <code>Match</code> without checking for its size,
     * in order to create T-Shaped matches.
     * @param slot - the slot that is to be added.
     */
    private void addWithoutCheck(GridPoint2 slot) {
        gemSlots.add(slot);
    }

    /**
     * Returns where the gem with the <code>GemEnhancement</code> of
     * the <code>Match</code> will be created
     * @return the point on a board where the special gem will be created
     */
    public GridPoint2 specialPosition() {
        if (matchType == MatchType.TSHAPED) {
            return gemSlots.get(0);
        }
        else {
            switch (gemSlots.size) {
                case 4:
                    return gemSlots.get(1);
                case 5:
                case 6:
                    return gemSlots.get(2);
                default:
                    return gemSlots.get((gemSlots.size) / 2);
            }
        }
    }

    //FIXME: detects t-shapes, and any blob-like structures won't work
    /**
     * Processes a set of matches with potentially intersecting matches, condensing
     * them into tshaped matches.
     * @param matches a set of matches to be processed
     * @return an <code>ObjectSet</code> with unified tshaped matches
     */
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

    /**
     * Creates a TShaped match with two intersecting matches.
     * @param m the first match
     * @param n the second match
     * @return a TShaped match
     */
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
        ret.setGemColor(m.getGemColor());
        for (GridPoint2 p : combinedSlots)
            ret.addWithoutCheck(p);

        return ret;
    }

    /**
     * Finds the position where two matches intersect.
     * @param other the other match to be checked
     * @return the point where the two matches intersect
     */
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

    public void setGemColor(GemColor color) {
        this.gemColor = color;
    }

    public GemColor getGemColor() {
        return gemColor;
    }

    public GemEnhancement getGemEnhancement() {
        return gemEnhancement;
    }

    public int getScore() {
        return gemSlots.size * BASE_SCORE;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(16);
        s.append(gemColor + " " + gemEnhancement + " " + matchType + " [");
        for (GridPoint2 point : gemSlots) {
            s.append(point.toString() + " ");
        }
        s.append("]");
        return s.toString();
    }
}
