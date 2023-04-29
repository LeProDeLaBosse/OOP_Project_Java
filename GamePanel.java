import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class GamePanel extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 51 * 30;
    private static final int PANEL_HEIGHT = 26 * 30;
    private static final int BLOCK_SIZE = 30;
    private static final int NUM_ROWS = PANEL_HEIGHT / BLOCK_SIZE;
    private static final int NUM_COLS = PANEL_WIDTH / BLOCK_SIZE;
    private Character player;
    private Block[][] blocks;
    private Point endPoint = new Point(0, 0);
    private Color baseColor = null;
    private Levels levels = new Levels();
    
    int currentLevelNb = 0;
    String[] currentLevel = levels.getLevel(currentLevelNb);
    
    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.WHITE);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        // Show instructions window
        JOptionPane.showMessageDialog(null, "Welcome to the game!\n\nThe goal of the game is to reach the end point (the star block) while avoiding the walls and obstacles in your way.\n\nYou can use the arrows on your keypad to move your character (red block).\n\nHere are the different types of blocks you will encounter:\n\n- Walls : Grey blocks\n- Bumper (left) : Left arrow blocks\n- Bumper (right) : Right arrow blocks\n- Locked door (requires a key) : Dark grey blocks\n- Open door : Green blocks\n- Key : Key blocks\n\nThe four first levels will intriduce you all blocks mecanics.\n\nPress enter or click OK to start playing.");
        // create a new character object
        player = new Character(30, 30, 30, 30, 0, PANEL_WIDTH, 0, PANEL_HEIGHT, this);
        // Create blocks
        blocks = new Block[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < NUM_ROWS; row++) {
            String rowData = currentLevel[row];
            for (int col = 0; col < NUM_COLS; col++) {
                char blockChar = rowData.charAt(col);
                boolean isWall = (blockChar == 'W');
                boolean isBumperLeft = (blockChar == 'B');
                boolean isBumperRight = (blockChar == 'R');
                boolean isLockedDoor = (blockChar == 'L');
                boolean isOpenedDoor = (blockChar == 'O');
                boolean isGround = (blockChar == ' ');
                boolean isKey = (blockChar == 'K');
                if (blockChar == 'P') {
                    player.setX(col * BLOCK_SIZE);
                    player.setY(row * BLOCK_SIZE);
                    blocks[row][col] = new Block(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, baseColor, false, false, false, false, false, false,false, endPoint, false);
                } else if (blockChar == 'E') {
                    endPoint.setLocation(col * BLOCK_SIZE, row * BLOCK_SIZE);
                    blocks[row][col] = new Block(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, baseColor, false, false, false, false, false, false,true, endPoint, false);
                } else {
                    blocks[row][col] = new Block(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, baseColor, isWall, isBumperLeft, isBumperRight,isLockedDoor, isOpenedDoor, isGround,false, endPoint, isKey);
                }
            }
        }
    }

    public void resetLevel(String[] level, int x, int y) {
        currentLevel = level;
        player = new Character(x, y, 30, 30, 0, PANEL_WIDTH, 0, PANEL_HEIGHT, this);
            blocks = new Block[NUM_ROWS][NUM_COLS];
            for (int row = 0; row < NUM_ROWS; row++) {
                String rowData = currentLevel[row];
                for (int col = 0; col < NUM_COLS; col++) {
                    char blockChar = rowData.charAt(col);
                    boolean isWall = (blockChar == 'W');
                    boolean isBumperLeft = (blockChar == 'B');
                    boolean isBumperRight = (blockChar == 'R');
                    boolean isLockedDoor = (blockChar == 'L');
                    boolean isOpenedDoor = (blockChar == 'O');
                    boolean isGround = (blockChar == ' ');
                    boolean isKey = (blockChar == 'K');
                    if (blockChar == 'P') {
                        player.setX(col * BLOCK_SIZE);
                        player.setY(row * BLOCK_SIZE);
                        blocks[row][col] = new Block(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, baseColor, false, false, false, false, false, false,false, endPoint, false);
                    } else if (blockChar == 'E') {
                        endPoint.setLocation(col * BLOCK_SIZE, row * BLOCK_SIZE);
                        blocks[row][col] = new Block(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, baseColor, false, false, false, false, false, false,true, endPoint, false);
                    } else {
                        blocks[row][col] = new Block(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, baseColor, isWall, isBumperLeft, isBumperRight, isLockedDoor, isOpenedDoor, isGround,false, endPoint, isKey);
                    }
                }
            }
    }

    public Block getBlock(int x, int y) {
        int row = y / BLOCK_SIZE;
        int col = x / BLOCK_SIZE;
        if (row >= 0 && row < NUM_ROWS && col >= 0 && col < NUM_COLS) {
            return blocks[row][col];
        }
        return new Block(x, y, row, col, baseColor, false, false, false, false, false, false, false, endPoint, false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw blocks
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Block block = blocks[row][col];
                if (block != null) {
                    block.draw(g);
                }
            }
        }
        // Render player
        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if ((keyCode == KeyEvent.VK_LEFT) && 
            !((getBlock(player.getX() - 1, player.getY()).isWall())) && 
            !((getBlock(player.getX() - 1, player.getY()).isLockedDoor()))) {
            player.moveLeft();
        } else if ((keyCode == KeyEvent.VK_RIGHT) &&
            !((getBlock(player.getX() + player.getWidth() + 1, player.getY()).isWall())) && 
            !((getBlock(player.getX() + player.getWidth() + 1, player.getY()).isLockedDoor()))){
            player.moveRight();
        } else if ((keyCode == KeyEvent.VK_UP) && 
            !((getBlock(player.getX(), player.getY() - 1).isWall())) && 
            !((getBlock(player.getX(), player.getY() - 1).isLockedDoor()))){
            player.moveUp();
        } else if ((keyCode == KeyEvent.VK_DOWN) &&
            !((getBlock(player.getX(), player.getY() + player.getHeight() + 1).isWall())) && 
            !((getBlock(player.getX(), player.getY() + player.getHeight() + 1).isLockedDoor()))){
            player.moveDown();
        }

        // check if player reached endpoint
        Block currentBlock = getBlock(player.getX(), player.getY());
        if (currentBlock != null && currentBlock.isEndPoint()) {
            // reset level
            currentLevelNb ++;
            if (currentLevelNb == 6) {
                JFrame winFrame = new JFrame("You Win!");
                winFrame.setSize(300, 100);
                winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JLabel winLabel = new JLabel("Congratulations, you win!");
                winLabel.setHorizontalAlignment(JLabel.CENTER);
                winFrame.getContentPane().add(winLabel);
                winFrame.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Congratulations, you finished level " + currentLevelNb + "\n\nPress enter or click ok to go to level " + (currentLevelNb + 1));
                resetLevel(levels.getLevel(currentLevelNb), 30, 30);
            }
        }
        else if (currentBlock != null && currentBlock.isBumperLeft()) {
            player.update();
            repaint();
            // update player position during the delay
            for (int i = 0; i < 5; i++) {
                player.moveLeft();
                player.update();
                repaint();
                /*try {
                    Thread.sleep(200); // wait for 200 milliseconds between each update
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }*/
            }
            while (getBlock(player.getX(), player.getY()).isWall()) {
                player.moveLeft();
            }
        }
        else if (currentBlock != null && currentBlock.isBumperRight()) {
            player.update();
            repaint();
            // update player position during the delay
            for (int i = 0; i < 5; i++) {
                player.moveRight();
                player.update();
                repaint();
                /*try {
                    Thread.sleep(200); // wait for 200 milliseconds between each update
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }*/
            }
            while (getBlock(player.getX(), player.getY()).isWall()) {
                player.moveRight();
            }
        }
        

        //check if player is on a key
        if (currentBlock != null && currentBlock.isKey()) {
            levels.setBlock(currentLevel, 'O');
            resetLevel(currentLevel, player.getX(), player.getY());
        }

        repaint(); // repaint the panel to show the updated position of the character
    }


    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public void start() {
        // start the game loop
        Thread gameThread = new Thread(() -> {
            while (true) {
                player.update();
                repaint();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException ex) {
                    System.out.println("Game loop interrupted: " + ex.getMessage());
                }
            }
        });
        gameThread.start();
    }
}