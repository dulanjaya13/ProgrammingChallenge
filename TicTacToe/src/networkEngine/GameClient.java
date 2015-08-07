/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkEngine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dulanjaya Tennekoon
 */
public class GameClient {

    JPanel gameBoard;
    JLabel messageLabel;
    Icon cross;
    Icon ring;
    JLabel currentBox;
    JLabel opponentName;
    String myName;
    
    Icon icon;
    Icon opponentIcon;

    private int PORT;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public GameClient(final JPanel gameBoard, JLabel messageLabel, String serverAddress, Icon cross, Icon ring, JLabel opponentName, String myName,int PORT) throws ConnectException, IOException {
        this.gameBoard = gameBoard;
        this.messageLabel = messageLabel;
        this.cross = cross;
        this.ring = ring;
        this.opponentName = opponentName;
        this.myName = myName;
        this.PORT = PORT;
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        gameBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel tem = (JPanel) gameBoard.getComponentAt(e.getPoint());
                currentBox = ((JLabel) tem.getComponent(0));
                //System.out.println(gameBoard.getComponentZOrder(tem));
                out.println("MOVE " + Integer.toString(gameBoard.getComponentZOrder(tem)));
            }
        });

    }

    public void Play() throws IOException {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                char mark = response.charAt(8);
                icon = mark == 'X' ? cross : ring;
                opponentIcon = mark == 'X' ? ring : cross;
                //opponentName.setText(response.substring(10));
                //frame.setTitle("Tic Tac Toe - Player " + mark);
            }
            out.println("MY_NAME : " + myName);
            while (true) {
                response = in.readLine();
                System.out.println(response);
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, please wait");
                    currentBox.setIcon(icon);
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    ((JLabel) ((JPanel) (gameBoard.getComponent(loc))).getComponent(0)).setIcon(opponentIcon);
                    ((JLabel) ((JPanel) gameBoard.getComponent(loc)).getComponent(0)).setIcon(opponentIcon);
                    gameBoard.repaint();
                    messageLabel.setText("Opponent Moved, Your Turn!");
                } else if (response.startsWith("VICTORY")) {
                    messageLabel.setText("You Won");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    messageLabel.setText("You Lose");
                    break;
                } else if (response.startsWith("TIE")) {
                    messageLabel.setText("Game Tied");
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                } else if (response.startsWith("OPPONENT_NAME")) {
                    System.out.println("Got opponent name");
                    System.out.println(response);
                    opponentName.setText(response.substring(15));
                } else if (response.startsWith("DIED")) {
                    System.out.println("Reaches here too");
                    messageLabel.setText("Opponent Died");
                }
            }
            out.println("QUIT");
        } finally {
            socket.close();
        }
    }

    public void startClient() throws IOException {
        Play();
    }
}
