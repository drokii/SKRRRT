package Menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Lobby> lobbies;

    public Menu(){
        this.lobbies = new ArrayList<Lobby>();
    }

    public void createLobby(String name, String map){
        Lobby lobby = new Lobby(name, map);
    }
}
