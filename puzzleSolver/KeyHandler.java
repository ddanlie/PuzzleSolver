import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyHandler implements KeyListener
{
    public enum GameKeys
    {
        W_KEY, D_KEY, A_KEY, S_KEY
    }
    public boolean[] pressed;

    KeyHandler()
    {
        super();
        pressed = new boolean[GameKeys.values().length];
        dflt();
    }

    public void dflt()
    {
        Arrays.fill(pressed, false);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        switch(code)
        {
            case KeyEvent.VK_W:
            {
                pressed[GameKeys.W_KEY.ordinal()] = true;
                break;
            }
            case KeyEvent.VK_S:
            {
                pressed[GameKeys.S_KEY.ordinal()] = true;
                break;
            }
            case KeyEvent.VK_A:
            {
                pressed[GameKeys.A_KEY.ordinal()] = true;
                break;
            }
            case KeyEvent.VK_D:
            {
                pressed[GameKeys.D_KEY.ordinal()] = true;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
//        int code = e.getKeyCode();
//        switch(code)
//        {
//            case KeyEvent.VK_W:
//            {
//                pressed[GameKeys.W_KEY.ordinal()] = false;
//                break;
//            }
//            case KeyEvent.VK_S:
//            {
//                pressed[GameKeys.S_KEY.ordinal()] = false;
//                break;
//            }
//            case KeyEvent.VK_A:
//            {
//                pressed[GameKeys.A_KEY.ordinal()] = false;
//                break;
//            }
//            case KeyEvent.VK_D:
//            {
//                pressed[GameKeys.D_KEY.ordinal()] = false;
//                break;
//            }
//        }
    }
}
