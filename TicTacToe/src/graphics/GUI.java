package graphics;

import com.jtattoo.plaf.hifi.HiFiBorderFactory;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import game.GameEngine;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dulanjaya Tennekoon
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    private CardLayout cardLayout = new CardLayout();
    Icon cross;
    Icon ring;
    Icon nextIcon;
    boolean state = true;
    private GameEngine gameEngine = new GameEngine();

    public GUI() {
        initComponents();
        startUpComponents();
        nextIcon = cross;
    }

    public void markBox(int pos) {
        nextIcon = ring;
        switch (pos) {
            case (0): {
                Ibox1.setIcon(nextIcon);
                break;
            }
            case (1): {
                Ibox2.setIcon(nextIcon);
                break;
            }
            case (2): {
                Ibox3.setIcon(nextIcon);
                break;
            }
            case (3): {
                Ibox4.setIcon(nextIcon);
                break;
            }
            case (4): {
                Ibox5.setIcon(nextIcon);
                break;
            }
            case (5): {
                Ibox6.setIcon(nextIcon);
                break;
            }
            case (6): {
                Ibox7.setIcon(nextIcon);
                break;
            }
            case (7): {
                Ibox8.setIcon(nextIcon);
                break;
            }
            case (8): {
                Ibox9.setIcon(nextIcon);
                break;
            }
        }
        nextIcon = cross;
    }

    public void makeMove(int row, int column) {
        gameEngine.play(row, column);
        int ret_code = gameEngine.play();
        switch (ret_code) {
            case (1):
                int msg1 = JOptionPane.showConfirmDialog(this, "Congradulations! You Won. Do You Want to Play a New Game?",
                        "Tic Tac Toe Referee", JOptionPane.YES_NO_OPTION);
                if (msg1 == JOptionPane.YES_OPTION) {
                    //clean the game
                }
                //player wins
                break;
            case (2):
                markBox(gameEngine.getCPUMove());
                int msg = JOptionPane.showConfirmDialog(this, "Sorry! Computer Won. Do You Want to Play a New Game?",
                        "Tic Tac Toe Referee", JOptionPane.YES_NO_OPTION);
                if (msg == JOptionPane.YES_OPTION) {
                    //clean the game
                }
                //computer wins
                return;
            case (3):
                markBox(gameEngine.getCPUMove());
                JOptionPane.showMessageDialog(this, "This is a Draw!");
                //draw
                return;
        }
        markBox(gameEngine.getCPUMove());
        if (gameEngine.isADraw()) {
            JOptionPane.showMessageDialog(this, "This is a Draw!");
            int msg = JOptionPane.showConfirmDialog(this, "This is a Draw! Do You Want to Play a New Game?",
                    "Tic Tac Toe Referee", JOptionPane.YES_NO_OPTION);
            if (msg == JOptionPane.YES_OPTION) {
                //clean the game
            }
        }
    }

    private void startUpComponents() {
        this.setLocationRelativeTo(null);
        setLayout();
        addListners();
        loadPlaySymbol();
        addAboutText();
    }

    private void setLayout() {
        pnlLayout.setLayout(cardLayout);
        pnlLayout.add("pnlMenu", pnlMenu);
        pnlLayout.add("pnlNewGame", pnlNewGame);
        pnlLayout.add("pnlGame", pnlGame);
        pnlLayout.add("pnlSinglePlayerGameMode", pnlSinglePlayerGameMode);
        pnlLayout.add("pnlAbout", pnlAbout);
    }

    private void loadPlaySymbol() {
        //handle exception : Logger Class
        URL crossUrl = this.getClass().getResource("/resources/cross.png");
        URL ringUrl = this.getClass().getResource("/resources/ring.png");
        cross = new ImageIcon(crossUrl);
        ring = new ImageIcon(ringUrl);
    }

    private void addListners() {
        box1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box1.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box1.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox1.setIcon(nextIcon);
                makeMove(0, 0);

            }

        });
        box2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box2.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box2.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox2.setIcon(nextIcon);
                makeMove(0, 1);
            }
        });
        box3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box3.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box3.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox3.setIcon(nextIcon);
                makeMove(0, 2);
            }
        });
        box4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box4.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box4.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox4.setIcon(nextIcon);
                makeMove(1, 0);
            }
        });
        box5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box5.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box5.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox5.setIcon(nextIcon);
                makeMove(1, 1);
            }
        });
        box6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box6.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box6.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox6.setIcon(nextIcon);
                makeMove(1, 2);
            }
        });
        box7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box7.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box7.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox7.setIcon(nextIcon);
                makeMove(2, 0);
            }
        });
        box8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box8.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box8.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox8.setIcon(nextIcon);
                makeMove(2, 1);
            }
        });
        box9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                box9.setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box9.setBorder(BorderFactory.createEtchedBorder()); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Ibox9.setIcon(nextIcon);
                makeMove(2, 2);
            }
        });
    }

    private void addAboutText() {
        lblAboutTxt.setText("<html> The Tic Tac Toe game has been developed as a project, done<br>under  "
                + "the  course  module  CS2202 - Programming Challenge."
                + "<br>The game has 2 player  modes, Multiplayer and Singleplayer."
                + "<br>Singleplayer game mode has been developed with an AI player<br>"
                + "which comprises with three difficulty levels.  "
                + "Multiplayer<br>game mode has been developed to play a tic tac toe session<br>"
                + "with another player having the  same  game over an internet<br>connection. "
                + "The game has been developed  by Nadheesh Jihan<br>and Dulanjaya  Tennekoon,  undergraduates,  Department  of"
                + "<br>Computer Science and  Engineering, University of Moratuwa,<br>Sri Lanka. All Rights Reserved.</html>");
    }

    private void addGameWelcomeName() {
        txtPlayerName.addCaretListener(new CaretListener() {

            @Override
            public void caretUpdate(CaretEvent e) {
                lblGameWelcome.setText(lblGameWelcome.getText().concat(((JTextField) e.getSource()).getText()));
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlLayout = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        pnlSwitches_MainMenu = new javax.swing.JPanel();
        btnContinue = new javax.swing.JButton();
        btnNewGame = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblCopyRight = new javax.swing.JLabel();
        pnlHeadingTicTacToe = new javax.swing.JPanel();
        lblHeadingTicTacToe = new javax.swing.JLabel();
        pnlNewGame = new javax.swing.JPanel();
        pnlNewGameHeading = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlNewGameModeSwitches = new javax.swing.JPanel();
        pnlGameModeButtons = new javax.swing.JPanel();
        btnSinglePlayer = new javax.swing.JButton();
        btnMultiPlayer = new javax.swing.JButton();
        btnBack6 = new javax.swing.JButton();
        pnlSinglePlayerGameMode = new javax.swing.JPanel();
        pnlModeSelection = new javax.swing.JPanel();
        pnlGameInfo = new javax.swing.JPanel();
        pnlModes = new javax.swing.JPanel();
        rbHardMode = new javax.swing.JRadioButton();
        rbMediumMode = new javax.swing.JRadioButton();
        rbEasyMode = new javax.swing.JRadioButton();
        chkFirstPlayerPC = new javax.swing.JCheckBox();
        txtPlayerName = new javax.swing.JTextField();
        lblPlayerName = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        btnBackFromSinglePlayer = new javax.swing.JButton();
        pnlSinglePlayerHeading = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pnlGame = new javax.swing.JPanel();
        pnlGameBoard = new javax.swing.JPanel();
        box1 = new javax.swing.JPanel();
        Ibox1 = new javax.swing.JLabel();
        box2 = new javax.swing.JPanel();
        Ibox2 = new javax.swing.JLabel();
        box3 = new javax.swing.JPanel();
        Ibox3 = new javax.swing.JLabel();
        box4 = new javax.swing.JPanel();
        Ibox4 = new javax.swing.JLabel();
        box5 = new javax.swing.JPanel();
        Ibox5 = new javax.swing.JLabel();
        box6 = new javax.swing.JPanel();
        Ibox6 = new javax.swing.JLabel();
        box7 = new javax.swing.JPanel();
        Ibox7 = new javax.swing.JLabel();
        box8 = new javax.swing.JPanel();
        Ibox8 = new javax.swing.JLabel();
        box9 = new javax.swing.JPanel();
        Ibox9 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        lblGameWelcome = new javax.swing.JLabel();
        lblGameGameMode = new javax.swing.JLabel();
        pnlAbout = new javax.swing.JPanel();
        lblAboutHeading = new javax.swing.JLabel();
        lblAboutTxt = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe 1.0");
        setResizable(false);

        pnlLayout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        pnlLayout.setLayout(new java.awt.CardLayout());

        pnlSwitches_MainMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnContinue.setText("Continue");
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
            }
        });

        btnNewGame.setText("New Game");
        btnNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGameActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jButton1.setText("High Scores");

        jButton2.setText("About");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSwitches_MainMenuLayout = new javax.swing.GroupLayout(pnlSwitches_MainMenu);
        pnlSwitches_MainMenu.setLayout(pnlSwitches_MainMenuLayout);
        pnlSwitches_MainMenuLayout.setHorizontalGroup(
            pnlSwitches_MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSwitches_MainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSwitches_MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(btnNewGame, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(btnContinue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSwitches_MainMenuLayout.setVerticalGroup(
            pnlSwitches_MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSwitches_MainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCopyRight.setText("© CodeHikers Inc.");

        lblHeadingTicTacToe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/GameName.png"))); // NOI18N

        javax.swing.GroupLayout pnlHeadingTicTacToeLayout = new javax.swing.GroupLayout(pnlHeadingTicTacToe);
        pnlHeadingTicTacToe.setLayout(pnlHeadingTicTacToeLayout);
        pnlHeadingTicTacToeLayout.setHorizontalGroup(
            pnlHeadingTicTacToeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadingTicTacToeLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblHeadingTicTacToe)
                .addGap(40, 40, 40))
        );
        pnlHeadingTicTacToeLayout.setVerticalGroup(
            pnlHeadingTicTacToeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadingTicTacToeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeadingTicTacToe)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pnlSwitches_MainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(lblCopyRight, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlHeadingTicTacToe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHeadingTicTacToe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCopyRight)
                    .addComponent(pnlSwitches_MainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlLayout.add(pnlMenu, "card2");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/GameMode.png"))); // NOI18N

        javax.swing.GroupLayout pnlNewGameHeadingLayout = new javax.swing.GroupLayout(pnlNewGameHeading);
        pnlNewGameHeading.setLayout(pnlNewGameHeadingLayout);
        pnlNewGameHeadingLayout.setHorizontalGroup(
            pnlNewGameHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameHeadingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(46, 46, 46))
        );
        pnlNewGameHeadingLayout.setVerticalGroup(
            pnlNewGameHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlGameModeButtons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSinglePlayer.setText("Single Player");
        btnSinglePlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed(evt);
            }
        });

        btnMultiPlayer.setText("Multi Player");
        btnMultiPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultiPlayerbtnMultiPlayer4btnMultiPlayerActionPerformed(evt);
            }
        });

        btnBack6.setText("Back");
        btnBack6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack6btnBack4btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGameModeButtonsLayout = new javax.swing.GroupLayout(pnlGameModeButtons);
        pnlGameModeButtons.setLayout(pnlGameModeButtonsLayout);
        pnlGameModeButtonsLayout.setHorizontalGroup(
            pnlGameModeButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameModeButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGameModeButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnMultiPlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSinglePlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(btnBack6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGameModeButtonsLayout.setVerticalGroup(
            pnlGameModeButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameModeButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMultiPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlNewGameModeSwitchesLayout = new javax.swing.GroupLayout(pnlNewGameModeSwitches);
        pnlNewGameModeSwitches.setLayout(pnlNewGameModeSwitchesLayout);
        pnlNewGameModeSwitchesLayout.setHorizontalGroup(
            pnlNewGameModeSwitchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewGameModeSwitchesLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(pnlGameModeButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );
        pnlNewGameModeSwitchesLayout.setVerticalGroup(
            pnlNewGameModeSwitchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameModeSwitchesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(pnlGameModeButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlNewGameLayout = new javax.swing.GroupLayout(pnlNewGame);
        pnlNewGame.setLayout(pnlNewGameLayout);
        pnlNewGameLayout.setHorizontalGroup(
            pnlNewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameLayout.createSequentialGroup()
                .addGroup(pnlNewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlNewGameHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlNewGameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlNewGameModeSwitches, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlNewGameLayout.setVerticalGroup(
            pnlNewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlNewGameHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNewGameModeSwitches, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlLayout.add(pnlNewGame, "card7");

        buttonGroup1.add(rbHardMode);
        rbHardMode.setSelected(true);
        rbHardMode.setText("Hard Mode");

        buttonGroup1.add(rbMediumMode);
        rbMediumMode.setText("Medium Mode");

        buttonGroup1.add(rbEasyMode);
        rbEasyMode.setText("Easy Mode");

        chkFirstPlayerPC.setText("First Player is Computer");

        javax.swing.GroupLayout pnlModesLayout = new javax.swing.GroupLayout(pnlModes);
        pnlModes.setLayout(pnlModesLayout);
        pnlModesLayout.setHorizontalGroup(
            pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModesLayout.createSequentialGroup()
                        .addComponent(rbMediumMode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(chkFirstPlayerPC))
                    .addGroup(pnlModesLayout.createSequentialGroup()
                        .addGroup(pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbHardMode)
                            .addComponent(rbEasyMode))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlModesLayout.setVerticalGroup(
            pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbHardMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbMediumMode)
                    .addComponent(chkFirstPlayerPC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbEasyMode)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        txtPlayerName.setToolTipText("Name");

        lblPlayerName.setText("Player Name");

        btnPlay.setText("Play");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnBackFromSinglePlayer.setText("Back");
        btnBackFromSinglePlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackFromSinglePlayerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGameInfoLayout = new javax.swing.GroupLayout(pnlGameInfo);
        pnlGameInfo.setLayout(pnlGameInfoLayout);
        pnlGameInfoLayout.setHorizontalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGameInfoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBackFromSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlGameInfoLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlModes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                                .addComponent(lblPlayerName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 63, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlGameInfoLayout.setVerticalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGameInfoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlayerName)
                    .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(pnlModes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPlay)
                    .addComponent(btnBackFromSinglePlayer))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlModeSelectionLayout = new javax.swing.GroupLayout(pnlModeSelection);
        pnlModeSelection.setLayout(pnlModeSelectionLayout);
        pnlModeSelectionLayout.setHorizontalGroup(
            pnlModeSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModeSelectionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlModeSelectionLayout.setVerticalGroup(
            pnlModeSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModeSelectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/SinglePlayer.png"))); // NOI18N

        javax.swing.GroupLayout pnlSinglePlayerHeadingLayout = new javax.swing.GroupLayout(pnlSinglePlayerHeading);
        pnlSinglePlayerHeading.setLayout(pnlSinglePlayerHeadingLayout);
        pnlSinglePlayerHeadingLayout.setHorizontalGroup(
            pnlSinglePlayerHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSinglePlayerHeadingLayout.setVerticalGroup(
            pnlSinglePlayerHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSinglePlayerHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSinglePlayerGameModeLayout = new javax.swing.GroupLayout(pnlSinglePlayerGameMode);
        pnlSinglePlayerGameMode.setLayout(pnlSinglePlayerGameModeLayout);
        pnlSinglePlayerGameModeLayout.setHorizontalGroup(
            pnlSinglePlayerGameModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSinglePlayerGameModeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSinglePlayerGameModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlModeSelection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSinglePlayerHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSinglePlayerGameModeLayout.setVerticalGroup(
            pnlSinglePlayerGameModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSinglePlayerGameModeLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(pnlSinglePlayerHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(pnlModeSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pnlLayout.add(pnlSinglePlayerGameMode, "card5");

        pnlGameBoard.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlGameBoard.setLayout(new java.awt.GridLayout(3, 3));

        box1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box1Layout = new javax.swing.GroupLayout(box1);
        box1.setLayout(box1Layout);
        box1Layout.setHorizontalGroup(
            box1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Ibox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
        );
        box1Layout.setVerticalGroup(
            box1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Ibox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );

        pnlGameBoard.add(box1);

        box2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box2Layout = new javax.swing.GroupLayout(box2);
        box2.setLayout(box2Layout);
        box2Layout.setHorizontalGroup(
            box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box2Layout.setVerticalGroup(
            box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box2);

        box3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box3Layout = new javax.swing.GroupLayout(box3);
        box3.setLayout(box3Layout);
        box3Layout.setHorizontalGroup(
            box3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box3Layout.setVerticalGroup(
            box3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box3);

        box4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box4Layout = new javax.swing.GroupLayout(box4);
        box4.setLayout(box4Layout);
        box4Layout.setHorizontalGroup(
            box4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box4Layout.setVerticalGroup(
            box4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box4);

        box5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box5Layout = new javax.swing.GroupLayout(box5);
        box5.setLayout(box5Layout);
        box5Layout.setHorizontalGroup(
            box5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box5Layout.setVerticalGroup(
            box5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box5);

        box6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box6Layout = new javax.swing.GroupLayout(box6);
        box6.setLayout(box6Layout);
        box6Layout.setHorizontalGroup(
            box6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box6Layout.setVerticalGroup(
            box6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box6);

        box7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box7Layout = new javax.swing.GroupLayout(box7);
        box7.setLayout(box7Layout);
        box7Layout.setHorizontalGroup(
            box7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box7Layout.setVerticalGroup(
            box7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box7);

        box8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box8Layout = new javax.swing.GroupLayout(box8);
        box8.setLayout(box8Layout);
        box8Layout.setHorizontalGroup(
            box8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box8Layout.setVerticalGroup(
            box8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box8);

        box9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box9Layout = new javax.swing.GroupLayout(box9);
        box9.setLayout(box9Layout);
        box9Layout.setHorizontalGroup(
            box9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box9Layout.setVerticalGroup(
            box9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard.add(box9);

        jButton7.setText("Home");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        lblGameWelcome.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        lblGameWelcome.setText("Welcome ");

        lblGameGameMode.setText("Game Mode : ");

        javax.swing.GroupLayout pnlGameLayout = new javax.swing.GroupLayout(pnlGame);
        pnlGame.setLayout(pnlGameLayout);
        pnlGameLayout.setHorizontalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameLayout.createSequentialGroup()
                .addGroup(pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblGameGameMode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7))
                    .addGroup(pnlGameLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(pnlGameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 95, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnlGameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGameWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGameLayout.setVerticalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblGameWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlGameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(lblGameGameMode))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pnlLayout.add(pnlGame, "card3");

        lblAboutHeading.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAboutHeading.setText("About");

        lblAboutTxt.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        lblAboutTxt.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAboutLayout = new javax.swing.GroupLayout(pnlAbout);
        pnlAbout.setLayout(pnlAboutLayout);
        pnlAboutLayout.setHorizontalGroup(
            pnlAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAboutLayout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(pnlAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAboutLayout.createSequentialGroup()
                        .addGroup(pnlAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAboutHeading)
                            .addComponent(lblAboutTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAboutLayout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pnlAboutLayout.setVerticalGroup(
            pnlAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAboutLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(lblAboutHeading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAboutTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );

        pnlLayout.add(pnlAbout, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGameActionPerformed
        cardLayout.show(pnlLayout, "pnlNewGame");
    }//GEN-LAST:event_btnNewGameActionPerformed

    private void btnContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnContinueActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        cardLayout.show(pnlLayout, "pnlGame");
        lblGameWelcome.setText("Welcome ".concat(txtPlayerName.getText()));
        Enumeration<AbstractButton> modeSelector = buttonGroup1.getElements();
        int modeCount = 0;
        while (modeSelector.hasMoreElements()) {
            JRadioButton modeSelect = (JRadioButton) modeSelector.nextElement();
            if (modeSelect.isSelected()) {
                gameEngine = new GameEngine(modeCount, txtPlayerName.getText(), !chkFirstPlayerPC.isSelected());
                lblGameGameMode.setText("Game Mode : ".concat(modeSelect.getText()));
            }
        };
        if (chkFirstPlayerPC.isSelected()) {
            gameEngine.play();
            markBox(gameEngine.getCPUMove());
        }

    }//GEN-LAST:event_btnPlayActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cardLayout.show(pnlLayout, "pnlAbout");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnBack6btnBack4btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack6btnBack4btnBackActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
    }//GEN-LAST:event_btnBack6btnBack4btnBackActionPerformed

    private void btnMultiPlayerbtnMultiPlayer4btnMultiPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultiPlayerbtnMultiPlayer4btnMultiPlayerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMultiPlayerbtnMultiPlayer4btnMultiPlayerActionPerformed

    private void btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed
        cardLayout.show(pnlLayout, "pnlSinglePlayerGameMode");
    }//GEN-LAST:event_btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnBackFromSinglePlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackFromSinglePlayerActionPerformed
        cardLayout.show(pnlLayout, "pnlNewGame");
    }//GEN-LAST:event_btnBackFromSinglePlayerActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
        Ibox1.setIcon(null);
        Ibox2.setIcon(null);
        Ibox3.setIcon(null);
        Ibox4.setIcon(null);
        Ibox5.setIcon(null);
        Ibox6.setIcon(null);
        Ibox7.setIcon(null);
        Ibox8.setIcon(null);
        Ibox9.setIcon(null);
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    //javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    //UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                    Properties prop = new Properties();
                    prop.put("LogoString", "Tic Tac Toe 1.0");
                    HiFiLookAndFeel.setCurrentTheme(prop);
                    UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ibox1;
    private javax.swing.JLabel Ibox2;
    private javax.swing.JLabel Ibox3;
    private javax.swing.JLabel Ibox4;
    private javax.swing.JLabel Ibox5;
    private javax.swing.JLabel Ibox6;
    private javax.swing.JLabel Ibox7;
    private javax.swing.JLabel Ibox8;
    private javax.swing.JLabel Ibox9;
    private javax.swing.JPanel box1;
    private javax.swing.JPanel box2;
    private javax.swing.JPanel box3;
    private javax.swing.JPanel box4;
    private javax.swing.JPanel box5;
    private javax.swing.JPanel box6;
    private javax.swing.JPanel box7;
    private javax.swing.JPanel box8;
    private javax.swing.JPanel box9;
    private javax.swing.JButton btnBack6;
    private javax.swing.JButton btnBackFromSinglePlayer;
    private javax.swing.JButton btnContinue;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnMultiPlayer;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnSinglePlayer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkFirstPlayerPC;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblAboutHeading;
    private javax.swing.JLabel lblAboutTxt;
    private javax.swing.JLabel lblCopyRight;
    private javax.swing.JLabel lblGameGameMode;
    private javax.swing.JLabel lblGameWelcome;
    private javax.swing.JLabel lblHeadingTicTacToe;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JPanel pnlAbout;
    private javax.swing.JPanel pnlGame;
    private javax.swing.JPanel pnlGameBoard;
    private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlGameModeButtons;
    private javax.swing.JPanel pnlHeadingTicTacToe;
    private javax.swing.JPanel pnlLayout;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlModeSelection;
    private javax.swing.JPanel pnlModes;
    private javax.swing.JPanel pnlNewGame;
    private javax.swing.JPanel pnlNewGameHeading;
    private javax.swing.JPanel pnlNewGameModeSwitches;
    private javax.swing.JPanel pnlSinglePlayerGameMode;
    private javax.swing.JPanel pnlSinglePlayerHeading;
    private javax.swing.JPanel pnlSwitches_MainMenu;
    private javax.swing.JRadioButton rbEasyMode;
    private javax.swing.JRadioButton rbHardMode;
    private javax.swing.JRadioButton rbMediumMode;
    private javax.swing.JTextField txtPlayerName;
    // End of variables declaration//GEN-END:variables
}
