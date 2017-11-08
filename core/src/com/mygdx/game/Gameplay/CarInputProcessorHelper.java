package com.mygdx.game.Gameplay;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Timer;
import java.util.TimerTask;

public class CarInputProcessorHelper implements InputProcessor, ApplicationListener {
    /**
     * This class translates the user input into usable calls for the car class.
     * In addition to translating input, the class is responsible for deacceleration and torque correction of the car.
     * @param timerUp if the forward key is released a timer schedule is going to bring back the speed to zero.
     * @param timerLeft if the turn-left key is released a timer schedule is going to bring back the torque to zero.
     * @param timerRight if the turn-right key is released a timer schedule is going to bring back the torque to zero.
     * @param timerDown if the backward key is released a timer schedule is going to bring back the speed to zero.
     */
    private Car car;
    private float torque;
    private Body kartBody;


    public CarInputProcessorHelper(Car car) {

        Gdx.input.setInputProcessor(this);
        this.car = car;
        torque = car.getTorque();
        kartBody = car.getKartBody();

    }
    //Timers for deacceleration and torque correction
    private Timer timerUp = new Timer(true);
    private Timer timerLeft = new Timer(true);
    private Timer timerRight = new Timer(true);
    private Timer timerDown = new Timer(true);

    public void cancelUpTimer(){
        timerUp.cancel();
    }
    public void cancelDownTimer(){
        timerDown.cancel();
    }

    public void keyPressed() {

        /**
        * Depending on the key pressed the torque or speed of the car should be updated.
        */

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (timerRight != null) {
                timerRight.cancel();
            }
            if ((torque -= 0.025f) < -1f) {
                torque = -1f;
            } else {
                torque -= 0.025f;
            }

            kartBody.setAngularVelocity(torque);

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                car.driveForward(timerUp);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                car.driveBackward(timerDown);
            }

            car.keepVelocity();

        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (timerLeft != null) {
                timerLeft.cancel();
            }
            if ((torque += 0.025f) > 1f) {
                torque = 1f;
            } else {
                torque += 0.025f;
            }

            kartBody.setAngularVelocity(torque);

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                car.driveForward(timerUp);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                car.driveBackward(timerDown);
            }

            car.keepVelocity();

        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            car.driveForward(timerUp);

        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            car.driveBackward(timerDown);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        /**
         * When a key is released the released direction should start to lose its influence on the car over time.
         */
        if (keycode == Input.Keys.W) {
            timerUp = new Timer(true);
            timerUp.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (car.getSpeed() > 0) {
                        car.setSpeed(car.getSpeed() * 0.95f);
                        car.keepVelocity();
                    }
                }
            }, 0, 100);
        }
        if (keycode == Input.Keys.A) {
            timerLeft = new Timer(true);
            timerLeft.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (torque > 0) {
                        if ((torque - 0.25f) < 0 || torque < 0.25) {
                            torque = 0;
                            kartBody.setAngularVelocity(torque);
                        } else {
                            torque = torque - 0.25f;
                            kartBody.setAngularVelocity(torque);
                            car.keepVelocity();
                        }
                    } else {
                        timerLeft.cancel();
                    }
                }
            }, 0, 250);
        }
        if (keycode == Input.Keys.D) {
            timerRight = new Timer(true);
            timerRight.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (torque < 0) {
                        if ((torque + 0.25f) > 0 || torque > -0.25) {
                            torque = 0;
                            kartBody.setAngularVelocity(torque);
                        } else {
                            torque = torque + 0.25f;
                            kartBody.setAngularVelocity(torque);
                            car.keepVelocity();
                        }
                    } else {
                        timerRight.cancel();
                    }
                }
            }, 0, 250);
        }
        if (keycode == Input.Keys.S) {
            timerDown = new Timer(true);
            timerDown.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    car.setSpeed(car.getSpeed() * 0.95f);
                    car.keepVelocity();
                }
            }, 0, 100);
        }
        return true;
    }


    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        keyPressed();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

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