package Menu;

import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.Enums.ESkin;

public class Player {
    private Car car;
    private ESkin carSkin;
    private String name;

    public Player(String name){
        this.name = name;
    }

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
}
