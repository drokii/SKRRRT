package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Gameplay.Car;
import org.junit.Before;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

import static junit.framework.TestCase.*;

public class CarTest extends GameTest {

    private Car car;

    @Before
    public void carTest(){
        World world = new World(new Vector2(0,0), false);
        car = new Car(world);

        assertNotNull(car);
    }

    @Test
    public void driveForwardTest(){
        final Timer driveTimer = new Timer();
        car.driveForward(driveTimer);
        assertEquals(1.05f, car.getSpeed());

        car.setSpeed(200f);
        car.driveForward(driveTimer);
        assertEquals(200f, car.getSpeed());

        car.setSpeed(-2);
        car.driveForward(driveTimer);
        assertEquals(1.05f, car.getSpeed());

        car.setSpeed(-5f);
        car.driveForward(driveTimer);
        assertEquals(-4.75f, car.getSpeed());
    }

    @Test
    public void driveBackwardTest(){
        final Timer driveTimer = new Timer();
        car.driveBackward(driveTimer);
        assertEquals(-1f, car.getSpeed());

        car.setSpeed(-200f);
        car.driveBackward(driveTimer);
        assertEquals(-200f, car.getSpeed());

        car.setSpeed(2f);
        car.driveBackward(driveTimer);
        assertEquals(-1f, car.getSpeed());

        car.setSpeed(4f);
        car.driveBackward(driveTimer);
        assertEquals(3.92f, car.getSpeed());
    }
}
