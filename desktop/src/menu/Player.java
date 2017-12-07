package Menu;

import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.Enums.ESkin;

public class Player {
    private ESkin carSkin;
    private String name;

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
