import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Main {

    static Random RANDOM = new Random();

    static int WIDTH = 101;
    static int HEIGHT = 51;

    static BufferedImage IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    record Pixel(int x, int y) {}

    static int WHITE = Color.WHITE.getRGB();
    static int GRAY = Color.GRAY.getRGB();

    public static int setOddNumber(int integer){
        return (integer/2)*2+1;
    }

    public static Pixel getRandomNeighbor(Pixel pixel){
        int x = pixel.x();
        int y = pixel.y();

        Pixel[] candidates = new Pixel[4];
        int count = 0;

        for (int i = -2; i <= 2; i+=2) {
            int newX = i+x;
            for (int j = -2; j <= 2; j+=2) {
                if (Math.abs(i) == Math.abs(j)) continue;
                int newY = j+y;
                if (0 > newX || newX >= WIDTH || 0 > newY || newY >= HEIGHT) continue;
                if (IMAGE.getRGB(newX,newY) == GRAY){
                    candidates[count++] = new Pixel(newX,newY);
                }
            }
        }

        if (count == 0) return null;

        return candidates[RANDOM.nextInt(count)];
    }

    public static void putPixel(Pixel a, Pixel b){
        IMAGE.setRGB(a.x(), a.y(), WHITE);
        IMAGE.setRGB((a.x() + b.x()) / 2, (a.y() + b.y()) / 2, WHITE);
    }

    static void openEntrance(int yInside, int yOutside) {
        Pixel chosen = null;
        int count = 0;

        for (int x = 0; x < WIDTH; x++) {
            if (IMAGE.getRGB(x, yInside) == WHITE) {
                count++;
                if (RANDOM.nextInt(count) == 0) {
                    chosen = new Pixel(x, yOutside);
                }
            }
        }

        if (chosen != null) {
            IMAGE.setRGB(chosen.x(), chosen.y(), WHITE);
        }
    }

    public static void main(String[] args) throws IOException {
        Graphics2D g = IMAGE.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.dispose();

        for (int x = 1; x < WIDTH; x+=2) {
            for (int y = 1; y < HEIGHT; y+=2) {
                IMAGE.setRGB(x, y, GRAY);
            }
        }

        Pixel startPixel = new Pixel(setOddNumber(RANDOM.nextInt(0, WIDTH-2)),
                setOddNumber(RANDOM.nextInt(0, HEIGHT-2)));
        Deque<Pixel> path = new ArrayDeque<>();
        path.push(startPixel);

        IMAGE.setRGB(startPixel.x(), startPixel.y(), WHITE);

        Pixel pixel = startPixel;

        while (!path.isEmpty()){
            Pixel neighbor = getRandomNeighbor(pixel);
            if (neighbor == null){
                pixel = path.pop();
                continue;
            }
            putPixel(neighbor, pixel);
            path.push(neighbor);
            pixel = neighbor;
        }

        openEntrance(1,0);
        openEntrance(HEIGHT-2,HEIGHT-1);

        ImageIO.write(IMAGE, "png", new File("labyrinthe.png"));
    }
}