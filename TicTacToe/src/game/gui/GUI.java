package game.gui;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import game.engine.GameEngine;
import game.handler.PlayerHandler;
import game.handler.SaveHandler;
import game.model.Player;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import static java.awt.PageAttributes.MediaType.A;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import networkEngine.GameClient;
import networkEngine.GameServer;

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
    private Icon cross;
    private Icon ring;
    private Icon nextIcon;
    private boolean state;
    private GameEngine gameEngine;
    private boolean isFinish;
    private boolean isStarted;
    private boolean isSinglePlay;
    private final static SaveHandler saveHandler = new SaveHandler();
    private final static PlayerHandler playerHandler = new PlayerHandler();
    private int mode = 0;
    private Thread multiPlayerClient;
    private Thread multiPlayerServer;

    public GUI() {
        state = true;
        gameEngine = new GameEngine();
        isFinish = false;
        isStarted = false;
        initComponents();
        startUpComponents();
        nextIcon = cross;
        addPropertyChangeListener();
    }

    public void markBox(int pos) {
        nextIcon = ring;
        getBoxesArray().get(pos).setIcon(nextIcon);
        nextIcon = cross;
    }

    public void makeMove(int row, int column) {
        gameEngine.play(row, column);
        int ret_code = gameEngine.play();
        switch (ret_code) {
            case (1):
                setFinished();
                int msg1 = JOptionPane.showConfirmDialog(this, "Congratulations! You Won. Do You Want to Play a New Game?",
                        "Tic Tac Toe Referee", JOptionPane.YES_NO_OPTION);
                updateScore(mode, true);
                if (msg1 == JOptionPane.YES_OPTION) {
                    setNewGame();
                    return;
                }
                //player wins
                break;
            case (2):
                setFinished();
                markBox(gameEngine.getCPUMove());
                int msg = JOptionPane.showConfirmDialog(this, "Sorry! Computer Won. Do You Want to Play a New Game?",
                        "Tic Tac Toe Referee", JOptionPane.YES_NO_OPTION);
                if (msg == JOptionPane.YES_OPTION) {
                    setNewGame();
                }
                //computer wins
                return;

            case (3):
                setFinished();
                markBox(gameEngine.getCPUMove());
                msg = -1;
                msg = JOptionPane.showConfirmDialog(this, "This is a Draw! Do You Want to Play a New Game?",
                        "Tic Tac Toe Referee", JOptionPane.YES_NO_OPTION);
                updateScore(mode, false);
                System.out.println(msg);
                if (msg == JOptionPane.YES_OPTION) {
                    setNewGame();
                }
                //draw
                return;
        }
        markBox(gameEngine.getCPUMove());
        if (gameEngine.isADraw()) {
            updateScore(mode, false);
            setFinished();
            int msg = JOptionPane.showConfirmDialog(this, "This is a Draw! Do You Want to Play a New Game?",
                    "Tic Tac Toe Referee", JOptionPane.YES_NO_OPTION);
            System.out.println(msg);
            if (msg == JOptionPane.YES_OPTION) {
                setNewGame();
            }
        }
    }

    public void updateScore(int mode, boolean win_draw) {
        final int hardScore = 30;
        final int mediumScore = 10;
        final int easyScore = 2;
        final int multiplayerScore = 30;
        System.out.println("Executes");
        try {
            switch (mode) {
                case (0):
                    playerHandler.updateScores(gameEngine.getPlayer().getPlayerName(), win_draw ? hardScore : hardScore / 2);
                    break;
                case (1):
                    playerHandler.updateScores(gameEngine.getPlayer().getPlayerName(), win_draw ? mediumScore : mediumScore / 2);
                    break;
                case (2):
                    playerHandler.updateScores(gameEngine.getPlayer().getPlayerName(), win_draw ? easyScore : easyScore / 2);
                    break;
                case (3):
                    playerHandler.updateScores(comboMultiNames.getSelectedItem().toString(), win_draw ? multiplayerScore : multiplayerScore / 2);
                    break;
            }
        } catch (SQLException e) {
            //update logger here
        }
    }

    private void startUpComponents() {
        this.setLocationRelativeTo(null);
        setLayout();
        addListners();
        loadPlaySymbol();
        addAboutText();
        comboNames.setModel(new DefaultComboBoxModel(playerHandler.getPlayers().toArray()));
    }

    private void setLayout() {
        pnlLayout.setLayout(cardLayout);
        pnlLayout.add("pnlMenu", pnlMenu);
        pnlLayout.add("pnlNewGame", pnlNewGame);
        pnlLayout.add("pnlGame", pnlGame);
        pnlLayout.add("pnlSinglePlayerGameMode", pnlSinglePlayerGameMode);
        pnlLayout.add("pnlAbout", pnlAbout);
        pnlLayout.add("pnlContinue", pnlContinue);
        pnlLayout.add("pnlHighScores", pnlHighScores);
        pnlLayout.add("pnlMultiPlayerGame", pnlMultiPlayerGame);
        pnlLayout.add("pnlMultiPlayerGameMode", pnlMultiPlayerGameMode);
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
                if (Ibox1.getIcon() == null) {
                    Ibox1.setIcon(nextIcon);
                    makeMove(0, 0);
                    setStarted();
                }
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
                if (Ibox2.getIcon() == null) {
                    Ibox2.setIcon(nextIcon);
                    makeMove(0, 1);
                    setStarted();
                }
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
                if (Ibox3.getIcon() == null) {
                    Ibox3.setIcon(nextIcon);
                    makeMove(0, 2);
                    setStarted();
                }
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
                if (Ibox4.getIcon() == null) {
                    Ibox4.setIcon(nextIcon);
                    makeMove(1, 0);
                    setStarted();
                }
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
                if (Ibox5.getIcon() == null) {
                    Ibox5.setIcon(nextIcon);
                    makeMove(1, 1);
                    setStarted();
                }
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
                if (Ibox6.getIcon() == null) {
                    Ibox6.setIcon(nextIcon);
                    makeMove(1, 2);
                    setStarted();
                }
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
                if (Ibox7.getIcon() == null) {
                    Ibox7.setIcon(nextIcon);
                    makeMove(2, 0);
                    setStarted();
                }
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
                if (Ibox8.getIcon() == null) {
                    Ibox8.setIcon(nextIcon);
                    makeMove(2, 1);
                    setStarted();
                }
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
                if (Ibox9.getIcon() == null) {
                    Ibox9.setIcon(nextIcon);
                    makeMove(2, 2);
                    setStarted();
                }
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

    private void addPropertyChangeListener() {
        PropertyChangeListener l = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (messageLabel.getText().equals("You Won")) {
                    updateScore(3, true);
                } else if (messageLabel.getText().equals("Game Tied")) {
                    updateScore(3, false);
                }
            }
        };
        messageLabel.addPropertyChangeListener("text", l);
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
        saveProgress = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        progress = new javax.swing.JProgressBar();
        busyProgress = new org.jdesktop.swingx.JXBusyLabel();
        infoLabel = new javax.swing.JLabel();
        pnlLayout = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        pnlSwitches_MainMenu = new javax.swing.JPanel();
        btnContinue = new javax.swing.JButton();
        btnNewGame = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnHighScores = new javax.swing.JButton();
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
        lblPlayerName = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        btnBackFromSinglePlayer = new javax.swing.JButton();
        comboNames = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
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
        pnlContinue = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        savedDetails = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        pnlHighScores = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listName = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listScore = new javax.swing.JList();
        jButton6 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        pnlMultiPlayerGame = new javax.swing.JPanel();
        pnlGameBoard1 = new javax.swing.JPanel();
        box10 = new javax.swing.JPanel();
        Ibox10 = new javax.swing.JLabel();
        box11 = new javax.swing.JPanel();
        Ibox11 = new javax.swing.JLabel();
        box12 = new javax.swing.JPanel();
        Ibox12 = new javax.swing.JLabel();
        box13 = new javax.swing.JPanel();
        Ibox13 = new javax.swing.JLabel();
        box14 = new javax.swing.JPanel();
        Ibox14 = new javax.swing.JLabel();
        box15 = new javax.swing.JPanel();
        Ibox15 = new javax.swing.JLabel();
        box16 = new javax.swing.JPanel();
        Ibox16 = new javax.swing.JLabel();
        box17 = new javax.swing.JPanel();
        Ibox17 = new javax.swing.JLabel();
        box18 = new javax.swing.JPanel();
        Ibox18 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblPortNumber = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblOpponentName = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        pnlMultiPlayerGameMode = new javax.swing.JPanel();
        pnlMultiplayerPane = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlMultiPlayerOptions = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btn = new javax.swing.JButton();
        txtServerPort = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblServerStates = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtClientPort = new javax.swing.JTextField();
        btnConnect = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        comboMultiNames = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        saveProgress.setAlwaysOnTop(true);
        saveProgress.setMinimumSize(new java.awt.Dimension(327, 163));
        saveProgress.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        saveProgress.setUndecorated(true);
        saveProgress.setResizable(false);

        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder1.setShowLeftShadow(true);
        dropShadowBorder1.setShowTopShadow(true);
        jPanel1.setBorder(dropShadowBorder1);

        busyProgress.setBusy(true);

        infoLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        infoLabel.setText("Saving....");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(busyProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                    .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(infoLabel)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(busyProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout saveProgressLayout = new javax.swing.GroupLayout(saveProgress.getContentPane());
        saveProgress.getContentPane().setLayout(saveProgressLayout);
        saveProgressLayout.setHorizontalGroup(
            saveProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, saveProgressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        saveProgressLayout.setVerticalGroup(
            saveProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, saveProgressLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe 1.0");
        setResizable(false);

        pnlLayout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        pnlLayout.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlLayout.setPreferredSize(new java.awt.Dimension(498, 400));
        pnlLayout.setLayout(new java.awt.CardLayout());

        pnlMenu.setMinimumSize(new java.awt.Dimension(498, 400));

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

        btnHighScores.setText("High Scores");
        btnHighScores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHighScoresActionPerformed(evt);
            }
        });

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
                .addGap(15, 15, 15)
                .addGroup(pnlSwitches_MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNewGame, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnContinue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHighScores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        pnlSwitches_MainMenuLayout.setVerticalGroup(
            pnlSwitches_MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSwitches_MainMenuLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHighScores, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        lblCopyRight.setText("© CodeGamers Inc.");
        lblCopyRight.setPreferredSize(new java.awt.Dimension(96, 14));

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
                .addGap(15, 15, 15)
                .addComponent(lblHeadingTicTacToe)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                            .addGap(150, 150, 150)
                            .addComponent(lblCopyRight, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlMenuLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(pnlHeadingTicTacToe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(pnlSwitches_MainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHeadingTicTacToe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSwitches_MainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblCopyRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlLayout.add(pnlMenu, "card2");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/GameMode.png"))); // NOI18N
        jLabel5.setMinimumSize(new java.awt.Dimension(398, 76));
        jLabel5.setPreferredSize(new java.awt.Dimension(398, 76));

        javax.swing.GroupLayout pnlNewGameHeadingLayout = new javax.swing.GroupLayout(pnlNewGameHeading);
        pnlNewGameHeading.setLayout(pnlNewGameHeadingLayout);
        pnlNewGameHeadingLayout.setHorizontalGroup(
            pnlNewGameHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameHeadingLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        pnlNewGameHeadingLayout.setVerticalGroup(
            pnlNewGameHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlGameModeButtons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSinglePlayer.setText("Single Player");
        btnSinglePlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed(evt);
            }
        });

        btnMultiPlayer.setText("MultiPlayer");
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
                .addGap(15, 15, 15)
                .addGroup(pnlGameModeButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnMultiPlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSinglePlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(btnBack6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
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
            .addGroup(pnlNewGameModeSwitchesLayout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(pnlGameModeButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNewGameModeSwitchesLayout.setVerticalGroup(
            pnlNewGameModeSwitchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameModeSwitchesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGameModeButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
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

        pnlSinglePlayerGameMode.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlSinglePlayerGameMode.setPreferredSize(new java.awt.Dimension(498, 400));

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

        jButton3.setText("Create New Player");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGameInfoLayout = new javax.swing.GroupLayout(pnlGameInfo);
        pnlGameInfo.setLayout(pnlGameInfoLayout);
        pnlGameInfoLayout.setHorizontalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBackFromSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblPlayerName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlModes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlGameInfoLayout.createSequentialGroup()
                        .addComponent(comboNames, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        pnlGameInfoLayout.setVerticalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGameInfoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlayerName)
                    .addComponent(comboNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(8, 8, 8)
                .addComponent(pnlModes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPlay)
                    .addComponent(btnBackFromSinglePlayer))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlModeSelectionLayout = new javax.swing.GroupLayout(pnlModeSelection);
        pnlModeSelection.setLayout(pnlModeSelectionLayout);
        pnlModeSelectionLayout.setHorizontalGroup(
            pnlModeSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModeSelectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlModeSelectionLayout.setVerticalGroup(
            pnlModeSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModeSelectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLayout.add(pnlSinglePlayerGameMode, "card5");

        pnlGame.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlGame.setPreferredSize(new java.awt.Dimension(498, 400));

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
                        .addGap(0, 92, Short.MAX_VALUE)))
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
                .addGap(50, 50, 50)
                .addGroup(pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(lblGameGameMode))
                .addContainerGap())
        );

        pnlLayout.add(pnlGame, "card3");

        pnlAbout.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlAbout.setPreferredSize(new java.awt.Dimension(498, 400));

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
                .addContainerGap(82, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );

        pnlLayout.add(pnlAbout, "card6");

        pnlContinue.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlContinue.setPreferredSize(new java.awt.Dimension(498, 400));

        jButton5.setText("Back");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        savedDetails.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        savedDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savedDetailsMouseClicked(evt);
            }
        });
        savedDetails.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                savedDetailsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(savedDetails);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("Selete Saved Games");

        javax.swing.GroupLayout pnlContinueLayout = new javax.swing.GroupLayout(pnlContinue);
        pnlContinue.setLayout(pnlContinueLayout);
        pnlContinueLayout.setHorizontalGroup(
            pnlContinueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContinueLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnlContinueLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(pnlContinueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        pnlContinueLayout.setVerticalGroup(
            pnlContinueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContinueLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap())
        );

        pnlLayout.add(pnlContinue, "pnlContinue");

        pnlHighScores.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlHighScores.setPreferredSize(new java.awt.Dimension(498, 400));

        jScrollPane2.setViewportView(listName);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Player Name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Score");

        jScrollPane3.setViewportView(listScore);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(48, 48, 48))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        jButton6.setText("Back");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/HighScore.png"))); // NOI18N

        javax.swing.GroupLayout pnlHighScoresLayout = new javax.swing.GroupLayout(pnlHighScores);
        pnlHighScores.setLayout(pnlHighScoresLayout);
        pnlHighScoresLayout.setHorizontalGroup(
            pnlHighScoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHighScoresLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(pnlHighScoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHighScoresLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHighScoresLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(42, 42, 42))))
        );
        pnlHighScoresLayout.setVerticalGroup(
            pnlHighScoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHighScoresLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlHighScoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHighScoresLayout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHighScoresLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );

        pnlLayout.add(pnlHighScores, "card8");

        pnlMultiPlayerGame.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlMultiPlayerGame.setPreferredSize(new java.awt.Dimension(498, 400));

        pnlGameBoard1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlGameBoard1.setLayout(new java.awt.GridLayout(3, 3));

        box10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box10Layout = new javax.swing.GroupLayout(box10);
        box10.setLayout(box10Layout);
        box10Layout.setHorizontalGroup(
            box10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Ibox10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
        );
        box10Layout.setVerticalGroup(
            box10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Ibox10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );

        pnlGameBoard1.add(box10);

        box11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box11Layout = new javax.swing.GroupLayout(box11);
        box11.setLayout(box11Layout);
        box11Layout.setHorizontalGroup(
            box11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box11Layout.setVerticalGroup(
            box11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box11);

        box12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box12Layout = new javax.swing.GroupLayout(box12);
        box12.setLayout(box12Layout);
        box12Layout.setHorizontalGroup(
            box12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box12Layout.setVerticalGroup(
            box12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box12);

        box13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box13Layout = new javax.swing.GroupLayout(box13);
        box13.setLayout(box13Layout);
        box13Layout.setHorizontalGroup(
            box13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box13Layout.setVerticalGroup(
            box13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box13);

        box14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box14Layout = new javax.swing.GroupLayout(box14);
        box14.setLayout(box14Layout);
        box14Layout.setHorizontalGroup(
            box14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box14Layout.setVerticalGroup(
            box14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box14);

        box15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box15Layout = new javax.swing.GroupLayout(box15);
        box15.setLayout(box15Layout);
        box15Layout.setHorizontalGroup(
            box15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box15Layout.setVerticalGroup(
            box15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box15);

        box16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box16Layout = new javax.swing.GroupLayout(box16);
        box16.setLayout(box16Layout);
        box16Layout.setHorizontalGroup(
            box16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box16Layout.setVerticalGroup(
            box16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box16);

        box17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box17Layout = new javax.swing.GroupLayout(box17);
        box17.setLayout(box17Layout);
        box17Layout.setHorizontalGroup(
            box17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box17Layout.setVerticalGroup(
            box17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box17);

        box18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ibox18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout box18Layout = new javax.swing.GroupLayout(box18);
        box18.setLayout(box18Layout);
        box18Layout.setHorizontalGroup(
            box18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
            .addGroup(box18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        box18Layout.setVerticalGroup(
            box18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
            .addGroup(box18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Ibox18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pnlGameBoard1.add(box18);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/MultiPlayer.png"))); // NOI18N

        jLabel13.setText("Port :");

        jLabel9.setText("Status :");

        messageLabel.setText(" ");

        jLabel11.setText("Opponent :");

        lblOpponentName.setText("Not Recieved Yet");

        jButton9.setText("Back");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMultiPlayerGameLayout = new javax.swing.GroupLayout(pnlMultiPlayerGame);
        pnlMultiPlayerGame.setLayout(pnlMultiPlayerGameLayout);
        pnlMultiPlayerGameLayout.setHorizontalGroup(
            pnlMultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerGameLayout.createSequentialGroup()
                .addGroup(pnlMultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMultiPlayerGameLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(pnlGameBoard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMultiPlayerGameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(lblPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMultiPlayerGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMultiPlayerGameLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel12))
                    .addGroup(pnlMultiPlayerGameLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblOpponentName)))
                .addGap(33, 33, 33))
        );
        pnlMultiPlayerGameLayout.setVerticalGroup(
            pnlMultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMultiPlayerGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMultiPlayerGameLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlGameBoard1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMultiPlayerGameLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(1, 1, 1)
                .addGroup(pnlMultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messageLabel)
                    .addComponent(jLabel11)
                    .addComponent(lblOpponentName))
                .addContainerGap())
        );

        pnlLayout.add(pnlMultiPlayerGame, "card9");

        pnlMultiPlayerGameMode.setMinimumSize(new java.awt.Dimension(498, 400));
        pnlMultiPlayerGameMode.setPreferredSize(new java.awt.Dimension(498, 400));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/MultiPlayer.png"))); // NOI18N

        jLabel7.setText("Create a Game Server :");

        btn.setText("Create Game Server");
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        jLabel8.setText("PORT");

        jLabel10.setText("Status :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn))
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblServerStates, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblServerStates, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel14.setText("Connect to a Remote Game Server");

        jLabel15.setText("PORT");

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtClientPort, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtClientPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConnect))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel16.setText("Player Name");
        jLabel16.setMaximumSize(new java.awt.Dimension(65, 14));
        jLabel16.setMinimumSize(new java.awt.Dimension(65, 14));

        comboMultiNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMultiNamesActionPerformed(evt);
            }
        });

        jButton1.setText("Create New Player");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboMultiNames, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMultiNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton8.setText("Back");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMultiPlayerOptionsLayout = new javax.swing.GroupLayout(pnlMultiPlayerOptions);
        pnlMultiPlayerOptions.setLayout(pnlMultiPlayerOptionsLayout);
        pnlMultiPlayerOptionsLayout.setHorizontalGroup(
            pnlMultiPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerOptionsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlMultiPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlMultiPlayerOptionsLayout.setVerticalGroup(
            pnlMultiPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMultiPlayerOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMultiPlayerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMultiPlayerOptionsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8))
                    .addGroup(pnlMultiPlayerOptionsLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlMultiplayerPaneLayout = new javax.swing.GroupLayout(pnlMultiplayerPane);
        pnlMultiplayerPane.setLayout(pnlMultiplayerPaneLayout);
        pnlMultiplayerPaneLayout.setHorizontalGroup(
            pnlMultiplayerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiplayerPaneLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel6)
                .addContainerGap(33, Short.MAX_VALUE))
            .addComponent(pnlMultiPlayerOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlMultiplayerPaneLayout.setVerticalGroup(
            pnlMultiplayerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiplayerPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMultiPlayerOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMultiPlayerGameModeLayout = new javax.swing.GroupLayout(pnlMultiPlayerGameMode);
        pnlMultiPlayerGameMode.setLayout(pnlMultiPlayerGameModeLayout);
        pnlMultiPlayerGameModeLayout.setHorizontalGroup(
            pnlMultiPlayerGameModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMultiplayerPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlMultiPlayerGameModeLayout.setVerticalGroup(
            pnlMultiPlayerGameModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMultiplayerPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlLayout.add(pnlMultiPlayerGameMode, "card10");

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
        String[] details = saveHandler.getSavedGames();
        savedDetails.setListData(details);
        pnlContinue.setName("pnlContinue");
        ((CardLayout) pnlLayout.getLayout()).show(pnlLayout, "pnlContinue");

    }//GEN-LAST:event_btnContinueActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        
         if (((Player)(comboNames.getSelectedItem())).getPlayerName().trim().equals("")) {
           JOptionPane.showMessageDialog(this, "Please Select Your Name");
        } else {
          setNewGame();
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cardLayout.show(pnlLayout, "pnlAbout");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnBack6btnBack4btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack6btnBack4btnBackActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
    }//GEN-LAST:event_btnBack6btnBack4btnBackActionPerformed

    private void btnMultiPlayerbtnMultiPlayer4btnMultiPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultiPlayerbtnMultiPlayer4btnMultiPlayerActionPerformed
        cardLayout.show(pnlLayout, "pnlMultiPlayerGameMode");
        comboMultiNames.setModel(new DefaultComboBoxModel(playerHandler.getPlayers().toArray()));
        comboNames.repaint();
    }//GEN-LAST:event_btnMultiPlayerbtnMultiPlayer4btnMultiPlayerActionPerformed

    private void btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed
        cardLayout.show(pnlLayout, "pnlSinglePlayerGameMode");
        comboNames.setModel(new DefaultComboBoxModel(playerHandler.getPlayers().toArray()));
        comboNames.repaint();
    }//GEN-LAST:event_btnSinglePlayerbtnSinglePlayer4btnSinglePlayerActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnBackFromSinglePlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackFromSinglePlayerActionPerformed
        cardLayout.show(pnlLayout, "pnlNewGame");
    }//GEN-LAST:event_btnBackFromSinglePlayerActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (isStarted && !isFinish) {
            int msg = JOptionPane.showConfirmDialog(this, "Do you want to save the game before leave?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (msg == JOptionPane.YES_NO_OPTION) {
                try {

                    saveHandler.saveGame(gameEngine.getBoard(), gameEngine.getPlayer(), gameEngine.getMode());
                    try {
                        infoLabel.setText("Saving...");
                        showProgress( "pnlMenu");
                    } catch (InterruptedException ex) {

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Error! Saving Failed");
                }
            } else {
                cardLayout.show(pnlLayout, "pnlMenu");
            }
        }else{
            cardLayout.show(pnlLayout, "pnlMenu");
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String input = "";
        try {

            while (true) {
                input = JOptionPane.showInputDialog(this, "Enter the Player Name", "Create New Player", JOptionPane.PLAIN_MESSAGE);
                if (input.trim().equals("") || input.equals(null)) {
                    JOptionPane.showMessageDialog(this, "Please Enter Your Name");
                } else {
                    if (playerHandler.setNewPlayer(input)) {
                        break;
                    }
                }
            }
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(this, "Error! DataBase Failed");
        }
        comboNames.setModel(new DefaultComboBoxModel(playerHandler.getPlayers().toArray()));
        comboNames.repaint();;

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnHighScoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHighScoresActionPerformed
        cardLayout.show(pnlLayout, "pnlHighScores");
        final ArrayList playersForScore = playerHandler.getPlayers();

        Collections.sort(playersForScore, new Comparator<Player>() {

            @Override
            public int compare(Player o1, Player o2) {
                return o2.getPlayerScore() - o1.getPlayerScore();
            }
        });
        listName.setModel(new AbstractListModel() {

            @Override
            public int getSize() {
                return playersForScore.size();
            }

            @Override
            public Object getElementAt(int index) {
                return ((Player) (playersForScore.get(index))).getPlayerName();
            }
        });
        listScore.setModel(new AbstractListModel() {

            @Override
            public int getSize() {
                return playersForScore.size();
            }

            @Override
            public Object getElementAt(int index) {
                return ((Player) (playersForScore.get(index))).getPlayerScore();
            }
        });
    }//GEN-LAST:event_btnHighScoresActionPerformed

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed
        int port;
        try {
            port = Integer.parseInt(txtServerPort.getText());
            if (port < 1024) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Server Port Should be a number > 1024", "Tic Tac Toe Game", JOptionPane.WARNING_MESSAGE);
            port = 8901;
            txtServerPort.setText("8901");
            return;
        }
        final int serverPort = port;
        multiPlayerServer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new GameServer().startGameServer(serverPort);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Port Already in Use. Try Different Port", "Tic Tac Toe Game", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        multiPlayerServer.start();
    }//GEN-LAST:event_btnActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        if (txtClientPort.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server Port Should be a number > 1024", "Tic Tac Toe Error Handler", JOptionPane.WARNING_MESSAGE);
            txtClientPort.setText(txtServerPort.getText().isEmpty() ? "8901" : txtServerPort.getText());
            return;
        }
        try {
            int PORT = Integer.parseInt(txtClientPort.getText());
            String playerName1 = ((Player) comboMultiNames.getSelectedItem()).toString();
            GameClient multiPlayer = new GameClient(pnlGameBoard1, messageLabel, "localhost", cross, ring, lblOpponentName, playerName1, PORT);
            multiPlayerClient = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        multiPlayer.startClient();
                    } catch (IOException e) {

                    }
                }
            });
            multiPlayerClient.start();
            cardLayout.show(pnlLayout, "pnlMultiPlayerGame");
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(this, "Port is not reachable", "Tic Tac Toe Error Handler", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this, "IOException", "Tic Tac Toe Error Handler", JOptionPane.WARNING_MESSAGE);
            System.out.println(e1);
        } catch (NumberFormatException e2) {
            JOptionPane.showMessageDialog(this, "Server Port Should be a number > 1024", "Tic Tac Toe Error Handler", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (HeadlessException e3) {
            JOptionPane.showMessageDialog(this, "Headless Exception. Please Try Again", "Tic Tac Toe Error Handler", JOptionPane.WARNING_MESSAGE);
            return;
        }
        lblPortNumber.setText(txtClientPort.getText());
    }//GEN-LAST:event_btnConnectActionPerformed

    private void comboMultiNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMultiNamesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMultiNamesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String input = JOptionPane.showInputDialog(this, "Enter the Player Name", "Create New Player", JOptionPane.OK_CANCEL_OPTION);
            if (input.isEmpty()) {
                throw new NullPointerException();
            }
            playerHandler.setNewPlayer(input);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Player Name can't be Null", "Tic Tac Toe Error Handler", JOptionPane.INFORMATION_MESSAGE);
            return;
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(this, "Error! DataBase Failed");
        }
        comboMultiNames.setModel(new DefaultComboBoxModel(playerHandler.getPlayers().toArray()));
        comboMultiNames.repaint();;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        cardLayout.show(pnlLayout, "pnlNewGame");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
                                                 
        cardLayout.show(pnlLayout,"pnlMultiPlayerGameMode");
        
        try {
            multiPlayerServer.stop();
            multiPlayerClient.stop();
            
        }catch(Exception e) {
            System.out.println(e);
        }
        Ibox10.setIcon(null);
        Ibox11.setIcon(null);
        Ibox12.setIcon(null);
        Ibox13.setIcon(null);
        Ibox14.setIcon(null);
        Ibox15.setIcon(null);
        Ibox16.setIcon(null);
        Ibox17.setIcon(null);
        Ibox18.setIcon(null);
                          
    }//GEN-LAST:event_jButton9ActionPerformed

    private void savedDetailsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_savedDetailsValueChanged
