import java.util.Arrays;
import java.util.Random;
public class XPuzzle
{
    enum MoveSide
    {
        LEFT, RIGHT, UP, DOWN
    }

    private int[][] map;
    private int width, height, xGap, yGap;
    XPuzzle(int width, int height)
    {
        this.width = width;
        this.height = height;
        xGap = width-1;
        yGap = height-1;
        map = new int[height][width];
        mapInit();
    }

    XPuzzle(XPuzzle xp)
    {
        this.width = xp.width;
        this.height = xp.height;
        xGap = xp.xGap;
        yGap = xp.yGap;
        map = new int[height][width];
        PuzzleSearch.copyState(this.map, xp.map, this.height, this.width);
    }

    private void mapInit()
    {
        int cnt = 0;
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                map[i][j] = cnt++;
            }
        }
        map[yGap][xGap] = -1;
    }

    void randomizeMap(int moves)
    {
        mapInit();
        Random random = new Random();
        MoveSide s;
        for(int i = 0; i < moves; i++)
        {
            s = MoveSide.values()[random.nextInt(MoveSide.values().length)];
            move(s);
        }
    }

    void move(MoveSide side)
    {
        int tmpXGap = xGap;
        int tmpYGap = yGap;
        switch(side)
        {
            case LEFT:
            {
                if(xGap > 0)
                    xGap -= 1;
                break;
            }
            case RIGHT:
            {
                if(xGap < width-1)
                    xGap += 1;
                break;
            }
            case UP:
            {
                if(yGap > 0)
                    yGap -= 1;
                break;
            }
            case DOWN:
            {
                if(yGap < height-1)
                    yGap += 1;
                break;
            }
        }
        int tmp = map[tmpYGap][tmpXGap];
        map[tmpYGap][tmpXGap] = map[yGap][xGap];
        map[yGap][xGap] = tmp;
    }

    public boolean canMove(MoveSide side)
    {
        switch(side)
        {
            case LEFT:
            {
                if(xGap > 0)
                    return true;
                break;
            }
            case RIGHT:
            {
                if(xGap < width-1)
                    return true;
                break;
            }
            case UP:
            {
                if(yGap > 0)
                    return true;
                break;
            }
            case DOWN:
            {
                if(yGap < height-1)
                    return true;
                break;
            }
        }
        return false;
    }

    int[][] getMap()
    {
        return map;
    }

    void drawMap()
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
                System.out.printf("%2d ", map[i][j]+1);
            System.out.println();
        }
        System.out.println();
    }

    int getxGap() {return xGap;}
    int getyGap() {return yGap;}
    public static boolean isEqualPuzzle(XPuzzle p1, XPuzzle p2)
    {
        return Arrays.deepEquals(p1.map, p2.map);
    }


}
