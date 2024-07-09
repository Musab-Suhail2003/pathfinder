import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    MyPanel mp;
    public KeyHandler(MyPanel mp) {
        this.mp = mp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ENTER){
            double t0 = System.nanoTime();
            mp.shortestPath();
            double t1 = System.nanoTime();
            System.out.print((t1-t0)/1_000_000);
            System.out.print(" milliSeconds");
            System.out.println();
        }
        if(code == KeyEvent.VK_C){
            mp.clearPath();
        }
        if (code == KeyEvent.VK_ESCAPE){
            mp.clearAll();
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
}
