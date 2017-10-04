package com.mygdx.game.Gameplay;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class Car extends ApplicationAdapter implements ApplicationListener ,InputProcessor
{
    SpriteBatch batch;
    Texture track;
    Texture kart;
    Sprite kartSprite;
    World world;
    Body kartBody;
    private float posX, posY;

    float speed = 1f;
    float maxspeed = 500f;

    @Override
    public void create () {
        batch = new SpriteBatch();
        track = new Texture("core\\assets\\Map.png");
        kart = new Texture("core\\assets\\Kart.png");
        kartSprite = new Sprite(kart);
        kartSprite.setPosition(posX, posY);
        Gdx.input.setInputProcessor(this);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX,posY);
        world = new World(new Vector2(0, 0), true);
        kartBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(kartSprite.getWidth()/2, kartSprite.getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        kartBody.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void render ()
    {

        //torque test
        world.step(1f/60f, 6, 2);
        //kartBody.applyTorque(torque,true);
        kartSprite.setPosition(kartBody.getPosition().x,kartBody.getPosition().y);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            /*if(speed *1.10 > maxspeed)
            {
                speed = maxspeed;
                if(Gdx.input.isKeyPressed(Input.Keys.UP))
                {
                    speed = speed * 1.10f;
                    kartBody.setLinearVelocity(speed, speed);
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
                {
                    speed = speed * 1.10f;
                    kartBody.setLinearVelocity(speed, -speed);
                }
                speed = speed * 1.10f;
                kartBody.setLinearVelocity(speed, 0);
            }
            else
            {
                if(Gdx.input.isKeyPressed(Input.Keys.UP))
                {
                    speed = speed * 1.10f;
                    kartBody.setLinearVelocity(speed, speed);
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
                {
                    speed = speed * 1.10f;
                    kartBody.setLinearVelocity(speed, -speed);
                }
                speed = speed * 1.10f;
                kartBody.setLinearVelocity(speed, 0);
            }*/
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            /*if(Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                posX -= 3;
                posY += 3;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                posX -= 3;
                posY -= 3;
            }
            posX -= 3;*/
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if(speed == 0 || (speed <=0 && speed >= -3))
            {
                speed = 1;
            }
            else if(speed * 1.02f>= maxspeed)
            {
                speed = maxspeed;
            }
            else if(speed < 0)
            {
                speed = speed * 0.98f;
                kartBody.setLinearVelocity(0, speed);
            }
            else if(speed >= 0)
            {
                speed = speed * 1.02f;
                kartBody.setLinearVelocity(0, speed);
            }

        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            if(speed == 0 || (speed >= 0 && speed <= 3))
            {
                speed = -1;
            }
            else if(speed < 0)
            {
                speed = speed * 1.02f;
                kartBody.setLinearVelocity(0, speed);
            }
            else if(speed > 0)
            {
                speed = speed *0.98f;
                kartBody.setLinearVelocity(0, speed);
            }
        }

        batch.draw(track, 0, 0);
        batch.draw(kartSprite, kartSprite.getX(), kartSprite.getY(),kartSprite.getOriginX(), kartSprite.getOriginY(), kartSprite.getWidth(),kartSprite.getHeight(),kartSprite.getScaleX(),kartSprite.getScaleY(),kartSprite.getRotation());
        batch.end();
    }

    @Override
    public void dispose () {
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
