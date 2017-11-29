package com.mygdx.game.Networking.Server;

import com.badlogic.gdx.math.Vector2;

public class PlayerInstance {
    private String nickname;
    private Vector2 speed;
    private float angularSpeed;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public float getAngularSpeed() {
        return angularSpeed;
    }

    public void setAngularSpeed(float angularSpeed) {
        this.angularSpeed = angularSpeed;
    }

    public PlayerInstance(String nickname, Vector2 speed, float angularSpeed) {
        this.nickname = nickname;
        this.speed = speed;
        this.angularSpeed = angularSpeed;
    }
}
