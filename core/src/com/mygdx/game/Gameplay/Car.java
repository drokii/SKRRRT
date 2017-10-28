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



    private SpriteBatch batch;
    private Texture track;
    private Texture kart;
    private Sprite kartSprite;
    private World world;
    private Body kartBody;
    private float posX, posY;

    private CarInputProcessorHelper input;
    public CarInputProcessorHelper getInput() {
        return input;
    }



    public float getSpeed() {
        return speed;
    }

    private float speed = 1f;
    private float maxspeed = 1000f;

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
        shape.setAsBox(kartSprite.getWidth(), kartSprite.getHeight());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        kartBody.createFixture(fixtureDef);
        shape.dispose();

        kartBody.setTransform(new Vector2(1728, 768),0);

        // Reference to Input Processor
        input = new CarInputProcessorHelper(this);
    }

    @Override
    public void render() {
        input.render();
        world.step(1f / 60f, 6, 2);
        kartBody.applyTorque(torque, true);
        kartSprite.setPosition(kartBody.getPosition().x, kartBody.getPosition().y);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        kartSprite.setRotation((float) Math.toDegrees(kartBody.getAngle()));
        kartSprite.setPosition(kartBody.getTransform().getPosition().x, kartBody.getTransform().getPosition().y);
        batch.draw(kartSprite, kartSprite.getX(), kartSprite.getY(), kartSprite.getOriginX(), kartSprite.getOriginY(), 32, 32, kartSprite.getScaleX(), kartSprite.getScaleY(), kartSprite.getRotation());
        batch.end();

        camera.position.set(getKartSprite().getX(), getKartSprite().getY(), 0);
        camera.update();
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
            kartBody.setLinearVelocity(0, speed);
        }
        if (speed >= 0) {
            speed = speed * 1.05f;
            keepVelocity();
        }
    }

    public void keepVelocity()
    {
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

}

