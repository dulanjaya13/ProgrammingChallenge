/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.handler;

import game.controller.BoardController;
import game.gui.GUI;
import game.model.Board;
import game.model.BoardData;
import game.model.Player;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import util.Converter;

/**
 *
 * @author Nadheesh
 */
public class SaveHandler {

    

    private ArrayList<BoardData> boardData = new ArrayList();

    public SaveHandler() {
        loadBoard();
    }

    
    public boolean saveGame(Board board, Player player, int mode) throws SQLException {

        String bString = Converter.boardToString(board);
        BoardData bData= new BoardData(bString, player.getPlayerName(), mode, Converter.getTody());
        boardData.add(bData);
        return BoardController.addBoard(bData);

    }

    public String[] getSavedGames() {
        String[] savedGames =new String[boardData.size()];

        int i = 0;
        for (BoardData d : boardData) {
            savedGames[i]=(Converter.getStringDetails(d.getPlayerName(), d.getMode(), d.getSavedDate()));
            i++;
        }

        return savedGames;
    }

    private void loadBoard() {
        try {
            boardData = BoardController.getAllBoards();
        } catch (SQLException ex) {
            Logger.getLogger(SaveHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadGame(GUI gui, int index) throws SQLException {

        BoardData bData = boardData.get(index);

        switch (bData.getMode()) {

            case 0:
                gui.getRbHardMode().setSelected(true);
                break;

            case 1:
                gui.getRbMediumMode().setSelected(true);
                break;

            case 2:
                gui.getRbEasyMode().setSelected(true);
                break;

        }
        //set player name
        JComboBox box = gui.getTxtPlayerName();
        
        for (int i = 0 ; i < box.getModel().getSize(); i ++){
            if (((Player)box.getItemAt(i)).getPlayerName().equals(bData.getPlayerName())){
                box.setSelectedItem(box.getItemAt(i));
            }
        }
        
          gui.setNewGame();
          gui.getGameEngine().setBoard(Converter.stringToBoard(bData.getBoardString()));
          gui.getGameEngine().setHumanFirst();
          
          ArrayList<JLabel> boxes = gui.getBoxesArray();
          
          for (int i = 0 ; i < 9 ; i ++){
              
              if (bData.getBoardString().charAt(i)=='C'){
                  boxes.get(i).setIcon(gui.getCross());
              }else if (bData.getBoardString().charAt(i)=='R'){
                  boxes.get(i).setIcon(gui.getRing());
              }
          }
        BoardController.deleteSavedBoard(bData);
        boardData.remove(bData);
    }

}
