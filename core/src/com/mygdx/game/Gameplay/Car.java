package com.mygdx.game.Gameplay;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import java.util.Timer;
import java.util.TimerTask;

public class Car extends ApplicationAdapter implements ApplicationListener {

    /**
     * This class keeps track of the maxspeed, speed and velocity of the car.
     * Also it calculates new vector values used to rotate the car.
     */

    private SpriteBatch batch;
    private Texture kart;
    private Sprite kartSprite;
    private World world;
    private Body kartBody;
    private float posX, posY;

    private Box2DDebugRenderer renderer;

    private CarInputProcessorHelper input;
    public CarInputProcessorHelper getInput() {
        return input;
    }


    private float speed = 1f;
    private boolean driftRight = false;
    private boolean driftLeft = false;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private float maxspeed = 300f;

    public String getName() {
        return name;
    }
    private String name = "CarBoy";


    public boolean getIsOnFinishLine() {
        return isOnFinishLine;
    }

    public void setOnFinishLine(boolean onFinishLine) {
        isOnFinishLine = onFinishLine;
    }

    private boolean isOnFinishLine;
    private OrthographicCamera camera;

    public float getTorque() {
        return torque;
    }
    private float torque = 0f;


    //Constructor for Car
    public Car(OrthographicCamera camera, World world) {


        // Reference to game Camera
        this.camera = camera;
        batch = new SpriteBatch();
        kart = new Texture("core\\assets\\MiniCar.png");
        kartSprite = new Sprite(kart);
        kartSprite.setPosition(posX, posY);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX, posY);
        this.world = world;
        kartBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 24);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        kartBody.createFixture(fixtureDef);
        kartSprite.setCenter(getKartBody().getPosition().x, getKartBody().getPosition().y);

        shape.dispose();

        kartBody.setTransform(new Vector2(1050, 800),-1.56f);

        renderer = new Box2DDebugRenderer(true, true, true, true, true, true);
        // Reference to Input Processor
        input = new CarInputProcessorHelper(this);
    }

    @Override
    public void render() {
        input.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        world.step(1f / 60f, 10, 10);
        kartBody.applyTorque(torque, true);
        kartSprite.setPosition(kartBody.getPosition().x, kartBody.getPosition().y);
        kartSprite.setRotation((float) Math.toDegrees(kartBody.getAngle()));
        kartSprite.setPosition(kartBody.getTransform().getPosition().x, kartBody.getTransform().getPosition().y);
        if(driftRight)
        {
            batch.draw(kartSprite, getKartBody().getPosition().x-16, getKartBody().getPosition().y-16, kartSprite.getOriginX(), kartSprite.getOriginY(), 32, 32, kartSprite.getScaleX(), kartSprite.getScaleY(), kartSprite.getRotation() - 30);
        }
        else if(driftLeft)
        {
            batch.draw(kartSprite, getKartBody().getPosition().x-16, getKartBody().getPosition().y-16, kartSprite.getOriginX(), kartSprite.getOriginY(), 32, 32, kartSprite.getScaleX(), kartSprite.getScaleY(), kartSprite.getRotation() + 30);
        }
        else
        {
            batch.draw(kartSprite, getKartBody().getPosition().x-16, getKartBody().getPosition().y-16, kartSprite.getOriginX(), kartSprite.getOriginY(), 32, 32, kartSprite.getScaleX(), kartSprite.getScaleY(), kartSprite.getRotation());
        }
        camera.position.set(getKartSprite().getX(), getKartSprite().getY(), 0);
        camera.update();
        batch.end();

        renderer.render(world, camera.combined);
    }

    public Body getKartBody(){
        return kartBody;
    }

    public Sprite getKartSprite() {
        return kartSprite;
    }

    @Override
    public void dispose() {
        batch.dispose();
        kart.dispose();
    }

    /**
     * The method driveBackward(Timer timer), when called it speeds up the car in a backward motion.
     * @param timer this is the timer that caculates the speed dropoff, since the method is for acceleration the timer needs to be cancelled.
     */
    public void driveBackward(Timer timer)
    {

        if(timer != null)
        {
            timer.cancel();
        }
        if (speed == 0 || (speed >= 0 && speed <= 3)) {
            speed = -1;
        } else if (speed < 0) {
            speed = speed * 1.02f;
            keepVelocity();
        } else if (speed > 0) {
            speed = speed * 0.98f;
            keepVelocity();
        }
    }

    /**
     * The method driveForward(Timer timer), when called it speeds up the car in a forward motion.
     * @param timer this is the timer that caculates the speed dropoff, since the method is for acceleration the timer needs to be cancelled.
     */
    public void driveForward(Timer timer)
    {

        if(timer != null)
        {
            timer.cancel();
        }
        if (speed == 0 || (speed <= 0 && speed >= -3)) {
            speed = 1;
        } else if (speed * 1.05f >= maxspeed) {
            speed = maxspeed;
        } else if (speed < 0) {
            speed = speed * 0.95f;
            keepVelocity();
        }
        if (speed >= 0) {
            speed = speed * 1.05f;
            keepVelocity();
        }
    }

    public void keepVelocity()
    {
        /**
         * The method keepVelocity needs to calculate the and set the LinearVelocity for the @param kartBody
         * @param angle , the angle of the kart.
         */
        float angle;
        if (speed * MathUtils.sinDeg(kartSprite.getRotation()) > 0) {

            angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) -
                    (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));

        } else if ((speed * MathUtils.sinDeg(kartSprite.getRotation()) < 0)) {

            angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) -
                    (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));

        } else {
            angle = 0;
        }
        kartBody.setLinearVelocity(angle, speed * MathUtils.cosDeg(kartSprite.getRotation()));
    }

    public void DriftRight()
    {
        if(!driftRight)
        {
            stopDrift();
            driftRight = true;
        }

    }
    public void DriftLeft()
    {
        if(!driftLeft)
        {
            stopDrift();
            driftLeft = true;
        }
    }

    public void stopDrift()
    {
        if(driftLeft)
            driftLeft = false;
        if(driftRight)
            driftRight = false;
    }

}

