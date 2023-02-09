package jadeddelta.genjwld.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;

public class BoardProperties {
    private int boardX;
    private int boardY;
    private int slotDiameter;

    private int height;
    private int width;
    private GridPoint2 center;
    private GridPoint2 bottomLeft;
    private GridPoint2 topRight;

    public BoardProperties() {
        this.boardX = 8;
        this.boardY = 8;
        this.slotDiameter = 75;
        this.width = boardX * slotDiameter;
        this.height = boardY * slotDiameter;
        this.center = new GridPoint2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.bottomLeft = new GridPoint2(center.x - width/2, center.y - height/2);
        this.topRight = new GridPoint2(center.x + width/2, center.y + height/2);
    }

    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public int getSlotDiameter() {
        return slotDiameter;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public GridPoint2 getBottomLeftCorner() {
        return bottomLeft;
    }

    public GridPoint2 getTopRightCorner() {
        return topRight;
    }

    public GridPoint2 getCenter() {
        return center;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Board Properties: ");
        String newLine = System.lineSeparator();
        ret.append(newLine);
        ret.append("Center: ").append(center).append(newLine);
        ret.append("BLCorner: ").append(bottomLeft).append(newLine);
        ret.append("Width: ").append(width).append(newLine);
        ret.append("Height: ").append(height).append(newLine);
        return ret.toString();
    }
}
