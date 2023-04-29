import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;

public class Block {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color baseColor;
    private boolean isWall;
    private boolean isBumperLeft;
    private boolean isBumperRight;
    private boolean isLockedDoor;
    private boolean isOpenedDoor;
    private boolean isEndPoint;
    private Point endPoint;
    private boolean isGround;
    private boolean isKey;

    public Block(int x, int y, int width, int height, Color baseColor, boolean isWall, boolean isBumperLeft, boolean isBumperRight,boolean isLockedDoor, boolean isOpenedDoor,boolean isGround,boolean isEndPoint, Point endPoint, boolean isKey) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if (isWall) {
            this.baseColor = Color.GRAY;
        }
        else if (isBumperLeft || isBumperRight || isEndPoint || isKey) {
            this.baseColor = Color.BLACK;
        }
        else if (isOpenedDoor) {
            this.baseColor = Color.GREEN;
        }
        else if (isLockedDoor) {
            this.baseColor = Color.darkGray;
        }
        else {
            this.baseColor = Color.lightGray;
        }
        this.isWall = isWall;
        this.isBumperLeft = isBumperLeft;
        this.isBumperRight = isBumperRight;
        this.isLockedDoor = isLockedDoor;
        this.isOpenedDoor = isOpenedDoor;
        this.isGround = isGround;
        this.isEndPoint = isEndPoint;
        this.endPoint = endPoint;
        this.isKey = isKey;
    }

    // Getters and setters

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }

    public boolean isBumperLeft() {
        return isBumperLeft;
    }

    public void setBumperLeft(boolean isBumperLeft) {
        this.isBumperLeft = isBumperLeft;
    }

    public boolean isBumperRight() {
        return isBumperRight;
    }

    public void setBumperRight(boolean isBumperRight) {
        this.isBumperRight = isBumperRight;
    }

    public boolean isLockedDoor() {
        return isLockedDoor;
    }

    public void setLockedDoor(boolean isLockedDoor) {
        this.isLockedDoor = isLockedDoor;
    }

    public boolean isOpenedDoor() {
        return isOpenedDoor;
    }

    public void setOpenedDoor(boolean isOpenedDoor) {
        this.isOpenedDoor = isOpenedDoor;
    }

    public boolean isGround() {
        return isGround;
    }

    public void setGround(boolean isGround) {
        this.isGround = isGround;
    }

    public boolean isEndPoint() {
        return isEndPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }

    public void draw(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(baseColor);
        g.fillRect(x, y, width, height);
        if (isEndPoint) {
            int[] xPoints = {x + width / 2, x + 12, x, x + 10, x + width / 4, x + 15, x + 3 * width / 4, x + 20, x + width, x + 17};
            int[] yPoints = {y, y + 11, y + height / 3, y + 16, y + height, y + 20, y + height, y + 16, y + height / 3, y + 11};
            g.setColor(Color.YELLOW);
            g.fillPolygon(xPoints, yPoints, 10);
        }
        else if (isBumperRight) {
            int[] xPoints = {x, x, x + 20, x + 20, x + 30, x + 20, x + 20};
            int[] yPoints = {y + 10, y + 20, y + 20, y + 30, y + 15, y, y + 10};
            g.setColor(Color.PINK);
            g.fillPolygon(xPoints, yPoints, 7);
            g.setColor(oldColor);
        }
        else if (isBumperLeft) {
            int[] xPoints = {x + 30, x + 30, x + 10, x + 10, x, x + 10, x + 10};
            int[] yPoints = {y + 10, y + 20, y + 20, y + 30, y + 15, y, y + 10};
            g.setColor(Color.PINK);
            g.fillPolygon(xPoints, yPoints, 7);
            g.setColor(oldColor);
        }
        else if (isKey) {
            int[] xPoints = {x + 4, x + 4, x + 8, x + 8, x + 20, x + 20, x + 22, x + 24, x + 26, x + 28, x + 28, x + 26, x + 24, x + 22, x + 20, x + 20};
            int[] yPoints = {y + 12, y + 23, y + 23, y + 18, y + 18, y + 20, y + 22, y + 25, y + 22, y + 20, y + 10 , y + 8, y + 5, y + 8, y + 10, y + 12};
            g.setColor(Color.YELLOW);
            g.fillPolygon(xPoints, yPoints, 16);
            int[] xP = {x + 24, x + 23, x + 23 , x + 24, x + 26, x + 26};
            int[] yP = {y + 8, y + 10, y + 20, y + 22, y + 20, y + 10};
            g.setColor(Color.BLACK);
            g.fillPolygon(xP, yP, 6);

            g.setColor(oldColor);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
