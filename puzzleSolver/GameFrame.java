import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame
{
    GameFrame(Dimension size)
    {
        super();
        //setPreferredSize(size);
        setLayout(new GridLayout(1,2));
        setTitle("Game");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
