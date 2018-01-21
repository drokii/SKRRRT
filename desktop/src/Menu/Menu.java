package Menu;

import MenuScreen.GameScreen;
import MenuScreen.LobbyScreen;
import MenuScreen.MatchScreen;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Gameplay.GameWorld;
import com.mygdx.game.Networking.Client.GameClient;
import com.mygdx.game.Networking.Client.LobbyClient;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.Networking.Server.GameServer;
import com.mygdx.game.RaceGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {
    private static final Logger LOGGER = Logger.getLogger(Menu.class.getName());

    private List<Lobby> lobbies;
    private LobbyClient lobbyClient;
    private GameWorld gameWorld;
    private RaceGame game;
    private Player currentPlayer;
    private LobbyScreen lobbyScreen;
    private MatchScreen matchScreen;
    private Lobby currentLobby;

    public Menu(RaceGame game, LobbyScreen lobbyScreen) throws IOException {
        lobbyClient = new LobbyClient(this);
        this.lobbies = new ArrayList<>();
        this.game = game;
        this.lobbyScreen = lobbyScreen;
    }

    public Menu(RaceGame game) throws IOException {
        lobbyClient = new LobbyClient(this);
        this.lobbies = new ArrayList<>();
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
        new Thread(() -> Gdx.app.postRunnable(() -> {
            MatchScreen newMatchScreen = new MatchScreen(game, currentPlayer, lobby, Menu.this );
            matchScreen = newMatchScreen;
            game.setScreen(newMatchScreen);
        })).start();
    }

    public void setReadyPlayers(List<Player> players)
    {
        currentLobby.setReadyPlayers(players);
        if(currentLobby.getReadyPlayers().size() != 0)
        {
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
        new Thread(() -> Gdx.app.postRunnable(() -> {
            BufferedReader in = null;
            try {
                if(currentPlayer.isHost())
                {
                    //send host ip to lobbyserver and send it to other clients
                    new GameServer();

                    URL whatismyip = new URL("http://checkip.amazonaws.com");
                    in = new BufferedReader(new InputStreamReader(
                            whatismyip.openStream()));

                    String ip = in.readLine(); //you get the IP as a String

                    lobbyClient.CreatedServer(ip, currentLobby);
                }
            } catch (IOException e) {
                LOGGER.log( Level.SEVERE, e.toString(), e );
            } finally {
                try {
                    in.close();
                } catch (Exception e) {
                    LOGGER.log( Level.SEVERE, e.toString(), e );
                }
            }
        })).start();
    }

    public void CreateGameClient(String ip) throws IOException {
        new Thread(() -> Gdx.app.postRunnable(() -> {
            try {
                gameWorld = new GameWorld(game, currentPlayer, ip);
                game.setScreen(new GameScreen(game, currentPlayer, gameWorld));
            } catch (IOException e) {
                LOGGER.log( Level.SEVERE, e.toString(), e );
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log( Level.SEVERE, e.toString(), e );
            } catch (ExecutionException e) {
                LOGGER.log( Level.SEVERE, e.toString(), e );
            }
        })).start();

    }
    public void setMatchScreen(MatchScreen matchScreen)
    {
        this.matchScreen = matchScreen;
    }
}
