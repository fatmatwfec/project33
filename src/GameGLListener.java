import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class GameGLListener implements GLEventListener, KeyListener {

    public BitSet keyBits = new BitSet(256);
    int maxWidth = 100;
    int maxHeight = 100;
    int xplayer1 = maxWidth/2, yplayer1 = maxHeight/2;
    int xplayer2 = maxWidth/2, yplayer2 = maxHeight/2;
    double xBall= maxWidth/0.2;
    double yBall= maxHeight/0.2;
    boolean playerwithball= false;
    int ballHolder=0; //0 if the ball is loose 1 if the ball is with player 1 and 2 if its with player 2
    final int stealRange=5;
    final double playerRange=3;
    private double dxBall= 8;
    private double dyBall= 6;
    private final double ballSize=30;
    public void updateBall() {
        xBall += dxBall;
        yBall += dyBall;
        //تغيير الاتجاه لو الكرة خبطت فى الحواف

        if (xBall + ballSize >= MAX_X || xBall <= MIN_X) {
            dxBall = -dxBall;
        }
        if (yBall + ballSize >= MAX_Y || yBall <= MIN_Y) {
            dyBall = -dyBall;
        }

    }
    public void throwBall(int player){
        if ball
    }
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
        if (isKeyPressed(KeyEvent.VK_PERIOD)) {
            steal(1);
        }
        if (isKeyPressed(KeyEvent.VK_TAB)) {
            steal(2);
        }

        // Player 1 Throw - SLASH (/) key
        if (isKeyPressed(KeyEvent.VK_SLASH)) {
            throwBall(1);
        }

        // Player 2 Throw - Q key
        if (isKeyPressed(KeyEvent.VK_Q)) {
            throwBall(2);
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
