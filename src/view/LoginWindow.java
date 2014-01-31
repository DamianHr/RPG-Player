package view;

import controller.MainControler;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import view.tools.WebRequester;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Damian
 */
public class LoginWindow extends JFrame {
    JPanel panel;

    final String urlToLogin = "http://localhost/rpg/index.php/service_login";
    final String urlToGetData = "http://localhost/rpg/index.php/service_game";
    int userId = 0;

    JTextField textFieldUsername;
    JLabel statutLabel;

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
        textFieldUsername = new JTextField(10);
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

        statutLabel = new JLabel("");
        panel.add(statutLabel);

        JPanel containerButtons = new JPanel(new FlowLayout());
        JButton okButton = new JButton("Connect");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!textFieldUsername.getText().isEmpty() && !String.valueOf(textFieldPassword.getPassword()).isEmpty()) {
                    //Connect WS game_login
                    java.util.List<GameToSelect> gamesToSelect = new ArrayList<GameToSelect>();
                    try {
                        String xml_login = WebRequester.sendAuthentificationRequest(urlToLogin, textFieldUsername.getText(), String.valueOf(textFieldPassword.getPassword()));
                        gamesToSelect = getGameToSelect(xml_login);
                        statutLabel.setText("");
                        LoginWindow.this.repaint();
                    } catch (Exception e1) {
                        statutLabel.setText("Error on login");
                        LoginWindow.this.repaint();
                        e1.printStackTrace();
                    }

                    if(!gamesToSelect.isEmpty()) {
                        createGameSelector(gamesToSelect);
                        statutLabel.setText("");
                        LoginWindow.this.repaint();
                    } else {
                        statutLabel.setText("No game found");
                        LoginWindow.this.repaint();
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
                try {
                    String xml_data = WebRequester.sendDataRetrivingRequest(urlToGetData, userId, selected.gameId);
                    (LoginWindow.this).setVisible(false);
                    new MainControler(xml_data);
                } catch (UnsupportedEncodingException e1) {
                    statutLabel.setText("Error on game retrieving");
                    LoginWindow.this.repaint();
                    e1.printStackTrace();
                }
            }
        });

        container.add(playButton);

        panel.add(container);
        this.setSize(300, 200);
        panel.repaint();
    }

    private java.util.List<GameToSelect> getGameToSelect(String xmlData) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= docFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(xmlData.getBytes());
        org.w3c.dom.Document document = documentBuilder.parse(stream);

        org.w3c.dom.Element root = document.getDocumentElement();
        userId = Integer.valueOf(root.getAttribute("userid"));
        NodeList situations = root.getElementsByTagName("game");

        java.util.List<GameToSelect> games = new ArrayList<GameToSelect>();
        for(int i = 0; i < situations.getLength(); ++i){
            org.w3c.dom.Element situation = (org.w3c.dom.Element)situations.item(i);
            games.add(new GameToSelect(situation.getTextContent(), Integer.valueOf(situation.getAttribute("id"))));
        }
        return games;
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