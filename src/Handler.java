import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Handler extends MouseAdapter {
    //Randomizers
    private Random rand;
    private int randomNo;

    //Handler things
    public boolean mouseIsPressed = false;
    private Test test;

    //Timer Things
    private double timeTaken;
    private double timeRed;
    private double timeGreen;
    private double timeNow;
    private double timePressed = 0;
    private double timeLastPress;
    private double timeBetweenClicks;

    public Handler(Test test) {
        this.test = test;
        rand = new Random();
        test.addMouseListener(this);
    }

    public void randomizer() {
        randomNo = rand.nextInt(4000) + 1000;
        //System.out.println("random = " + randomNo);
    }

    //Mouse input functions
    public void mousePressed(MouseEvent e) {
        mouseIsPressed = true;
        randomizer();
        timeLastPress = timePressed;
        timePressed = System.currentTimeMillis();
        timeBetweenClicks = timePressed - timeLastPress;
        System.out.println(timeBetweenClicks);
        if(test.green) {
            test.green = false;
            test.red = true;
        } else if(test.begin) {
            test.begin = false;
            test.red = true;
        } else if(test.tooSoon) {
            test.tooSoon = false;
            test.red = true;
        } else if (test.red) {
            test.red = false;
            test.tooSoon = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        mouseIsPressed = false;
    }

    public int getRandomNo() {
        return randomNo;
    }
}
