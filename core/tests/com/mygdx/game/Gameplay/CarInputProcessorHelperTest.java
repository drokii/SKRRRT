package com.mygdx.game.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import static junit.framework.TestCase.*;

public class CarInputProcessorHelperTest extends GameTest {

    private CarInputProcessorHelper carInputProcessorHelper;
    private Car car;

    @Before
    public void carInputProcessorHelperTest(){
        car = new Car(new World(new Vector2(0,0), false));
        carInputProcessorHelper = new CarInputProcessorHelper(car);

        assertNotNull(carInputProcessorHelper);
    }

    @Test
    public void keyUpTest() throws InterruptedException {
        //Test input W
        car.setSpeed(1f);
        carInputProcessorHelper.setUnlocked(true);
        carInputProcessorHelper.keyUp(Input.Keys.W);

        //Create a new schedule that runs 20 ms after the timer in carInputProcessorHelper
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                assertTrue(car.getSpeed() < 1f);
            }
        }, 120);

        //Wait for the assert to happen. Otherwise the unit test will close before the schedule is executed
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //Cancel up Timer for the next test
        carInputProcessorHelper.getTimerUp().cancel();

        //Test input S
        car.setSpeed(-1f);
        carInputProcessorHelper.setUnlocked(true);
        carInputProcessorHelper.keyUp(Input.Keys.S);

        //Create a new schedule that runs 20 ms after the timer in carInputProcessorHelper
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                assertTrue(car.getSpeed() > -1f);
            }
        }, 120);

        //Wait for the assert to happen. Otherwise the unit test will close before the schedule is executed
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
