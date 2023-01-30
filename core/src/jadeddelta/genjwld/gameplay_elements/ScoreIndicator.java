package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private GridPoint2 min, max;

    public ScoreIndicator(int boardX, int boardY, Assets manager) {
        this.score = 0;
        this.level = 0;
        this.combo = 1;
        this.comboProgress = 0;
        this.scoreCap = 2500;
        this.threshold = 4;

        this.manager = manager;

        this.min = new GridPoint2(boardX - 200 - 150, boardY);
    }

    public void render(float delta, SpriteBatch batch) {
        // load portion of texture from sprite max x -> sprite min x + (maxX-minX) * (currentScore / scoreCap)
        // render is called irrespective of board state

        int scoreHeight = (int) (600 * ((double) score / scoreCap));
        batch.draw(manager.getScoreFill(), min.x, min.y, 0, 0, 150, scoreHeight);
        batch.draw(manager.getScoreBar(), min.x, min.y, 150, 600);
        manager.getScoreText().draw(batch, "Score: " + aggregateScore, min.x, min.x + 600 + 100 + 5);
        manager.getScoreText().draw(
                batch,
                "Combo: " + combo + " | " + comboProgress + " / " + threshold,
                min.x,
                min.y + 600 + 75);
        manager.getScoreText().draw(batch, "Level: " + level, min.x, min.y + 600 + 50 - 5);
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
            if (comboProgress >= 0) {
                comboProgress = 0;
                return;
            }
            if (combo == 1)
                return;
            combo--;
        }
        else {
            comboProgress++;
            if (comboProgress == threshold) {
                combo++;
                comboProgress = 0;
            }
        }
        updateThreshold();
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
}
