package Menu;

import MenuScreen.GameScreen;
import MenuScreen.LobbyScreen;
import MenuScreen.MatchScreen;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Networking.Client.GameClient;
import com.mygdx.game.Networking.Client.LobbyClient;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.Networking.Server.GameServer;
import com.mygdx.game.RaceGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Lobby> lobbies;
    private LobbyClient lobbyClient;
    private GameClient gameClient;
    private RaceGame game;
    private Player currentPlayer;
    private LobbyScreen lobbyScreen;
    private MatchScreen matchScreen;
    private Lobby currentLobby;

    public Menu(RaceGame game, LobbyScreen lobbyScreen) throws IOException {
        lobbyClient = new LobbyClient(this);
        this.lobbies = new ArrayList<Lobby>();
        this.game = game;
        this.lobbyScreen = lobbyScreen;
    }

    public Menu(RaceGame game) throws IOException {
        lobbyClient = new LobbyClient(this);
        this.lobbies = new ArrayList<Lobby>();
        this.game = game;
    }

    public List<Lobby> getLobbies(){
        return lobbies;
    }

    public void createLobby(String name)  {
        lobbyClient.CreateLobby(name);
    }

    public void removeLobby(Lobby lobby){
        lobbies.remove(lobby);
    }

    public void setLobbies(List<Lobby> lobbyList)
    {
        lobbies = lobbyList;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void joinLobby(int index, Player player)
    {
        currentPlayer = player;
        currentLobby = lobbies.get(index);
        lobbyClient.joinLobby(index, player);
        lobbyClient.getReadyPlayers(currentLobby);
    }

    public void playerReady(Lobby lobby)
    {
        lobbyClient.playerReady(lobby, currentPlayer);
    }

    public void setLobbyPlayers(final Lobby lobby)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MatchScreen newMatchScreen = new MatchScreen(game, currentPlayer, lobby, Menu.this );
                        matchScreen = newMatchScreen;
                        game.setScreen(newMatchScreen);
                    }
                });
            }
        }).start();
    }

    public void setReadyPlayers(List<Player> players)
    {
        currentLobby.setReadyPlayers(players);
        if(currentLobby.getReadyPlayers().size() != 0)
        {
            System.out.println(currentLobby.getReadyPlayers().size());
            System.out.println(matchScreen);
            // crashes cus matchscreen is null; only crashes when theres already somebody ready in lobby? otherwise the matchscreen != null...
            matchScreen.getReadyLabelPlayers(currentLobby.getReadyPlayers());
        }
    }

    public void getLobbiesRequest()
    {
        lobbyClient.getLobbyRequest();
    }

    public void refreshLobbies()
    {
        lobbyScreen.refreshLobbies();
    }

    public void gameStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            game.setScreen(new GameScreen(game, currentPlayer));
                            if(currentPlayer.isHost())
                            {
                                //send host ip to lobbyserver and send it to other clients
                                GameServer gameServer = new GameServer(/* add ip for gameserver*/);
                            }
                            gameClient = new GameClient();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    public void setMatchScreen(MatchScreen matchScreen)
    {
        this.matchScreen = matchScreen;
    }
}
