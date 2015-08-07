/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkEngine;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Dulanjaya Tennekoon
 */
public class GameServer {

    //this is the game server that is needed to create from one player
    private boolean serverStartFlag = false;
    private boolean serverStoppedFlag = false;

    public void startGameServer(int port) throws IOException {
        ServerSocket clientListner = new ServerSocket(port);
        System.out.println("Tic Tac Toe Server is Running");
        serverStartFlag = true;

        System.out.println("Server has started");
        try {
            MultiplayerGameEngine game = new MultiplayerGameEngine();
            MultiplayerGameEngine.Player playerX = game.new Player(clientListner.accept(), 'X');
            MultiplayerGameEngine.Player playerO = game.new Player(clientListner.accept(), 'O');
            playerX.setOpponent(playerO);
            playerO.setOpponent(playerX);
            game.currentPlayer = playerX;
            playerX.start();
            playerO.start();
        } finally {
            serverStoppedFlag = true;
            clientListner.close();
        }

    }
}
