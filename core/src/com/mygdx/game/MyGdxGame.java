package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Map.Map;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Car car;
	private Map map;
	private OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600,600);
		car = new Car(camera);
		map = new Map(car, camera);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		map.render();
		car.render();

//		camera.position.set(car.getKartSprite().getX(), car.getKartSprite().getY(), 0);
//		System.out.println(car.getKartSprite().getOriginX());
//		System.out.println(car.getKartSprite().getX());
//		System.out.println(camera.position.x);
//		System.out.println(car.getKartSprite().getScaleX());
//		System.out.println(car.getKartSprite().getWidth());
//		System.out.println("-----------------");
//		camera.update();
	}

	@Override
	public void dispose () {
	}
}
