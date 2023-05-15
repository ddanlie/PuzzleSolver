import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        int c = 3, r = 3;
        MenuPanel menu = new MenuPanel();
        XPuzzleGamePanel xpuzzlePanel = new XPuzzleGamePanel(c, r, menu);
        GameFrame gf = new GameFrame(new Dimension(xpuzzlePanel.getFrameWidth()+menu.getWidth(), xpuzzlePanel.getFrameHeight()+menu.getHeight()));
        gf.add(xpuzzlePanel);
        gf.add(menu);
        gf.pack();
        xpuzzlePanel.requestFocus();
        xpuzzlePanel.startGame();
//        PuzzleSearch ps = new PuzzleSearch(4,5, 10);
//        System.out.println("Start:\n");
//        ps.showStartState();
//        System.out.println("Goal:\n");
//        ps.showGoalState();
//        System.out.printf("Solution found: %s\n", ps.execute() ? "yes" : "no");

    }
}