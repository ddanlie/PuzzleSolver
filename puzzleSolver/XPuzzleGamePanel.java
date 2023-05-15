import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class XPuzzleGamePanel extends JPanel implements Runnable
{
    private final int   tileSize = 32,
                        scale = 5,
                        gameTileSize = tileSize * scale,
                        padding = 5;
    private final int   cols, rows,
                        frameWidth, frameHeight;

    private final int   fps = 60;
    private Thread gameThread;
    private KeyHandler keyH = new KeyHandler();
    private XPuzzle puzzle;
    private MenuPanel menu;

    private PuzzleSearch search;
    private XPuzzle[] solution = null;
    private int solutionIndex = 0;

    public XPuzzleGamePanel(int cols, int rows, MenuPanel menu)
    {
        super();
        puzzle = new XPuzzle(cols, rows);
        this.menu = menu;
        this.cols = cols;
        this.rows = rows;
        this.frameWidth = gameTileSize * this.cols + padding * (this.rows + 1);
        this.frameHeight = gameTileSize *  this.rows + padding * (this.cols + 1);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setBackground(Color.gray);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);
    }

    public void startGame()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
         double drawInterwal = (double)1_000_000_000/fps;
         double delta = 0;
         long currentTime;
         long lastTime = System.nanoTime();
         long frameDelta = 0;
         int frameCounter = 0;
         while(gameThread != null)
         {
             currentTime = System.nanoTime();
             delta += (currentTime - lastTime) / drawInterwal;
             frameDelta += currentTime - lastTime;
             lastTime = currentTime;
             if(delta >= 1)
             {
                 update();
                 repaint();

                 delta--;
                 frameCounter++;
             }
             if(frameDelta > 1_000_000_000)
             {
                 //System.out.println("FPS: "+frameCounter);
                 frameCounter = 0;
                 frameDelta = 0;
             }
         }
    }

    private void update()
    {
        if(solution != null)
        {
            try
            {
                Thread.sleep(500);
            }
            catch(InterruptedException e){}

            puzzle = solution[solutionIndex++];
            while(puzzle == null)
            {
                puzzle = solution[solutionIndex++];
            }
            if(solutionIndex >= solution.length)
            {
                solution = null;
                solutionIndex = 0;
                menu.labelDflt();
            }
        }
        else if(menu.cmd == MenuPanel.Command.START_CMD)
        {
            puzzle = new XPuzzle(cols, rows);
            this.requestFocus();
            menu.dflt();
        }
        else if(menu.cmd == MenuPanel.Command.RAND_CMD)
        {
            puzzle.randomizeMap(50);
            this.requestFocus();
            menu.dflt();
        }
        else if(menu.cmd == MenuPanel.Command.SOLUTION_CMD)
        {
            boolean menuPrepare = false;
            search = new PuzzleSearch(rows, cols, puzzle);
            solution = search.execute();
            if(solution != null)
                menu.solutionFound(true);
            else
            {
                menu.solutionFound(false);
            }

            menu.dflt();
            requestFocus();
        }
        else if(keyH.pressed[KeyHandler.GameKeys.W_KEY.ordinal()])
        {
            puzzle.move(XPuzzle.MoveSide.DOWN);
            keyH.dflt();
        }
        else if(keyH.pressed[KeyHandler.GameKeys.S_KEY.ordinal()])
        {
            puzzle.move(XPuzzle.MoveSide.UP);
            keyH.dflt();
        }
        else if(keyH.pressed[KeyHandler.GameKeys.D_KEY.ordinal()])
        {
            puzzle.move(XPuzzle.MoveSide.LEFT);
            keyH.dflt();
        }
        else if(keyH.pressed[KeyHandler.GameKeys.A_KEY.ordinal()])
        {
            puzzle.move(XPuzzle.MoveSide.RIGHT);
            keyH.dflt();
        }

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if(i == puzzle.getyGap() && j == puzzle.getxGap())
                    continue;

                int y = (i+1)*padding+i*gameTileSize;
                int x = (j+1)*padding+j*gameTileSize;

                g.setColor(Color.darkGray);
                g.fillRect(x, y, gameTileSize, gameTileSize);
                g.setFont(new Font("Arial", FontUIResource.BOLD, 64));
                g.setColor(Color.gray);
                g.drawString(String.valueOf(puzzle.getMap()[i][j]+1), x+(gameTileSize)/2-16,y+(gameTileSize)/2+16);
            }
        }
        g.dispose();
    }

    int getFrameWidth() { return frameWidth; }
    int getFrameHeight() { return frameHeight; }
}