import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MenuPanel extends JPanel
{
    enum Command
    {
        NONE_CMD, RAND_CMD, SOLUTION_CMD, START_CMD;
    }

    Command cmd;
    private JLabel solutionStatus;
    MenuPanel()
    {
        setBackground(Color.gray);
        setPreferredSize(new Dimension(300, 100));
        this.setLayout(new GridLayout(3, 2));
        JButton randButton = new JButton("Randomize");
        randButton.addActionListener(e -> cmd = Command.RAND_CMD);
        this.add(randButton);
        JButton solutionButton = new JButton("Find Solution");
        solutionButton.addActionListener(e -> cmd = Command.SOLUTION_CMD);
        this.add(solutionButton);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> cmd = Command.START_CMD);
        this.add(startButton);
        solutionStatus = new JLabel("", SwingConstants.CENTER);
        this.add(solutionStatus);
        JLabel info = new JLabel("Control: W,A,S,D", SwingConstants.CENTER);
        this.add(info);
    }

    void solutionFound(boolean found)
    {
        if(found)
            solutionStatus.setText("Solution found");
        else
            solutionStatus.setText("Solution not found");
    }


    void dflt()
    {
        cmd = Command.NONE_CMD;
    }

    void labelDflt()
    {
        solutionStatus.setText("");
    }
}
