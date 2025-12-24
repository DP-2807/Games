import java.awt.*;
import java.awt.event.*;

class Touch_Me_If_You_Dare extends Frame implements MouseMotionListener, MouseListener {

    int Circlex = 200, CircleY = 200, CircleRadius = 6;
    boolean gameOver = false;

    int frameWidth = 400, frameHeight = 400;

    Rectangle restartBtn = new Rectangle(220, 335, 130, 20);

    int score = 0;              // Score
    long startTime;             // Timer start

    Touch_Me_If_You_Dare() {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addMouseMotionListener(this);
        addMouseListener(this);

        startTime = System.currentTimeMillis();

        setLayout(new FlowLayout());
        setVisible(true);
        setSize(400, 400);
        setSize(frameWidth, frameHeight);
        setTitle("Catch Me If You Can");
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (!gameOver) {
            g.setColor(Color.MAGENTA);
            g.fillOval(Circlex, CircleY, 2 * CircleRadius, 2 * CircleRadius);

            // Show score
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Score: " + score, 20, 60);

        } else {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!!", 100, 200);

            g.setColor(Color.orange);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Final Score: ",30, 350);
            g.setColor(Color.darkGray);
            g.drawString(String.valueOf(score), 130, 350);

            g.setColor(Color.DARK_GRAY);
            g.drawRect(restartBtn.x, restartBtn.y,
                    restartBtn.width, restartBtn.height);

            g.setColor(Color.ORANGE);
            g.drawString("Click to Restart",
                    restartBtn.x + 8, restartBtn.y + 17);
        }
    }

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {

        if (gameOver) return;

        int mouseX = e.getX();
        int mouseY = e.getY();

        // Opposite movement
        if (mouseX < Circlex + CircleRadius) Circlex++;
        if (mouseX > Circlex + CircleRadius) Circlex--;
        if (mouseY < CircleY + CircleRadius) CircleY++;
        if (mouseY > CircleY + CircleRadius) CircleY--;

        // Score based on time
        score = (int) ((System.currentTimeMillis() - startTime) / 1000);

        // Collision detection
        int centerX = Circlex + CircleRadius;
        int centerY = CircleY + CircleRadius;

        double distance = Math.sqrt(
                Math.pow(mouseX - centerX, 2) +
                        Math.pow(mouseY - centerY, 2)
        );

        if (distance <= CircleRadius) {
            gameOver = true;
        }
        // Out of frame detection
        if (Circlex < 0 ||
                CircleY < 0 ||
                Circlex + 2 * CircleRadius > frameWidth ||
                CircleY + 2 * CircleRadius > frameHeight) {

            gameOver = true;
        }

        repaint();
    }

    // Restart on mouse click
    public void mouseClicked(MouseEvent e) {

        if (gameOver && restartBtn.contains(e.getPoint())) {
            Circlex = 100;
            CircleY = 100;
            score = 0;
            gameOver = false;
            startTime = System.currentTimeMillis();
            repaint();
        }
    }


    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new Touch_Me_If_You_Dare();
    }
}
