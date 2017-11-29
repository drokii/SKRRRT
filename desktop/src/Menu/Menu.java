package Menu;

import com.mygdx.game.Networking.Client.LobbyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Lobby> lobbies;

    public Menu(){
        this.lobbies = new ArrayList<Lobby>();
    }

    public List<Lobby> getLobbies(){
        return lobbies;
    }

    public void createLobby(String name, String map) throws IOException {
        Lobby lobby = new Lobby(name, map);

        LobbyClient client = new LobbyClient();
        client.CreateLobby(name);
    }

    public void setLobbies(List<Lobby> lobbyList)
    {
        lobbies = lobbyList;
    }

}
