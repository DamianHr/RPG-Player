package view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * User: Damian
 */
public class MenuBar extends JMenuBar {

    public String CHOOSEN_FILE_PATH = null;

    public MenuBar() {
        // FILE
        JMenu menuFile = new JMenu("File");
        this.add(menuFile);

        JMenuItem itemOpenSource = new JMenuItem("Open source file");
        itemOpenSource.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return !file.isDirectory() && file.getAbsolutePath().endsWith(".xml");
                    }
                    @Override
                    public String getDescription() { return null; }
                });
                fileChooser.showOpenDialog(MenuBar.this.getParent());
                CHOOSEN_FILE_PATH = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });
        menuFile.add(itemOpenSource);

        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer reply = JOptionPane.showConfirmDialog(null, "Are you sure ?");
                if (JOptionPane.YES_OPTION == reply) {
                    System.exit(0);
                }
            }
        });
        menuFile.add(itemExit);
    }
}
