import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private char[][] map =  {
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '#' },
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
    };
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Integer score = 0;
    private Timer timer;
    private enum Direction { Left, Right, Up, Down }
    private Direction direction = Direction.Right;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void move() {
        for (int i = dots; i > 0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case Left: x[0] -= DOT_SIZE;
            break;
            case Right: x[0] += DOT_SIZE;
            break;
            case Up: y[0] -= DOT_SIZE;
            break;
            case Down: y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if(x[0] == appleX && y[0] == appleY) {
            dots++;
            score++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0 ; i--) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if(x[0] > map[0].length * DOT_SIZE) {
            inGame = false;
        }
        if(x[0] < DOT_SIZE) {
            inGame = false;
        }
        if(y[0] > map[1].length * DOT_SIZE) {
            inGame = false;
        }
        if(y[0] < DOT_SIZE) {
            inGame = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame) {
            for (int i = 0; i < map[1].length; i++) {
                for(int j = 0; j < map[0].length; j++) {
                    if(map[i][j] == '#') {
                        g.drawImage(apple, i * DOT_SIZE, j * DOT_SIZE, this);
                    }
                }
            }
            
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
            g.setColor(Color.CYAN);
            g.drawString(score.toString(), 30, 30);
        }
        else {
            g.setColor(Color.CYAN);
            g.drawString("Game Over", 125, SIZE / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            move();
        }
        checkApple();
        checkCollisions();
        repaint();
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(150, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(1, map[0].length - 1) * DOT_SIZE;
        appleY = new Random().nextInt(1, map[1].length - 1) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iit = new ImageIcon("dot.png");
        dot = iit.getImage();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && direction != Direction.Right) {
                direction = Direction.Left;
            }
            if(key == KeyEvent.VK_RIGHT && direction != Direction.Left) {
                direction = Direction.Right;
            }
            if(key == KeyEvent.VK_UP && direction != Direction.Down) {
                direction = Direction.Up;
            }
            if(key == KeyEvent.VK_DOWN && direction != Direction.Up) {
                direction = Direction.Down;
            }
        }
    }
}
