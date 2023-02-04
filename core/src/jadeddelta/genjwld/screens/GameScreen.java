package jadeddelta.genjwld.screens;

import com.badlogic.gdx.Screen;
import jadeddelta.genjwld.GemInputProcessor;
import jadeddelta.genjwld.GenjeweledGame;
import jadeddelta.genjwld.gameplay_elements.Announcer;
import jadeddelta.genjwld.gameplay_elements.Board;
import jadeddelta.genjwld.gameplay_elements.ScoreIndicator;

public abstract class GameScreen implements Screen {
    private final GenjeweledGame game;
    private final Board board;
    private final Announcer announcer;
    private final ScoreIndicator scoreIndicator;
    private final GemInputProcessor gemInputProcessor;

    //placeholder
    public GameScreen(final GenjeweledGame game) {
        this.game = game;
        this.board = Board.defaultBoard(null);
        this.announcer = null;
        this.scoreIndicator = new ScoreIndicator(0, 0, null);
        this.gemInputProcessor = new GemInputProcessor(null);
    }

}
