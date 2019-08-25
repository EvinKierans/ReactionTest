import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Handler extends MouseAdapter {
    //Randomizers
    private Random rand;
    private int randomNo;
    private double result;

    //Handler things
    public boolean mouseIsPressed = false;
    private Test test;

    //Timer Things
    double timeTaken;
    double timeRed;
    double timeGreen;
    double timePressed = System.currentTimeMillis();
    double timeLastPress;
    double timeBetweenClicks;

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
        timeLastPress = timePressed;
        timePressed = System.currentTimeMillis();
        //checks time between clicks - may use in future
//        timeBetweenClicks = timePressed - timeLastPress;
//        System.out.println("gap: "+timeBetweenClicks);
        if(test.green) {
            result = timePressed - timeRed - getRandomNo();
            System.out.println("Time to react: " + result);
            timeRed = timePressed;
            //System.out.println("random no: " + getRandomNo());
            test.green = false;
            test.result = true;
        } else if (test.result) {
            randomizer();
            test.result = false;
            test.red = true;
        } else if(test.begin) {
            randomizer();
            //System.out.println("random no: " + getRandomNo());
            timeRed = timePressed;
            test.begin = false;
            test.red = true;
        } else if(test.tooSoon) {
            randomizer();
            timeRed = timePressed;
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

    public double getResult() { return result; }
}
