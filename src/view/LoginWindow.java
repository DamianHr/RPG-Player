package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Damian
 */
public class LoginWindow extends JFrame {
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
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel containerUsername = new JPanel(new FlowLayout());
        JLabel labelUsername = new JLabel("Username", JLabel.TRAILING);
        containerUsername.add(labelUsername);
        JTextField textFieldUsername = new JTextField(10);
        labelUsername.setLabelFor(textFieldUsername);
        containerUsername.add(textFieldUsername);
        panel.add(containerUsername);

        JPanel containerPassword = new JPanel(new FlowLayout());
        JLabel labelPassword = new JLabel("Password", JLabel.TRAILING);
        containerPassword.add(labelPassword);
        JPasswordField textFieldPassword = new JPasswordField(10);
        labelPassword.setLabelFor(textFieldPassword);
        containerPassword.add(textFieldPassword);
        panel.add(containerPassword);

        JPanel containerButtons = new JPanel(new FlowLayout());
        JButton okButton = new JButton("Ok");
        containerButtons.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        containerButtons.add(cancelButton);
        panel.add(containerButtons);

        //Set up the content pane.
        panel.setOpaque(true);  //content panes must be opaque
        return panel;
    }
}