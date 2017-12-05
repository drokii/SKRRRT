package Menu;

import MenuScreen.MatchScreen;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Networking.Client.LobbyClient;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.RaceGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Lobby> lobbies;
    private LobbyClient client;
    private RaceGame game;
    private Player currentPlayer;

    public Menu(RaceGame game) throws IOException {
        client = new LobbyClient(this);
        this.lobbies = new ArrayList<Lobby>();
        this.game = game;
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
        currentPlayer = player;
        client.joinLobby(lobbyName, player);
    }

    public void setLobbyPlayers(final Lobby lobby)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new MatchScreen(game, currentPlayer, lobby, Menu.this ));
                    }
                });
            }
        }).start();
    }

    public void getLobbiesRequest()
    {

    }
}
