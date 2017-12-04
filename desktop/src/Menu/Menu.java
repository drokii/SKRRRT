package Menu;

import com.mygdx.game.Networking.Client.LobbyClient;
import com.mygdx.game.Networking.Lobby;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Lobby> lobbies;
    private LobbyClient client;
    public Menu() throws IOException {
        client = new LobbyClient(this);
        this.lobbies = new ArrayList<Lobby>();
    }

    public List<Lobby> getLobbies(){
        return lobbies;
    }

    public void createLobby(String name)  {

        client.CreateLobby(name);
    }

    public void removeLobby(Lobby lobby){
        lobbies.remove(lobby);
    }

    public void setLobbies(List<Lobby> lobbyList)
    {
        lobbies = lobbyList;
    }

    public void joinLobby(String lobbyName, Player player)
    {
        client.joinLobby(lobbyName, player);
    }

}
