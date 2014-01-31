import view.LoginWindow;
import view.Window;

import javax.swing.*;

/**
 * User: Damian
 * Date: 23/11/13
 * Time: 23:41
 */
public class Main {
    public static void main(String argv[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginWindow();

            }
        });
    }
}