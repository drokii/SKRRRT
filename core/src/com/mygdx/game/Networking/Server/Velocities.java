package com.mygdx.game.Networking.Server;

import com.badlogic.gdx.math.Vector2;

public class Velocities {


    private Vector2 linear;
    private float angular;

    public Velocities(){}

    public Velocities(Vector2 linear, float angular) {
        this.linear = linear;
        this.angular = angular;
    }

    public Vector2 getLinear() {
        return linear;
    }

    public float getAngular() {
        return angular;
    }


}
