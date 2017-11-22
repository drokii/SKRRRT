package Menu;


import com.mygdx.game.Map.Map;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private String name;
    private Map map;
    private List<Player> players;

    public Lobby(String name, Map map){
        this.name = name;
        this.map = map;
        this.players = new ArrayList<Player>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
