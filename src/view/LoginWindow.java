package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Damian
 */
public class LoginWindow extends JFrame {
    JPanel panel;

    public LoginWindow() {
        init();
    }

    private void init() {
        this.setTitle("Login");
        this.setSize(300, 150);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) ((screenDimension.getWidth() - this.getWidth()) / 2),
                (int) ((screenDimension.getHeight() - this.getHeight()) / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(createPanel());
        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel createPanel() {
        //Create and populate the panel.
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel containerUsername = new JPanel(new FlowLayout());
        JLabel labelUsername = new JLabel("Username", JLabel.TRAILING);
        containerUsername.add(labelUsername);
        final JTextField textFieldUsername = new JTextField(10);
        labelUsername.setLabelFor(textFieldUsername);
        containerUsername.add(textFieldUsername);
        panel.add(containerUsername);

        JPanel containerPassword = new JPanel(new FlowLayout());
        JLabel labelPassword = new JLabel("Password", JLabel.TRAILING);
        containerPassword.add(labelPassword);
        final JPasswordField textFieldPassword = new JPasswordField(10);
        labelPassword.setLabelFor(textFieldPassword);
        containerPassword.add(textFieldPassword);
        panel.add(containerPassword);

        JLabel statutLabel = new JLabel("Coucou");
        panel.add(statutLabel);

        JPanel containerButtons = new JPanel(new FlowLayout());
        JButton okButton = new JButton("Connect");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!textFieldUsername.getText().isEmpty() && !textFieldPassword.getPassword().toString().isEmpty()) {
                    //Connect WS game_login
                    boolean connected = true;
                    java.util.List<GameToSelect> gamesToSelect = new ArrayList<GameToSelect>();
                    if(connected) {
                        createGameSelector(gamesToSelect);
                    }
                }
            }
        });
        containerButtons.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        containerButtons.add(cancelButton);
        panel.add(containerButtons);

        panel.setOpaque(true);
        return panel;
    }

    private void createGameSelector(java.util.List<GameToSelect> gamesToSelect) {
        JPanel container = new JPanel(new FlowLayout());

        final JComboBox<GameToSelect> combo = new JComboBox<GameToSelect>();
        combo.setSize(100, 20);
        for(GameToSelect gameToSelect : gamesToSelect)
            combo.addItem(gameToSelect);
        container.add(combo);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameToSelect selected = (GameToSelect) combo.getSelectedItem();
                //Appel WS service_game

                //

            }
        });

        container.add(playButton);

        panel.add(container);
        this.setSize(300, 200);
        panel.repaint();
    }

    class GameToSelect {
        String gameName;
        int gameId;

        GameToSelect(String gameName, int gameId) {
            this.gameName = gameName;
            this.gameId = gameId;
        }

        @Override
        public String toString() {
            return gameName;
        }
    }
}