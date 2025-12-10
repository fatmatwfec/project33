import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class GameGLListener implements GLEventListener, KeyListener {

    public BitSet keyBits = new BitSet(256);
    private static final int MAX_X = 400;
    private static final int MIN_X = -400;
    private static final int MAX_Y = 250;
    private static final int MIN_Y = -250;
    int maxWidth = 100;
    int maxHeight = 100;
    int xplayer1 = -350;
    int yplayer1 = 0;
    int xplayer2 = 350;
    int yplayer2 = 0;

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xplayer1 > 0) {
                xplayer1--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xplayer1 < maxWidth-10) {
                xplayer1++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (yplayer1 > 0) {
                yplayer1--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (yplayer1 < maxHeight - 10) {
                yplayer1++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (yplayer2 < maxHeight-10) {
                yplayer2++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_A)) {
            if (xplayer2 > 0) {
                xplayer2--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            if (xplayer2 < maxWidth-10) {
                xplayer2++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            if (yplayer2 > 0) {
                yplayer2--;
            }
        }



    }
    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
}
public BitSet keyBits = new BitSet(256);

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
}
