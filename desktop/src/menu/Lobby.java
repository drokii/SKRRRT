package menu;


import com.mygdx.game.Player.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    List<Player> players = new ArrayList<Player>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Lobby(String name){
        this.name = name;
    }

    public void addPlayer(){
        throw new NotImplementedException();
    }

    public void startWorld(){
        throw new NotImplementedException();
    }
}

