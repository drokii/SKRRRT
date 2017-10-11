package com.mygdx.game
		;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Gameplay.Car;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;

	Car car;
	float torque = 0.0f;
	boolean drawSprite = true;

	final float PIXELS_TO_METERS = 100f;

	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, 0f),true);
		car = new Car(world,"./core/assets/badlogic.jpg" );

		Gdx.input.setInputProcessor(this);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
				getHeight());
	}

	private float elapsed = 0;
	@Override
	public void render() {
		camera.update();

		// Step the physics simulation forward at a rate of 60hz
		world.step(1f/60f, 6, 2);
		car.render();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		// Scale down the sprite batches projection matrix to box2D size
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
				PIXELS_TO_METERS, 0);

		batch.begin();

		if(drawSprite)
			batch.draw(car.getCarSprite(), car.getCarSprite().getX(), car.getCarSprite().getY(),car.getCarSprite().getOriginX(),
					car.getCarSprite().getOriginY(),
					car.getCarSprite().getWidth(),car.getCarSprite().getHeight(),car.getCarSprite().getScaleX(),car.getCarSprite().
							getScaleY(),car.getCarSprite().getRotation());

		batch.end();

	}

	@Override
	public void dispose() {
		car.dispose();
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		// On right or left arrow set the velocity at a fixed rate in that
		// direction
		if(keycode == Input.Keys.RIGHT)
			car.body.setLinearVelocity(1.2f, 0f);
		if(keycode == Input.Keys.LEFT)
			car.body.setLinearVelocity(-1f,0f);

		if(keycode == Input.Keys.UP)
			car.body.applyForceToCenter(0f,10f,true);
		if(keycode == Input.Keys.DOWN)
			car.body.applyForceToCenter(0f, -10f, true);

		// On brackets ( [ ] ) apply torque, either clock or counterclockwise
		if(keycode == Input.Keys.RIGHT_BRACKET)
			car.setTorque(car.getTorque() + 0.1f);
		if(keycode == Input.Keys.LEFT_BRACKET)
			car.setTorque(car.getTorque() -0.1f);

		// Remove the torque using backslash /
		if(keycode == Input.Keys.BACKSLASH)
			car.setTorque(0.0f);
		// If user hits spacebar, reset everything back to normal
		if(keycode == Input.Keys.SPACE) {
			car.body.setLinearVelocity(0f, 0f);
			car.body.setAngularVelocity(0f);
			torque = 0f;
			car.getCarSprite().setPosition(0f,0f);
			car.body.setTransform(0f,0f,0f);
		}

		// The ESC key toggles the visibility of the sprite allow user to see
		// physics debug info
		if(keycode == Input.Keys.ESCAPE)
			drawSprite = !drawSprite;

		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	// On touch we apply force from the direction of the users touch.
	// This could result in the object "spinning"
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		car.body.applyForce(1f,1f,screenX,screenY,true);
		//body.applyTorque(0.4f,true);
		return true;
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
