import java.awt.*;
import javax.swing.*;

class STriangles extends JPanel implements Runnable {
    private Thread watek;
    private int pauza;
    private int counter = 0;
    private Color selectedColor = Color.BLACK;

    public void paintComponent(Graphics g)
    {
        g.setColor(selectedColor);
        super.paintComponent(g);
        Dimension s = getSize();

        divide(0,s.height, s.width, 0, counter, g);
    }

    void divide(int x, int y, int len, int lvl, int max, Graphics g)
    {
        if(lvl == max)
        {
            Polygon triangle = new Polygon();
            triangle.addPoint(x, y);
            triangle.addPoint(x + len/2, y - len);
            triangle.addPoint(x + len, y);
            g.fillPolygon(triangle);
        }else {
            divide(x, y, len / 2, lvl + 1, max, g);
            divide(x + len / 2, y, len / 2, lvl + 1, max, g);
            divide(x + len / 4, y - len / 2, len / 2, lvl + 1, max, g);
        }
    }
//25pkt
    public STriangles(int pauza, int priority){
        this.pauza = pauza;
        watek = new Thread(this);
        watek.setPriority(priority);
        watek.start();
    }

    public void run(){

        while(true){
            repaint();
            if(counter == 7)
            {
                counter = 0;
            }
            try
            {
                watek.sleep(pauza);
                counter++;
            }
            catch(InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main extends JFrame {


    public Main() {
        super("Sierpinski Triangle");
        int[] priority = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        setSize(1500,645);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container Kontener = getContentPane();
        Kontener.setLayout(new GridLayout(2,5));
        for(int i=0;i<10;i++){
            Kontener.add(new STriangles(2000, priority[i]));
            setVisible(true);
        }
    }
    public static void main(String[] args) {
        Main triangles = new Main();
    }
}