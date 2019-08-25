import java.awt.*;
import java.awt.image.BufferStrategy;

public class Test extends Canvas implements Runnable {
    //Window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public Window window;
    public Handler handler;

    //Performance
    private Thread thread;
    private int fps;
    private boolean running = false;
    private double timeNow;

    //State Booleans
    boolean red = false;
    boolean green = false;
    boolean tooSoon = false;
    boolean begin = true;

    public Test() {
        window = new Window(WIDTH, HEIGHT, "Reaction Time Test", this);
        handler = new Handler(this);
    }

    //Starts the game thread
    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    //Stops the game thread
    public void stop() {
        try {
            running = false;
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Runs the game
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running) {
            requestFocus();
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    //Tick happens X amount of times per second - defined in run()
    public void tick() {

        timeNow = System.currentTimeMillis();

        if(red) {
            //if length of time it's been red; is more than the time between (the click and the random number)
            if(timeNow - handler.getRandomNo() >= handler.timeRed) {
                red = false;
                green = true;
            }
        }
    }

    //Render loads everything onto the screen
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        //Background
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //FPS counter
        g.setFont(new Font("Verdana", 1, 16));
        g.setColor(Color.GREEN);    //FPS counter colour
        g.drawString(fps + " FPS", WIDTH - 128, 40);

        //Game logic rendering
        if(green) {
            g.setColor(Color.green);
            g.fillRect(WIDTH/4 + WIDTH/8, HEIGHT/4 + HEIGHT/8, WIDTH/4, HEIGHT/4);
        } else if(red) {
            g.setColor(Color.red);
            g.fillRect(WIDTH/4 + WIDTH/8, HEIGHT/4 + HEIGHT/8, WIDTH/4, HEIGHT/4);
        } else if(begin) {
            g.setColor(Color.green);
            g.drawString("Click the screen to begin the test", WIDTH/4 + WIDTH/16, HEIGHT/2);
        } else if(tooSoon) {
            g.setColor(Color.green);
            g.drawString("Too Soon! Please click to begin again", WIDTH/4 + WIDTH/16, HEIGHT/2);
        }

        g.dispose();
        bs.show();
    }


    public static void main(String[] args) {
        Test test = new Test();
    }
}