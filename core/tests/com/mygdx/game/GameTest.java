package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

public class GameTest {
    // This is our "test" application
    private Application application;

    @Before
    public void init() {
        Gdx.gl =  Mockito.mock(GL20.class);
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });
    }

    @After
    public void cleanUp() {
        application.exit();
        application = null;
    }
}
