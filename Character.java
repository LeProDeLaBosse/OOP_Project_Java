import java.awt.Rectangle;
import java.util.Set;
import java.util.HashSet;
import java.awt.event.KeyEvent;

public class Character {
    private int x;
    private int y;
    private int width;
    private int height;
    private int moveSpeed;
    private Rectangle hitbox;
    private Set<Integer> pressedKeys; // new member variable
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private GamePanel panel; // new member variable

    public Character(int x, int y, int width, int height, int minX, int maxX, int minY, int maxY, GamePanel panel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.moveSpeed = 30;
        this.hitbox = new Rectangle(x, y, width, height);
        this.pressedKeys = new HashSet<>(); // initialize the set
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.panel = panel; // save the panel as an instance variable
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setX(int x) {
        this.x = x;
        this.hitbox.setLocation(x, this.hitbox.y);
    }

    public void setY(int y) {
        this.y = y;
        this.hitbox.setLocation(this.hitbox.x, y);
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void moveLeft() {
        if (x - moveSpeed >= minX) {
            x -= moveSpeed;
        } else {
            x = maxX - width;
        }
    }

    public void moveRight() {
        if (x + moveSpeed + 1 <= maxX) {
            x += moveSpeed;
        } else {
            x = minX;
        }
    }

    public void moveUp() {
        if (y - moveSpeed >= minY) {
            y -= moveSpeed;
        } else {
            y = maxY - height;
        }
    }

    public void moveDown() {
        if (y + moveSpeed + 1 <= maxY) {
            y += moveSpeed;
        } else {
            y = minY;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    

    public void update() {
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            moveLeft();
            panel.repaint(); // repaint the panel
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            moveRight();
            panel.repaint(); // repaint the panel
        }
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            moveUp();
            panel.repaint(); // repaint the panel
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            moveDown();
            panel.repaint(); // repaint the panel
        }
    }
    
    // new methods for key press and release
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressedKeys.add(keyCode);
        /*System.out.println("Key pressed: " + keyCode);
        System.out.println("Pressed keys: " + pressedKeys);*/
    }
    
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressedKeys.remove(keyCode);
        /*System.out.println("Key released: " + keyCode);
        System.out.println("Pressed keys: " + pressedKeys);*/
    }
}