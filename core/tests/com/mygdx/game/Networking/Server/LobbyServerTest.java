package com.mygdx.game.Networking.Server;

import Menu.Menu;
import com.mygdx.game.Networking.Client.LobbyClient;
import com.mygdx.game.RaceGame;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class LobbyServerTest {

    private static LobbyClient lobbyClient;
    private static Menu menu;

    @BeforeClass
    public static void mainTest(){
        String[] args = null;
        try {
            LobbyServer.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            menu = new Menu(new RaceGame());
            lobbyClient = new LobbyClient(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tests(){
        createLobbyTest();
        joinLobbyTest();
        getLobbyRequestTest();
    }

    public void createLobbyTest() throws InterruptedException {
        lobbyClient.CreateLobby("Test");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals(1, menu.getLobbies().size());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void joinLobbyTest(){
        //Work in progress
//        assertEquals(0, menu.getLobbies().get(0).getPlayers().size());
//
//        lobbyClient.joinLobby("Test", new Player("Test"));
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(1, menu.getLobbies().get(0).getPlayers().size());
//        System.out.println("a");
    }

    public void getLobbyRequestTest() throws InterruptedException {
        Menu menuRequestTest = null;
        LobbyClient lobbyClientRequestTest = null;
        try {
            menuRequestTest = new Menu(new RaceGame());
            lobbyClientRequestTest = new LobbyClient(menuRequestTest);
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }

        lobbyClientRequestTest.getLobbyRequest();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals(1, menuRequestTest.getLobbies().size());
    }
}