//        if (evt.getValueIsAdjusting()){
//        saveHandler.loadGame(this, savedDetails.getSelectedIndex());  
//        }
//         
    }//GEN-LAST:event_savedDetailsValueChanged

    private void savedDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savedDetailsMouseClicked
        if(evt.getClickCount()==2){
            try {
                saveHandler.loadGame(this, savedDetails.getSelectedIndex());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error! DataBase Falied");
            }
        }
        isFinish = false;
        isStarted = true;
    }//GEN-LAST:event_savedDetailsMouseClicked

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
    private javax.swing.JLabel Ibox10;
    private javax.swing.JLabel Ibox11;
    private javax.swing.JLabel Ibox12;
    private javax.swing.JLabel Ibox13;
    private javax.swing.JLabel Ibox14;
    private javax.swing.JLabel Ibox15;
    private javax.swing.JLabel Ibox16;
    private javax.swing.JLabel Ibox17;
    private javax.swing.JLabel Ibox18;
    private javax.swing.JLabel Ibox2;
    private javax.swing.JLabel Ibox3;
    private javax.swing.JLabel Ibox4;
    private javax.swing.JLabel Ibox5;
    private javax.swing.JLabel Ibox6;
    private javax.swing.JLabel Ibox7;
    private javax.swing.JLabel Ibox8;
    private javax.swing.JLabel Ibox9;
    private javax.swing.JPanel box1;
    private javax.swing.JPanel box10;
    private javax.swing.JPanel box11;
    private javax.swing.JPanel box12;
    private javax.swing.JPanel box13;
    private javax.swing.JPanel box14;
    private javax.swing.JPanel box15;
    private javax.swing.JPanel box16;
    private javax.swing.JPanel box17;
    private javax.swing.JPanel box18;
    private javax.swing.JPanel box2;
    private javax.swing.JPanel box3;
    private javax.swing.JPanel box4;
    private javax.swing.JPanel box5;
    private javax.swing.JPanel box6;
    private javax.swing.JPanel box7;
    private javax.swing.JPanel box8;
    private javax.swing.JPanel box9;
    private javax.swing.JButton btn;
    private javax.swing.JButton btnBack6;
    private javax.swing.JButton btnBackFromSinglePlayer;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnContinue;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHighScores;
    private javax.swing.JButton btnMultiPlayer;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnSinglePlayer;
    private org.jdesktop.swingx.JXBusyLabel busyProgress;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkFirstPlayerPC;
    private javax.swing.JComboBox comboMultiNames;
    private javax.swing.JComboBox comboNames;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAboutHeading;
    private javax.swing.JLabel lblAboutTxt;
    private javax.swing.JLabel lblCopyRight;
    private javax.swing.JLabel lblGameGameMode;
    private javax.swing.JLabel lblGameWelcome;
    private javax.swing.JLabel lblHeadingTicTacToe;
    private javax.swing.JLabel lblOpponentName;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JLabel lblPortNumber;
    private javax.swing.JLabel lblServerStates;
    private javax.swing.JList listName;
    private javax.swing.JList listScore;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JPanel pnlAbout;
    private javax.swing.JPanel pnlContinue;
    private javax.swing.JPanel pnlGame;
    private javax.swing.JPanel pnlGameBoard;
    private javax.swing.JPanel pnlGameBoard1;
    private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlGameModeButtons;
    private javax.swing.JPanel pnlHeadingTicTacToe;
    private javax.swing.JPanel pnlHighScores;
    private javax.swing.JPanel pnlLayout;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlModeSelection;
    private javax.swing.JPanel pnlModes;
    private javax.swing.JPanel pnlMultiPlayerGame;
    private javax.swing.JPanel pnlMultiPlayerGameMode;
    private javax.swing.JPanel pnlMultiPlayerOptions;
    private javax.swing.JPanel pnlMultiplayerPane;
    private javax.swing.JPanel pnlNewGame;
    private javax.swing.JPanel pnlNewGameHeading;
    private javax.swing.JPanel pnlNewGameModeSwitches;
    private javax.swing.JPanel pnlSinglePlayerGameMode;
    private javax.swing.JPanel pnlSinglePlayerHeading;
    private javax.swing.JPanel pnlSwitches_MainMenu;
    private javax.swing.JProgressBar progress;
    private javax.swing.JRadioButton rbEasyMode;
    private javax.swing.JRadioButton rbHardMode;
    private javax.swing.JRadioButton rbMediumMode;
    private javax.swing.JFrame saveProgress;
    private javax.swing.JList savedDetails;
    private javax.swing.JTextField txtClientPort;
    private javax.swing.JTextField txtServerPort;
    // End of variables declaration//GEN-END:variables

    public void setNewGame() {
        clearGUIBoard();
        isStarted = true;
        cardLayout.show(pnlLayout, "pnlGame");
        lblGameWelcome.setText("Welcome ".concat(((Player) comboNames.getSelectedItem()).getPlayerName()));
        Enumeration<AbstractButton> modeSelector = buttonGroup1.getElements();
        int modeCount = 0;
        while (modeSelector.hasMoreElements()) {
            JRadioButton modeSelect = (JRadioButton) modeSelector.nextElement();
            if (modeSelect.isSelected()) {
                
                gameEngine = new GameEngine(modeCount, ((Player) comboNames.getSelectedItem()).getPlayerName(), !chkFirstPlayerPC.isSelected());
                lblGameGameMode.setText("Game Mode : ".concat(modeSelect.getText()));
                mode = modeCount;
                break;
            }
            modeCount++;
        };
        isFinish = true;
        if (chkFirstPlayerPC.isSelected()) {
            setStarted();
            gameEngine.play();
            markBox(gameEngine.getCPUMove());
        }
    }

    private void clearGUIBoard() {
        Ibox1.setIcon(null);
        Ibox2.setIcon(null);
        Ibox3.setIcon(null);
        Ibox4.setIcon(null);
        Ibox5.setIcon(null);
        Ibox6.setIcon(null);
        Ibox7.setIcon(null);
        Ibox8.setIcon(null);
        Ibox9.setIcon(null);
    }

    private void setStarted() {
        isFinish = false;
    }

    private void setFinished() {
        isFinish = true;
        isStarted = false;
    }

    private void showProgress(String menu) throws InterruptedException {

        busyProgress.getBusyPainter().setBaseColor(java.awt.SystemColor.DARK_GRAY);
        busyProgress.getBusyPainter().setHighlightColor(Color.ORANGE);
        progress.setForeground(Color.ORANGE);

        saveProgress.setLocationRelativeTo(this);
        saveProgress.setUndecorated(true);
        saveProgress.setVisible(true);

        progress.setValue(1);

        new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                progress.setValue(progress.getValue() + 1);
                if (progress.getValue() == progress.getMaximum()) {
                    saveProgress.dispose();
                    progress.setValue(0);

                    cardLayout.show(pnlLayout, menu);
                    ((Timer) e.getSource()).stop();

                }
            }
        }).start();

    }

    public JComboBox getTxtPlayerName() {
        return comboNames;
    }

    public JRadioButton getRbEasyMode() {
        return rbEasyMode;
    }

    public JRadioButton getRbHardMode() {
        return rbHardMode;
    }

    public JRadioButton getRbMediumMode() {
        return rbMediumMode;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
    
    public ArrayList<JLabel> getBoxesArray(){
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(Ibox1);
        labels.add(Ibox2);
        labels.add(Ibox3);
        labels.add(Ibox4);
        labels.add(Ibox5);
        labels.add(Ibox6);
        labels.add(Ibox7);
        labels.add(Ibox8);
        labels.add(Ibox9);
        
        return labels;
        
    }

    public Icon getCross() {
        return cross;
    }

    public Icon getRing() {
        return ring;
    }
    
    
}
