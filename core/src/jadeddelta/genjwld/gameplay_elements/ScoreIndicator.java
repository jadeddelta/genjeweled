package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.math.GridPoint2;
import jadeddelta.genjwld.data.Assets;

public class ScoreIndicator {
    // 150 width, 600 height spaced 200 away from board
    private int score;
    private int scoreCap;
    private int aggregateScore;
    private int level;
    private int combo;
    private int comboProgress;
    private int threshold;

    private Assets manager;

    private GridPoint2 min;
    private int width, height;

    public ScoreIndicator(int boardX, int boardY, int spacing) {
        this.score = 0;
        this.level = 1;
        this.combo = 1;
        this.comboProgress = 0;
        this.scoreCap = 2500;
        this.threshold = 4;

        this.width = 150;
        this.height = 600;
        this.min = new GridPoint2(boardX - width - spacing, boardY);
    }

    public void checkLevelUp() {
        if (score >= scoreCap) {
            score -= scoreCap;
            scoreCap *= 2.5;
            level++;
        }
    }

    public void updateScore(int addition) {
        score += (addition * combo);
        aggregateScore += (addition * combo);
        checkLevelUp();
    }

    public void updateCombo(boolean broke) {
        if (broke) {
            if (comboProgress > 0) {
                comboProgress = 0;
                return;
            }
            if (combo == 1)
                return;
            combo--;
            updateThreshold();
        }
        else {
            comboProgress++;
            if (comboProgress == threshold) {
                combo++;
                comboProgress = 0;
                updateThreshold();
            }
        }
    }

    private void updateThreshold() {
        switch (combo) {
            case 1:
                threshold = 4;
                break;
            case 2:
            case 3:
                threshold = 6;
                break;
            case 4:
            case 5:
                threshold = 8;
                break;
            case 6:
            case 7:
                threshold = 12;
                break;
            case 8:
            case 9:
                threshold = 16;
                break;
            case 10:
                threshold = 32;
                break;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getAggregateScore() {
        return aggregateScore;
    }

    public int getCombo() {
        return combo;
    }

    public int getComboProgress() {
        return comboProgress;
    }

    public int getThreshold() {
        return threshold;
    }

    public GridPoint2 getMin() {
        return min;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScoreHeight() {
        System.out.println(height);
        System.out.println(score);
        System.out.println(scoreCap);
        System.out.println((int) (height * ((double) score / scoreCap)) + "a");
        return (int) (height * ((double) score / scoreCap));
    }
}
