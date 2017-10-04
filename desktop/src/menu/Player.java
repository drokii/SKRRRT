package menu;

import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.Enums.ESkin;

public class Player {
    private Car car;
    private String name;
    private ESkin carSkin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ESkin getCarSkin() {
        return carSkin;
    }

    public void setCarSkin(ESkin carSkin) {
        this.carSkin = carSkin;
    }

    public Player(String name){
        this.name = name;
    }
}
