package Menu;

import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.Enums.ESkin;

import java.io.Serializable;

public class Player implements Serializable{
    private Car car;
    private ESkin carSkin;
    private String name;
    private boolean host;

    public Player(String name){
        this.name = name;
    }

    public Player(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return name;
    }
}
