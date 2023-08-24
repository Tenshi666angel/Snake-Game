import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Змейка");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(320, 320);
        setLocation(400, 200);
        add(new GameField());
        setVisible(true);
    }
    public static void main(String[] args) {
        Main main = new Main();
    }
}