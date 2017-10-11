package com.mygdx.game.Gameplay;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.sun.media.jfxmedia.events.PlayerStateEvent;

public class Car extends ApplicationAdapter implements ApplicationListener, InputProcessor {
    private SpriteBatch batch;
    private Texture track;
    private Texture kart;
    private Sprite kartSprite;
    private World world;
    private Body kartBody;
    private float posX, posY;

    private float speed = 1f;
    private float maxspeed = 200f;

    private OrthographicCamera camera;

    float torque = 0f;

    public Car(OrthographicCamera camera) {
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
        world = new World(new Vector2(0, 0), true);
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

    public Body getKartBody(){
        return kartBody;
    }

    public Sprite getKartSprite() {
        return kartSprite;
    }

    @Override
    public void render() {
        //torque test
        world.step(1f / 60f, 6, 2);
        kartBody.applyTorque(torque, true);
        kartSprite.setPosition(kartBody.getPosition().x, kartBody.getPosition().y);

        //Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if((torque -= 0.025f) < -1f)
            {
                torque = -1f;
            }
            else
            {
                torque -= 0.025f;
            }
            kartBody.setAngularVelocity(torque);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if((torque += 0.025f) > 1f)
            {
                torque = 1f;
            }
            else
            {
                torque += 0.025f;
            }
            kartBody.setAngularVelocity(torque);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (speed == 0 || (speed <= 0 && speed >= -3)) {
                speed = 1;
            } else if (speed * 1.02f >= maxspeed) {
                speed = maxspeed;
            } else if (speed < 0) {
                speed = speed * 0.98f;
                kartBody.setLinearVelocity(0, speed);
            }
            if (speed >= 0) {
                speed = speed * 1.02f;
                float angle;
                //System.out.println(kartSprite.getRotation());
                // code hieronder veranderd de rotation wel maar hij is tegelijkertijd ook weer zijn oude value...? BUGGED AF BOI
                /*if(kartSprite.getRotation() >= 360 || kartSprite.getRotation() <= -360)
                {
                    kartSprite.setRotation(0);
                }*/
                //System.out.println(kartSprite.getRotation());
                if (speed * MathUtils.sinDeg(kartSprite.getRotation()) > 0) {
                    angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) - (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));
                } else if ((speed * MathUtils.sinDeg(kartSprite.getRotation()) < 0)) {
                    angle = ((speed * MathUtils.sinDeg(kartSprite.getRotation())) - (speed * MathUtils.sinDeg(kartSprite.getRotation()) * 2));
                } else {
                    angle = 0;
                }
                //System.out.println("PreCalc angle" + speed * MathUtils.sinDeg(kartSprite.getRotation()));
                //System.out.println("AfterCalc angle" + angle);
                kartBody.setLinearVelocity(angle, speed * MathUtils.cosDeg(kartSprite.getRotation()));
            }

        }
        //opposite van key up code maken? //wss gwn allebei de values minnen;
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (speed == 0 || (speed >= 0 && speed <= 3)) {
                speed = -1;
            } else if (speed < 0) {
                speed = speed * 1.02f;
                kartBody.setLinearVelocity(0, speed);
            } else if (speed > 0) {
                speed = speed * 0.98f;
                kartBody.setLinearVelocity(0, speed);
            }
        }
        kartSprite.setRotation((float) Math.toDegrees(kartBody.getAngle()));
        kartSprite.setPosition(kartBody.getTransform().getPosition().x, kartBody.getTransform().getPosition().y);
        //batch.draw(track, 0, 0);
        batch.draw(kartSprite, kartSprite.getX(), kartSprite.getY(), kartSprite.getOriginX()/4, kartSprite.getOriginY()/4, 50, 50, kartSprite.getScaleX(), kartSprite.getScaleY(), kartSprite.getRotation());
        //batch.draw(kartSprite, kartSprite.getX(), kartSprite.getY(),kartSprite.getOriginX(), kartSprite.getOriginY(), kartSprite.getWidth(),kartSprite.getHeight(),kartSprite.getScaleX(),kartSprite.getScaleY(),kartSprite.getRotation());


        //batch.draw(kart, kartBody.getTransform().getPosition().x, kartBody.getTransform().getPosition().y);
        //batch.draw(kart, kartSprite.getX(), kartSprite.getY());
        batch.end();


        camera.position.set(getKartSprite().getX(), getKartSprite().getY(), 0);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        track.dispose();
        kart.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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

