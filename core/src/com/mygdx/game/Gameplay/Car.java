package com.mygdx.game.Gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Car {

    Texture carImage;

    public Sprite getCarSprite() {
        return carSprite;
    }

    Sprite carSprite;
    BodyDef bodyDef;
    public Body body;
    PolygonShape shape;
    FixtureDef fixtureDef;

    final float PIXELS_TO_METERS = 100f;

    public float getTorque() {
        return torque;
    }

    public void setTorque(float torque) {
        this.torque = torque;
    }

    float torque = 0f;

    public Car(World world, String skin) {
        //Set texture and assign it to sprite
        carImage = new Texture(skin);
        carSprite = new Sprite(carImage);
        carSprite.setPosition(-carSprite.getWidth() / 2, -carSprite.getHeight() / 2);

        //Set regidbody definition
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((carSprite.getX() + carSprite.getWidth() / 2) /
                        PIXELS_TO_METERS,
                (carSprite.getY() + carSprite.getHeight() / 2) / PIXELS_TO_METERS);

        body = world.createBody(bodyDef);

        //Set fixture definition
        shape = new PolygonShape();
        shape.setAsBox(carSprite.getWidth() / 2 / PIXELS_TO_METERS, carSprite.getHeight()
                / 2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void render(){
        body.applyTorque(torque, true );
        carSprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - carSprite.
                        getWidth()/2 ,
                (body.getPosition().y * PIXELS_TO_METERS) -carSprite.getHeight()/2 );
        carSprite.setRotation((float)Math.toDegrees(body.getAngle()));

    }

    public void dispose(){
        carImage.dispose();
    }
}
