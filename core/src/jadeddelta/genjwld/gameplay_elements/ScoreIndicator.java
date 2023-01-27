package jadeddelta.genjwld.gameplay_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private Assets manager;

    private GridPoint2 min, max;
    private boolean doneLevelAnimation = false;

    public ScoreIndicator(int boardX, int boardY, Assets manager) {
        this.score = 0;
        this.level = 0;
        this.combo = 1;
        this.scoreCap = 2500;

        this.manager = manager;

        this.min = new GridPoint2(boardX - 200 - 150, boardY);
    }

    public void render(float delta, SpriteBatch batch) {
        // load portion of texture from sprite max x -> sprite min x + (maxX-minX) * (currentScore / scoreCap)
        // render is called irrespective of board state
        if (!doneLevelAnimation) {
            // TODO: cool animation stuff!
        }

        int scoreHeight = (int) (600 * ((double) score / scoreCap));
        batch.draw(manager.getScoreFill(), min.x, min.y, 0, 0, 150, scoreHeight);
        batch.draw(manager.getScoreBar(), min.x, min.y, 150, 600);
        manager.getScoreText().draw(batch, "Score: " + aggregateScore, min.x, min.x + 600 + 100 + 5);
        manager.getScoreText().draw(batch, "Combo: " + combo, min.x, min.y + 600 + 75);
        manager.getScoreText().draw(batch, "Level: " + level, min.x, min.y + 600 + 50 - 5);
    }

    public void checkLevelUp() {
        if (score >= scoreCap) {
            score -= scoreCap;
            scoreCap *= 2.5;
            level++;
            doneLevelAnimation = true;
        }
    }

    public void updateScore(int addition) {
        score += (addition * combo);
        aggregateScore += (addition * combo);
        checkLevelUp();
    }

    public void updateCombo(boolean broke) {
        if (broke)
            combo = 1;
        if (combo >= 10)
            return;
        combo++;
    }
}
