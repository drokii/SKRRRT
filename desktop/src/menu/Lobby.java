package menu;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Timer;

public class Lobby {
    private int id;
    //private List<Player> players = new ArrayList<Player>();
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lobby(String name){
        throw new NotImplementedException();
    }

    public void addPlayer(Player player){
        throw new NotImplementedException();
    }

    public void startWorld(int lapLimit, Timer timer){
        throw new NotImplementedException();
    }

}

