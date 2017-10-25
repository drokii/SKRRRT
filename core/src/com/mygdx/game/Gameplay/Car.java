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

public class Car extends ApplicationAdapter implements ApplicationListener, InputProcessor {
    private SpriteBatch batch;
    private Texture track;
    private Texture kart;
    private Sprite kartSprite;
    private World world;
    private Body kartBody;
    private float posX, posY;

    private float speed = 1f;
    private float maxspeed = 1000f;

    private OrthographicCamera camera;

    private float torque = 0f;

    private Timer timerUp = new Timer(true);
    private Timer timerLeft = new Timer(true);
    private Timer timerRight = new Timer(true);
    private Timer timerDown = new Timer(true);

    public Car(OrthographicCamera camera, World world) {
        this.camera = camera;

        batch = new SpriteBatch();
        track = new Texture("core\\assets\\Map.png");
        kart = new Texture("core\\assets\\Kart.png");
        kartSprite = new Sprite(kart);
        kartSprite.setPosition(posX, posY);
        Gdx.input.setInputProcessor(this);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX, posY);
        //world = new World(new Vector2(0, 0), true);
        this.world = world;
        kartBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(kartSprite.getWidth() / 2, kartSprite.getHeight() / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        kartBody.createFixture(fixtureDef);
        shape.dispose();

        kartBody.setTransform(new Vector2(320, 1152),0);
    }

    @Override
    public void render() {
        //torque test
        world.step(1f / 60f, 6, 2);
        kartBody.applyTorque(torque, true);
        kartSprite.setPosition(kartBody.getPosition().x, kartBody.getPosition().y);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        keyPressed();

        kartSprite.setRotation((float) Math.toDegrees(kartBody.getAngle()));
        kartSprite.setPosition(kartBody.getTransform().getPosition().x, kartBody.getTransform().getPosition().y);
        batch.draw(kartSprite, kartSprite.getX(), kartSprite.getY(), kartSprite.getOriginX()/4, kartSprite.getOriginY()/4, 50, 50, kartSprite.getScaleX(), kartSprite.getScaleY(), kartSprite.getRotation());
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
        track.dispose();
        kart.dispose();
    }

    public void keyPressed()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(timerRight != null)
            {
                timerRight.cancel();
            }
            if((torque -= 0.025f) < -1f)
            {
                torque = -1f;
            }
            else
            {
                torque -= 0.025f;
            }
            kartBody.setAngularVelocity(torque);
            if(Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                driveForward();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                driveBackward();
            }
            keepVelocity();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(timerLeft != null)
            {
                timerLeft.cancel();
            }
            if((torque += 0.025f) > 1f)
            {
                torque = 1f;
            }
            else
            {
                torque += 0.025f;
            }
            kartBody.setAngularVelocity(torque);
            if(Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                driveForward();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                driveBackward();
            }
            keepVelocity();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            driveForward();
        }
        //opposite van key up code maken? //wss gwn allebei de values minnen;
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            driveBackward();
        }
    }

    public void driveBackward()
    {
        if(timerDown != null)
        {
            timerDown.cancel();
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

    public void driveForward()
    {
        if(timerUp != null)
        {
            timerUp.cancel();
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
            float angle;
            if (speed * MathUtils.sinDeg(kartSprite.getRotation()) > 0) {
                angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) - (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));
            } else if ((speed * MathUtils.sinDeg(kartSprite.getRotation()) < 0)) {
                angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) - (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));
            } else {
                angle = 0;
            }
            kartBody.setLinearVelocity(angle, speed * MathUtils.cosDeg(kartSprite.getRotation()));
        }
    }

    public void keepVelocity()
    {
        float angle;
        if (speed * MathUtils.sinDeg(kartSprite.getRotation()) > 0) {
            angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) - (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));
        } else if ((speed * MathUtils.sinDeg(kartSprite.getRotation()) < 0)) {
            angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) - (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));
        } else {
            angle = 0;
        }
        kartBody.setLinearVelocity(angle, speed * MathUtils.cosDeg(kartSprite.getRotation()));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode == Input.Keys.UP)
        {
            timerUp = new Timer(true);
            timerUp.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(speed > 0)
                    {
                        speed = speed * 0.95f;
                        keepVelocity();
                    }
                }
            }, 0, 100);
        }
        if(keycode == Input.Keys.LEFT)
        {
            timerLeft = new Timer(true);
            timerLeft.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(torque > 0)
                    {
                        if ((torque - 0.25f) < 0 || torque < 0.25)
                        {
                            torque = 0;
                            kartBody.setAngularVelocity(torque);
                        }
                        else
                        {
                            torque = torque - 0.25f;
                            kartBody.setAngularVelocity(torque);
                            keepVelocity();
                        }
                    }
                    else
                    {
                        timerLeft.cancel();
                    }
                }
            }, 0, 250);
        }
        if(keycode == Input.Keys.RIGHT)
        {
            timerRight = new Timer(true);
            timerRight.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(torque < 0)
                    {
                        if ((torque + 0.25f) > 0 || torque > -0.25)
                        {
                            torque = 0;
                            kartBody.setAngularVelocity(torque);
                        }
                        else
                        {
                            torque = torque + 0.25f;
                            kartBody.setAngularVelocity(torque);
                            keepVelocity();
                        }
                    }
                    else
                    {
                        timerRight.cancel();
                    }
                }
            }, 0, 250);
        }
        if(keycode == Input.Keys.DOWN)
        {
            timerDown = new Timer(true);
            timerDown.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    speed = speed * 0.95f;
                    keepVelocity();
                }
            },0, 100);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

