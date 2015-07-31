package graphics;

import com.jtattoo.plaf.hifi.HiFiBorderFactory;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

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
    boolean state = true;

    public GUI() {
        initComponents();
        startUpComponents();
    }

    private void startUpComponents() {
        this.setLocationRelativeTo(null);
        setLayout();
        gameBoxBorderDecorate();
        loadPlaySymbol();
    }

    private void setLayout() {
        pnlLayout.setLayout(cardLayout);
        pnlLayout.add("pnlMenu", pnlMenu);
        pnlLayout.add("pnlNewGame", pnlNewGame);
        pnlLayout.add("pnlGame", pnlGame);
        pnlLayout.add("pnlSinglePlayerGameMode", pnlSinglePlayerGameMode);
    }

    private void loadPlaySymbol() {
        //handle exception : Logger Class
        URL crossUrl = this.getClass().getResource("/resources/cross.png");
        URL ringUrl = this.getClass().getResource("/resources/ring.png");
        cross = new ImageIcon(crossUrl);
        ring = new ImageIcon(ringUrl);
    }

    private void gameBoxBorderDecorate() {
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
                if (state) {
                    Ibox1.setIcon(cross);
                } else {
                    Ibox1.setIcon(ring);
                }
                state = !state;
                
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
                if (state) {
                    Ibox2.setIcon(cross);
                } else {
                    Ibox2.setIcon(ring);
                }
                state = !state;
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
                if (state) {
                    Ibox3.setIcon(cross);
                } else {
                    Ibox3.setIcon(ring);
                }
                state = !state;
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
                if (state) {
                    Ibox4.setIcon(cross);
                } else {
                    Ibox4.setIcon(ring);
                }
                state = !state;
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
                if (state) {
                    Ibox5.setIcon(cross);
                } else {
                    Ibox5.setIcon(ring);
                }
                state = !state;
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
                if (state) {
                    Ibox6.setIcon(cross);
                } else {
                    Ibox6.setIcon(ring);
                }
                state = !state;
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
                if (state) {
                    Ibox7.setIcon(cross);
                } else {
                    Ibox7.setIcon(ring);
                }
                state = !state;
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
                if (state) {
                    Ibox8.setIcon(cross);
                } else {
                    Ibox8.setIcon(ring);
                }
                state = !state;
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
                if (state) {
                    Ibox9.setIcon(cross);
                } else {
                    Ibox9.setIcon(ring);
                }
                state = !state;
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
        btnSinglePlayer = new javax.swing.JButton();
        btnMultiPlayer = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlSinglePlayerGameMode = new javax.swing.JPanel();
        pnlModeSelection = new javax.swing.JPanel();
        pnlGameInfo = new javax.swing.JPanel();
        pnlModes = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        txtPlayerName = new javax.swing.JTextField();
        lblPlayerName = new javax.swing.JLabel();
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
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(pnlSwitches_MainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(lblCopyRight, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlHeadingTicTacToe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        pnlNewGameHeading.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSinglePlayer.setText("Single Player");
        btnSinglePlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinglePlayerActionPerformed(evt);
            }
        });

        btnMultiPlayer.setText("Multi Player");
        btnMultiPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultiPlayerActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNewGameHeadingLayout = new javax.swing.GroupLayout(pnlNewGameHeading);
        pnlNewGameHeading.setLayout(pnlNewGameHeadingLayout);
        pnlNewGameHeadingLayout.setHorizontalGroup(
            pnlNewGameHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewGameHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnMultiPlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSinglePlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNewGameHeadingLayout.setVerticalGroup(
            pnlNewGameHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMultiPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/GameMode.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(46, 46, 46))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlNewGameLayout = new javax.swing.GroupLayout(pnlNewGame);
        pnlNewGame.setLayout(pnlNewGameLayout);
        pnlNewGameLayout.setHorizontalGroup(
            pnlNewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewGameLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlNewGameHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        pnlNewGameLayout.setVerticalGroup(
            pnlNewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewGameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(pnlNewGameHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        pnlLayout.add(pnlNewGame, "card6");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Hard Mode");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Medium Mode");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Easy Mode");

        javax.swing.GroupLayout pnlModesLayout = new javax.swing.GroupLayout(pnlModes);
        pnlModes.setLayout(pnlModesLayout);
        pnlModesLayout.setHorizontalGroup(
            pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModesLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        pnlModesLayout.setVerticalGroup(
            pnlModesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtPlayerName.setToolTipText("Name");

        lblPlayerName.setText("Player Name");

        jButton3.setText("Play");
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
                .addGap(76, 76, 76)
                .addComponent(lblPlayerName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlModes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(179, 179, 179))
        );
        pnlGameInfoLayout.setVerticalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGameInfoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlayerName)
                    .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlModes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout pnlGameLayout = new javax.swing.GroupLayout(pnlGame);
        pnlGame.setLayout(pnlGameLayout);
        pnlGameLayout.setHorizontalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(pnlGameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        pnlGameLayout.setVerticalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(pnlGameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pnlLayout.add(pnlGame, "card3");

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

    private void btnSinglePlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinglePlayerActionPerformed
        cardLayout.show(pnlLayout, "pnlSinglePlayerGameMode");
    }//GEN-LAST:event_btnSinglePlayerActionPerformed

    private void btnMultiPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultiPlayerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMultiPlayerActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        cardLayout.show(pnlLayout, "pnlMenu");
    }//GEN-LAST:event_btnBackActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cardLayout.show(pnlLayout, "pnlGame");
    }//GEN-LAST:event_jButton3ActionPerformed

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
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnContinue;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnMultiPlayer;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JButton btnSinglePlayer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JLabel lblCopyRight;
    private javax.swing.JLabel lblHeadingTicTacToe;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JPanel pnlGame;
    private javax.swing.JPanel pnlGameBoard;
    private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlHeadingTicTacToe;
    private javax.swing.JPanel pnlLayout;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlModeSelection;
    private javax.swing.JPanel pnlModes;
    private javax.swing.JPanel pnlNewGame;
    private javax.swing.JPanel pnlNewGameHeading;
    private javax.swing.JPanel pnlSinglePlayerGameMode;
    private javax.swing.JPanel pnlSinglePlayerHeading;
    private javax.swing.JPanel pnlSwitches_MainMenu;
    private javax.swing.JTextField txtPlayerName;
    // End of variables declaration//GEN-END:variables
}
