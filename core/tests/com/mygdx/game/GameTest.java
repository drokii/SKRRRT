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
            @Override public void create() {/*sonarqube*/}
            @Override public void resize(int width, int height) {/*sonarqube*/}
            @Override public void render() {/*sonarqube*/}
            @Override public void pause() {/*sonarqube*/}
            @Override public void resume() {/*sonarqube*/}
            @Override public void dispose() {/*sonarqube*/}
        });
    }

    @After
    public void cleanUp() {
        application.exit();
        application = null;
    }
}
