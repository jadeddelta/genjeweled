package jadeddelta.genjwld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import jadeddelta.genjwld.GemInputProcessor;
import jadeddelta.genjwld.GenjeweledGame;
import jadeddelta.genjwld.data.BoardProperties;
import jadeddelta.genjwld.gameplay_elements.*;

import java.util.Iterator;

public abstract class GameScreen implements Screen {
    private final GenjeweledGame game;
    public final Board board;
    private final Announcer announcer;
    private final ScoreIndicator scoreIndicator;
    private final GemInputProcessor gemInputProcessor;

    private Texture background;

    private Texture gemSelect;

    //TODO: remove for gemfall prep
    private long lastMatchTime = 0;

    //placeholder
    public GameScreen(final GenjeweledGame game) {
        this.game = game;
        this.board = Board.defaultBoard();
        this.announcer = null;
        GridPoint2 boardBL = board.getProps().getBottomLeftCorner();
        this.scoreIndicator = new ScoreIndicator(boardBL.x, boardBL.y, 200);
        this.gemInputProcessor = new GemInputProcessor(this);
        Gdx.input.setInputProcessor(gemInputProcessor);
        background = game.manager.getBg(1);
        gemSelect = game.manager.getGemSelected();
    }

    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.camera.update();
        game.batch.setTransformMatrix(game.camera.view);
        game.batch.setProjectionMatrix(game.camera.projection);

        game.batch.begin();
        renderBg();
        renderBoard(delta);
        renderIndicator();
        renderAnnouncer();
        game.batch.end();
    }

    private void renderBg() {
        int bgLevel = (scoreIndicator.getLevel() % 5) + 1;
        background = game.manager.getBg(bgLevel);
        game.batch.disableBlending();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.enableBlending();
    }

    private void renderBoard(float delta) {
        BoardProperties props = board.getProps();
        GridPoint2 min = props.getBottomLeftCorner();
        if (board.isSelected()) {
            game.batch.draw(
                    gemSelect,
                    min.x + (board.getSelectSlot().x * props.getSlotDiameter()),
                    min.y + (board.getSelectSlot().y * props.getSlotDiameter()),
                    props.getSlotDiameter(),
                    props.getSlotDiameter());
        }

        // TODO: get these out of render method and into GIP / Board
        if (board.isSwappable()) {
            board.swapGem();
            ObjectSet<Match> matches = board.checkBoard();

            boolean broke = (matches.size == 0);
            handleMatches(matches);
            scoreIndicator.updateCombo(broke);
            lastMatchTime = TimeUtils.nanoTime();
        }

        if (lastMatchTime != 0 && (lastMatchTime + 1000000000) <= TimeUtils.nanoTime()) {
            lastMatchTime = 0;
            handleMatches(board.checkBoard());
        }

        int x = min.x;
        int y = min.y;
        Array<Array<Gem>> gems = board.getGems();
        for (Iterator<Array<Gem>> iter = gems.iterator(); iter.hasNext();) {
            for (Gem gem : iter.next()) {
                int gemX = x;
                int gemY = y;
                if (gem.isInTransit()) {
                    GridPoint2 gemPos = gem.getCurrentPosition();
                    gemX = gemPos.x;
                    gemY = gemPos.y;
                }

                Texture gemTexture = game.manager.getGemTexture(gem.getColor());
                game.batch.draw(gemTexture, gemX, gemY, props.getSlotDiameter(), props.getSlotDiameter());

                Texture gemEffect = game.manager.getGemEffects(gem.getEnhancement());
                if (gemEffect != null)
                    game.batch.draw(gemEffect, gemX, gemY, props.getSlotDiameter(), props.getSlotDiameter());
                x += props.getSlotDiameter();
                if (gem.move(delta)) {
                    handleMatches(board.checkBoard());
                }
            }
            y += props.getSlotDiameter();
            x = min.x;
        }
    }

    // TODO: transition from individual to IndicatorProps
    private void renderIndicator() {
        Texture scoreBar = game.manager.getScoreBar();
        Texture scoreFill = game.manager.getScoreFill();
        GridPoint2 min = scoreIndicator.getMin();

        game.batch.draw(scoreFill, min.x, min.y, 0, 0, scoreIndicator.getWidth(), scoreIndicator.getScoreHeight());
        game.batch.draw(scoreBar, min.x, min.y, scoreIndicator.getWidth(), scoreIndicator.getHeight());

        game.manager.getScoreText().draw(game.batch, "Score: " + scoreIndicator.getAggregateScore(), min.x, min.x + 600 + 100 + 5);
        game.manager.getScoreText().draw(
                game.batch,
                "Combo: " + scoreIndicator.getCombo() + " | " + scoreIndicator.getComboProgress() + " / " + scoreIndicator.getThreshold(),
                min.x,
                min.y + scoreIndicator.getHeight() + 75);
        game.manager.getScoreText().draw(game.batch, "Level: " + scoreIndicator.getLevel(), min.x, min.y + scoreIndicator.getHeight() + 50 - 5);
    }

    private void renderAnnouncer() {

    }

    private void handleMatches(ObjectSet<Match> matches) {
        if (matches.size > 0) {
            for (Match m : matches)
                scoreIndicator.updateScore(m.getScore());
            board.removeMatchedGems(matches);
        }
    }

    public Vector3 unproject(Vector3 v) {
        return game.camera.unproject(v);
    }
}
