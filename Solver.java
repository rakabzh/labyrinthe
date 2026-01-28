import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Solver {

    static BufferedImage IMAGE;

    static {
        try {
            IMAGE = ImageIO.read(new File("labyrinthe.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static int WIDTH = IMAGE.getWidth();
    static int HEIGHT = IMAGE.getHeight();

    record Pixel(int x, int y) {}

    static int WHITE = Color.WHITE.getRGB();
    static int GRAY = Color.GRAY.getRGB();
    static int RED = Color.RED.getRGB();

    public static ArrayList<Pixel> getNeighbor(Pixel pixel){
        int x = pixel.x();
        int y = pixel.y();

        ArrayList<Pixel> candidates = new ArrayList<>(4);

        for (int i = -1; i <= 1; i++) {
            int newX = i+x;
            for (int j = -1; j <= 1; j++) {
                if (Math.abs(i) == Math.abs(j)) continue;
                int newY = j+y;
                if (0 > newX || newX >= WIDTH || 0 > newY || newY >= HEIGHT) continue;
                if (IMAGE.getRGB(newX,newY) == WHITE){
                    candidates.add(new Pixel(newX,newY));
                }
            }
        }
        
        return candidates;
    }



    static Pixel findEntrance() {
        for (int x = 0; x < WIDTH; x++) {
            if (IMAGE.getRGB(x, 0) == WHITE) {
                return new Pixel(x, 0);
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        Pixel startPixel = findEntrance();
        Deque<Pixel> visited = new ArrayDeque<>();
        Map<Pixel, Pixel> parent = new HashMap<>();

        visited.push(startPixel);
        parent.put(startPixel, null);

        IMAGE.setRGB(startPixel.x(), startPixel.y(), GRAY);

        Pixel exit = null;

        while (!visited.isEmpty()){
            Pixel current = visited.removeFirst();

            if (current.y() == HEIGHT - 1) {
                exit = current;
                break;
            }

            for (Pixel n : getNeighbor(current)) {
                IMAGE.setRGB(n.x(), n.y(), GRAY);
                parent.put(n, current);
                visited.addLast(n);
            }
        }

        Pixel p = exit;
        while (p != null) {
            IMAGE.setRGB(p.x(), p.y(), RED);
            p = parent.get(p);
        }

        ImageIO.write(IMAGE, "png", new File("labyrinthe_solver.png"));

    }
}
